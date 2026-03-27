/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.overheid.aerius.gml.filter;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for filtering XML content based on regex patterns.
 * This class implements a streaming approach to handle large XML files efficiently,
 * processing data in chunks and maintaining proper state across buffer boundaries.
 *
 * <p><b>Sliding Window Algorithm:</b>
 * The reader processes the input using a fixed-size sliding window buffer instead of loading
 * the entire file into memory. This allows processing files of arbitrary size with constant
 * memory usage (typically under 1MB regardless of file size).
 *
 * <p><b>Boundary-Aware Chunking:</b>
 * When sliding the window forward, the algorithm ensures cuts are made at safe positions
 * (after complete XML tags) to prevent splitting elements across chunks. The algorithm:
 * <ul>
 *   <li>Keeps a safety margin at the buffer end for patterns that might span into next chunk</li>
 *   <li>Prefers cutting after the configured end tag (e.g., "&lt;/imaer:featureMember&gt;")</li>
 *   <li>Falls back to cutting after any closing angle bracket "&gt;"</li>
 * </ul>
 *
 * <p><b>Limitation:</b>
 * The content between {@code tagToFilter} and its corresponding end tag (including all nested
 * elements) must not exceed {@value #MAX_MATCH_SIZE} characters (64KB by default). If a single
 * block exceeds this size, the regex may fail to match and the block will not be filtered.
 * This limit is sufficient for typical IMAER receptor points but may need consideration
 * for elements with very large nested content.
 *
 * <p>Subclasses must provide the tag to filter and content regex pattern via the constructor:
 * <ul>
 *   <li>{@code tagToFilter} - the start tag of the container element (e.g., "&lt;imaer:featureMember&gt;")</li>
 *   <li>{@code contentRegex} - the regex pattern matching the content between start and end tags</li>
 * </ul>
 *
 * <p>The complete pattern is constructed as: {@code tagToFilter + contentRegex + endTag}
 * where endTag is automatically derived from tagToFilter.
 */
public abstract class AbstractXMLFilteringReader extends Reader {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractXMLFilteringReader.class);

  private static final int DEFAULT_BUFFER_SIZE = 8192;
  private static final int MAX_MATCH_SIZE = 65536;
  private static final int MIN_KEEP_RATIO = 4;

  private final Reader source;
  private final StringBuilder buffer;
  private final char[] readBuffer;
  private final Pattern pattern;
  private final String endTag;
  private int bufferPosition;
  private int bufferLength;
  private boolean endOfSource;
  private boolean closed;

  /**
   * Creates a new AbstractXMLFilteringReader.
   *
   * @param source the source reader to read from
   * @param tagToFilter the start tag of the container element to filter (e.g., "<imaer:featureMember>")
   * @param contentRegex the regex pattern for the content between start and end tags
   */
  protected AbstractXMLFilteringReader(final Reader source, final String tagToFilter, final String contentRegex) {
    this.source = source;
    this.buffer = new StringBuilder(MAX_MATCH_SIZE * 2);
    this.readBuffer = new char[DEFAULT_BUFFER_SIZE];
    this.endTag = "</" + tagToFilter.substring(1);
    this.pattern = Pattern.compile(tagToFilter + contentRegex + endTag, Pattern.CASE_INSENSITIVE);
    this.bufferPosition = 0;
    this.bufferLength = 0;
    this.endOfSource = false;
    this.closed = false;
    LOG.trace("Initialized {} with pattern: {}", this.getClass().getSimpleName(), pattern.pattern());
  }

  @Override
  public int read() throws IOException {
    final char[] singleCharBuffer = new char[1];
    final int result = read(singleCharBuffer, 0, 1);
    return result == -1 ? -1 : singleCharBuffer[0];
  }

  @Override
  public int read(final char[] cbuf, final int off, final int len) throws IOException {
    ensureOpen();

    if (len == 0) {
      return 0;
    }

    final int bytesRead = readFromBufferOrSource(cbuf, off, len);
    return determineReturnValue(bytesRead);
  }

  private int readFromBufferOrSource(final char[] cbuf, final int off, final int len) throws IOException {
    int remaining = len;
    int currentOffset = off;
    boolean canReadMore = true;

    while (remaining > 0 && canReadMore) {
      if (hasBufferedData()) {
        final int copied = copyFromBuffer(cbuf, currentOffset, remaining);
        currentOffset += copied;
        remaining -= copied;
      } else {
        canReadMore = tryToReadMoreData();
      }
    }

    return currentOffset - off;
  }

  private int determineReturnValue(final int bytesRead) {
    if (bytesRead > 0) {
      return bytesRead;
    } else if (endOfSource) {
      return -1;
    } else {
      return 0;
    }
  }

  private boolean tryToReadMoreData() throws IOException {
    if (endOfSource) {
      return false;
    }

    fillAndProcessBuffer();
    return hasBufferedData() || !endOfSource;
  }

  @Override
  public int read(final CharBuffer target) throws IOException {
    ensureOpen();

    final int len = target.remaining();
    final char[] chars = new char[len];
    final int n = read(chars, 0, len);

    if (n > 0) {
      target.put(chars, 0, n);
    }

    return n;
  }

  @Override
  public long skip(final long n) throws IOException {
    ensureOpen();

    if (n <= 0) {
      return 0;
    }

    final int bufferSize = (int) Math.min(n, DEFAULT_BUFFER_SIZE);
    final char[] skipBuffer = new char[bufferSize];
    long remaining = n;
    int totalSkipped = 0;
    boolean shouldContinue = true;

    while (remaining > 0 && shouldContinue) {
      final int toRead = (int) Math.min(remaining, bufferSize);
      final int read = read(skipBuffer, 0, toRead);

      if (read == -1) {
        shouldContinue = false;
      } else {
        totalSkipped += read;
        remaining -= read;
      }
    }

    return totalSkipped;
  }

  @Override
  public boolean ready() throws IOException {
    ensureOpen();
    return bufferPosition < bufferLength || source.ready();
  }

  @Override
  public boolean markSupported() {
    return false;
  }

  @Override
  public void mark(final int readAheadLimit) throws IOException {
    throw new IOException("mark/reset not supported");
  }

  @Override
  public void reset() throws IOException {
    throw new IOException("mark/reset not supported");
  }

  @Override
  public void close() throws IOException {
    if (!closed) {
      closed = true;
      source.close();
    }
  }

  private void ensureOpen() throws IOException {
    if (closed) {
      throw new IOException("Reader is closed");
    }
  }

  private boolean hasBufferedData() {
    return bufferPosition < bufferLength;
  }

  private int copyFromBuffer(final char[] cbuf, final int off, final int len) {
    final int available = bufferLength - bufferPosition;
    final int toCopy = Math.min(len, available);
    buffer.getChars(bufferPosition, bufferPosition + toCopy, cbuf, off);
    bufferPosition += toCopy;
    return toCopy;
  }

  private void fillAndProcessBuffer() throws IOException {
    compactBuffer();
    readFromSource();

    bufferLength = buffer.length();

    if (bufferLength > 0) {
      processBuffer();
    }
  }

  private void compactBuffer() {
    if (bufferPosition > 0) {
      buffer.delete(0, bufferPosition);
      bufferLength -= bufferPosition;
      bufferPosition = 0;
    }
  }

  private void readFromSource() throws IOException {
    int totalRead = 0;
    boolean shouldContinue = true;

    while (shouldContinue && !endOfSource && buffer.length() < MAX_MATCH_SIZE * 2) {
      final int bytesRead = source.read(readBuffer);

      if (bytesRead == -1) {
        endOfSource = true;
        shouldContinue = false;
      } else {
        buffer.append(readBuffer, 0, bytesRead);
        totalRead += bytesRead;

        if (totalRead >= DEFAULT_BUFFER_SIZE) {
          shouldContinue = false;
        }
      }
    }
  }

  private void processBuffer() {
    final StringBuilder filteredContent = new StringBuilder(bufferLength);
    final Matcher matcher = pattern.matcher(buffer);
    int lastMatchEnd = 0;

    while (matcher.find()) {
      filteredContent.append(buffer, lastMatchEnd, matcher.start());
      lastMatchEnd = matcher.end();
    }

    if (endOfSource) {
      appendRemainingContent(filteredContent, lastMatchEnd);
      replaceBufferContent(filteredContent);
    } else {
      final int safeCutPosition = findSafeCutPosition(lastMatchEnd);
      filteredContent.append(buffer, lastMatchEnd, safeCutPosition);
      // Replace buffer with filtered content, then append remaining unprocessed data
      buffer.delete(0, safeCutPosition);
      buffer.insert(0, filteredContent);
    }

    updateBufferState();
  }

  private void appendRemainingContent(final StringBuilder filteredContent, final int lastMatchEnd) {
    filteredContent.append(buffer, lastMatchEnd, bufferLength);
  }

  private void replaceBufferContent(final StringBuilder filteredContent) {
    buffer.setLength(0);
    buffer.append(filteredContent);
  }

  private void updateBufferState() {
    bufferLength = buffer.length();
    bufferPosition = 0;
  }

  private int findSafeCutPosition(final int lastMatchEnd) {
    final int minKeep = calculateMinimumKeepSize();
    final int maxCut = bufferLength - minKeep;

    int safeCutPosition = lastMatchEnd;

    if (maxCut > lastMatchEnd) {
      safeCutPosition = determineBestCutPosition(maxCut, lastMatchEnd);
    }

    return safeCutPosition;
  }

  private int determineBestCutPosition(final int maxCut, final int lastMatchEnd) {
    int bestCutPosition = maxCut;

    final int endTagPosition = findEndTagPosition(maxCut);
    if (isValidCutPosition(endTagPosition, lastMatchEnd)) {
      bestCutPosition = endTagPosition + endTag.length();
    } else {
      final int lastCloseTag = findLastCloseTag(maxCut);
      if (isValidCutPosition(lastCloseTag, lastMatchEnd)) {
        bestCutPosition = lastCloseTag + 1;
      }
    }

    return bestCutPosition;
  }

  private int calculateMinimumKeepSize() {
    return Math.min(MAX_MATCH_SIZE / MIN_KEEP_RATIO, bufferLength / MIN_KEEP_RATIO);
  }

  private int findEndTagPosition(final int maxCut) {
    return buffer.toString().lastIndexOf(endTag, maxCut);
  }

  private int findLastCloseTag(final int maxCut) {
    return buffer.toString().lastIndexOf(">", maxCut);
  }

  private static boolean isValidCutPosition(final int cutPosition, final int lastMatchEnd) {
    return cutPosition != -1 && cutPosition >= lastMatchEnd;
  }
}
