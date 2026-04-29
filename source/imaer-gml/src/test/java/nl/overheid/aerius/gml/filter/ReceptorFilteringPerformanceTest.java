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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Performance and memory benchmark for the receptor filtering implementation.
 * Disabled by default — run manually to compare before/after implementation changes.
 *
 * <p>Usage: run each test method, note the printed metrics, then switch implementation
 * and re-run to compare.
 */
@Disabled("Performance benchmark — run manually")
class ReceptorFilteringPerformanceTest {

  private static final Logger LOG = LoggerFactory.getLogger(ReceptorFilteringPerformanceTest.class);

  private static final int WARMUP_ITERATIONS = 3;
  private static final int MEASURE_ITERATIONS = 5;

  @TempDir
  Path tempDir;

  /**
   * Benchmarks the ReceptorFilteringReader reading from temp files, matching the
   * real-world pattern where GML comes from disk or a network stream.
   * Samples memory during the read loop to show min/max/avg — if the sliding window
   * is bounded, these values should stay in a fixed band regardless of input size.
   */
  @Test
  void testBenchmarkFilteringReaderFromFile() throws IOException {
    final int[] receptorCounts = {100, 1_000, 5_000, 10_000, 50_000, 100_000, 250_000};

    LOG.info("");
    LOG.info("=== ReceptorFilteringReader File-Based Benchmark ===");
    LOG.info("Memory columns show sampled heap usage DURING filtering (not before/after delta).");
    LOG.info("If the sliding window is bounded, Mem Min-Max should stay in a fixed band as input grows.");
    LOG.info(String.format("%-12s %-12s %-15s %-15s %-12s %-12s %-12s",
        "Receptors", "Input MB", "Avg Time ms", "Throughput MB/s", "Mem Min MB", "Mem Avg MB", "Mem Max MB"));

    for (final int count : receptorCounts) {
      final Path gmlFile = tempDir.resolve("bench_" + count + ".gml");
      writeGmlToFile(gmlFile, count);
      final long inputSizeBytes = Files.size(gmlFile);

      // Warmup — fewer iterations for large inputs
      final int warmup = count > 10_000 ? 1 : WARMUP_ITERATIONS;
      for (int i = 0; i < warmup; i++) {
        consumeFilteredFileWithMemorySampling(gmlFile);
      }

      // Measure — fewer iterations for large inputs to keep total runtime reasonable
      final int iterations = count > 10_000 ? 2 : MEASURE_ITERATIONS;
      long totalTimeNanos = 0;
      long sampleMin = Long.MAX_VALUE;
      long sampleMax = Long.MIN_VALUE;
      long sampleSum = 0;
      long sampleCount = 0;

      for (int i = 0; i < iterations; i++) {
        forceGc();

        final long start = System.nanoTime();
        final MemorySamples memorySamples = consumeFilteredFileWithMemorySampling(gmlFile);
        final long elapsed = System.nanoTime() - start;

        totalTimeNanos += elapsed;

        sampleMin = Math.min(memorySamples.sampleMin(), sampleMin);
        sampleMax = Math.max(memorySamples.sampleMax(), sampleMax);
        sampleSum += memorySamples.sampleSum();
        sampleCount += memorySamples.sampleCount();

        assertTrue(memorySamples.outputChars() < inputSizeBytes, "Filtered output should be smaller than input");
      }

      final double avgTimeMs = ((double) totalTimeNanos / iterations) / 1_000_000.0;
      final double inputMb = inputSizeBytes / (1024.0 * 1024.0);
      final double throughputMBs = inputMb / (avgTimeMs / 1000.0);

      if (sampleCount > 0) {
        LOG.info(String.format("%-12d %-12.1f %-15.2f %-15.2f %-12.2f %-12.2f %-12.2f",
            count, inputMb, avgTimeMs, throughputMBs,
            sampleMin / (1024.0 * 1024.0),
            (sampleSum / sampleCount) / (1024.0 * 1024.0),
            sampleMax / (1024.0 * 1024.0)));
      } else {
        LOG.info(String.format("%-12d %-12.1f %-15.2f %-15.2f %-12s %-12s %-12s",
            count, inputMb, avgTimeMs, throughputMBs, "N/A", "N/A", "N/A"));
      }
    }
    LOG.info("");
  }

  private record MemorySamples(long sampleMin, long sampleMax, long sampleSum, long sampleCount, long outputChars) {
  }

  private MemorySamples consumeFilteredFileWithMemorySampling(final Path gmlFile) throws IOException {
    long totalChars = 0;
    long sampleMin = Long.MAX_VALUE;
    long sampleMax = Long.MIN_VALUE;
    long sampleSum = 0;
    long sampleCount = 0;
    int readCounter = 0;
    final char[] buf = new char[8192];
    try (final Reader reader = new ReceptorFilteringReader(
        new BufferedReader(new InputStreamReader(Files.newInputStream(gmlFile), StandardCharsets.UTF_8)))) {
      int read;
      while ((read = reader.read(buf)) != -1) {
        totalChars += read;
        if (++readCounter % 10 == 0) {
          final long mem = usedMemory();
          sampleMin = Math.min(mem, sampleMin);
          sampleMax = Math.max(mem, sampleMax);
          sampleSum += mem;
          sampleCount++;
        }
      }
    }
    return new MemorySamples(sampleMin, sampleMax, sampleSum, sampleCount, totalChars);
  }

  private static void writeGmlToFile(final Path file, final int receptorCount) throws IOException {
    try (final BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
      writeGmlHeader(writer);
      for (int i = 1; i <= receptorCount; i++) {
        writeReceptorPoint(writer, i);
      }
      writeGmlFooter(writer, receptorCount);
    }
  }

  private static void writeGmlHeader(final Writer writer) throws IOException {
    writer.write("""
        <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
        <imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:xlink="http://www.w3.org/1999/xlink"
            xmlns:gml="http://www.opengis.net/gml/3.2"
            xmlns:imaer="http://imaer.aerius.nl/6.0"
            gml:id="NL.IMAER.Collection"
            xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
            <imaer:metadata>
                <imaer:AeriusCalculatorMetadata>
                    <imaer:project><imaer:ProjectMetadata><imaer:year>2013</imaer:year></imaer:ProjectMetadata></imaer:project>
                    <imaer:calculation><imaer:CalculationMetadata><imaer:method>NATURE_AREA</imaer:method><imaer:substance>NOX</imaer:substance><imaer:substance>NH3</imaer:substance><imaer:resultType>DEPOSITION</imaer:resultType></imaer:CalculationMetadata></imaer:calculation>
                    <imaer:version><imaer:VersionMetadata><imaer:aeriusVersion>V1.1</imaer:aeriusVersion><imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion></imaer:VersionMetadata></imaer:version>
                </imaer:AeriusCalculatorMetadata>
            </imaer:metadata>
        """);
  }

  private static void writeReceptorPoint(final Writer writer, final int index) throws IOException {
    writer.write(String.format(Locale.ROOT, """
                <imaer:featureMember>
                    <imaer:ReceptorPoint receptorPointId="%d" gml:id="CP.%d">
                        <imaer:identifier><imaer:NEN3610ID><imaer:namespace>NL.IMAER</imaer:namespace><imaer:localId>CP.%d</imaer:localId></imaer:NEN3610ID></imaer:identifier>
                        <imaer:GM_Point><gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.%d.POINT"><gml:pos>%d.0 %d.0</gml:pos></gml:Point></imaer:GM_Point>
                        <imaer:representation><gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.REPR.%d"><gml:exterior><gml:LinearRing><gml:posList>137589.0 456305.0 137620.0 456251.0 137589.0 456197.0 137527.0 456197.0 137496.0 456251.0 137527.0 456305.0 137589.0 456305.0</gml:posList></gml:LinearRing></gml:exterior></gml:Polygon></imaer:representation>
                        <imaer:result><imaer:CalculationResult resultType="DEPOSITION" substance="NH3"><imaer:value>%s</imaer:value></imaer:CalculationResult></imaer:result>
                        <imaer:result><imaer:CalculationResult resultType="DEPOSITION" substance="NOX"><imaer:value>%s</imaer:value></imaer:CalculationResult></imaer:result>
                    </imaer:ReceptorPoint>
                </imaer:featureMember>
            """, index, index, index, index, 137558 + index, 456251 + index, index,
        Double.toString(8546.77 + index * 0.01), Double.toString(968.3 + index * 0.01)));
  }

  private static void writeGmlFooter(final Writer writer, final int receptorCount) throws IOException {
    for (int i = 1; i <= 2; i++) {
      final int id = receptorCount + i;
      writer.write(String.format(Locale.ROOT, """
              <imaer:featureMember>
                  <imaer:CalculationPoint gml:id="CP.%d">
                      <imaer:identifier><imaer:NEN3610ID><imaer:namespace>NL.IMAER</imaer:namespace><imaer:localId>CP.%d</imaer:localId></imaer:NEN3610ID></imaer:identifier>
                      <imaer:GM_Point><gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.%d.POINT"><gml:pos>207413.0 475162.0</gml:pos></gml:Point></imaer:GM_Point>
                      <imaer:label>Calc point %d</imaer:label>
                  </imaer:CalculationPoint>
              </imaer:featureMember>
          """, id, id, id, i));
    }
    writer.write("</imaer:FeatureCollectionCalculator>\n");
  }

  private static void forceGc() {
    System.gc();
    System.gc();
    try {
      Thread.sleep(50);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static long usedMemory() {
    final Runtime rt = Runtime.getRuntime();
    return rt.totalMemory() - rt.freeMemory();
  }
}
