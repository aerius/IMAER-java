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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Performance and memory benchmark for the receptor filtering implementation.
 * Disabled by default — run manually to compare before/after implementation changes.
 *
 * <p>Usage: run each test method, note the printed metrics, then switch implementation
 * and re-run to compare.
 */
@Disabled("Performance benchmark — run manually")
class ReceptorFilteringPerformanceTest {

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

    System.out.println();
    System.out.println("=== ReceptorFilteringReader File-Based Benchmark ===");
    System.out.println("Memory columns show sampled heap usage DURING filtering (not before/after delta).");
    System.out.println("If the sliding window is bounded, Mem Min-Max should stay in a fixed band as input grows.");
    System.out.printf("%-12s %-12s %-15s %-15s %-12s %-12s %-12s%n",
        "Receptors", "Input MB", "Avg Time ms", "Throughput MB/s", "Mem Min MB", "Mem Avg MB", "Mem Max MB");

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
      // Aggregate memory samples across iterations
      long sampleMin = Long.MAX_VALUE;
      long sampleMax = Long.MIN_VALUE;
      long sampleSum = 0;
      long sampleCount = 0;

      for (int i = 0; i < iterations; i++) {
        forceGc();

        final long start = System.nanoTime();
        final long[] memSamples = consumeFilteredFileWithMemorySampling(gmlFile);
        final long elapsed = System.nanoTime() - start;

        totalTimeNanos += elapsed;

        // memSamples: [min, max, sum, count, outputChars]
        if (memSamples[0] < sampleMin) {
          sampleMin = memSamples[0];
        }
        if (memSamples[1] > sampleMax) {
          sampleMax = memSamples[1];
        }
        sampleSum += memSamples[2];
        sampleCount += memSamples[3];

        assertTrue(memSamples[4] < inputSizeBytes, "Filtered output should be smaller than input");
      }

      final double avgTimeMs = ((double) totalTimeNanos / iterations) / 1_000_000.0;
      final double inputMb = inputSizeBytes / (1024.0 * 1024.0);
      final double throughputMBs = inputMb / (avgTimeMs / 1000.0);

      if (sampleCount > 0) {
        System.out.printf("%-12d %-12.1f %-15.2f %-15.2f %-12.2f %-12.2f %-12.2f%n",
            count, inputMb, avgTimeMs, throughputMBs,
            sampleMin / (1024.0 * 1024.0),
            (sampleSum / sampleCount) / (1024.0 * 1024.0),
            sampleMax / (1024.0 * 1024.0));
      } else {
        System.out.printf("%-12d %-12.1f %-15.2f %-15.2f %-12s %-12s %-12s%n",
            count, inputMb, avgTimeMs, throughputMBs, "N/A", "N/A", "N/A");
      }
    }
    System.out.println();
  }

  // --- Helper methods ---

  /**
   * Reads through the filter and samples memory every 500 read() calls.
   *
   * @return long array: [sampleMin, sampleMax, sampleSum, sampleCount, outputChars]
   */
  private long[] consumeFilteredFileWithMemorySampling(final Path gmlFile) throws IOException {
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
          if (mem < sampleMin) {
            sampleMin = mem;
          }
          if (mem > sampleMax) {
            sampleMax = mem;
          }
          sampleSum += mem;
          sampleCount++;
        }
      }
    }
    return new long[] {sampleMin, sampleMax, sampleSum, sampleCount, totalChars};
  }

  // --- GML generation: writes directly to file/writer, never holding full content in memory ---

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
    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
    writer.write("<imaer:FeatureCollectionCalculator xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
    writer.write("xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:gml=\"http://www.opengis.net/gml/3.2\" ");
    writer.write("xmlns:imaer=\"http://imaer.aerius.nl/6.0\" gml:id=\"NL.IMAER.Collection\" ");
    writer.write("xsi:schemaLocation=\"http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd\">\n");
    writer.write("    <imaer:metadata>\n");
    writer.write("        <imaer:AeriusCalculatorMetadata>\n");
    writer.write("            <imaer:project><imaer:ProjectMetadata><imaer:year>2013</imaer:year></imaer:ProjectMetadata></imaer:project>\n");
    writer.write("            <imaer:calculation><imaer:CalculationMetadata><imaer:method>NATURE_AREA</imaer:method>");
    writer.write("<imaer:substance>NOX</imaer:substance><imaer:substance>NH3</imaer:substance>");
    writer.write("<imaer:resultType>DEPOSITION</imaer:resultType></imaer:CalculationMetadata></imaer:calculation>\n");
    writer.write("            <imaer:version><imaer:VersionMetadata><imaer:aeriusVersion>V1.1</imaer:aeriusVersion>");
    writer.write("<imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion></imaer:VersionMetadata></imaer:version>\n");
    writer.write("        </imaer:AeriusCalculatorMetadata>\n");
    writer.write("    </imaer:metadata>\n");
  }

  private static void writeReceptorPoint(final Writer writer, final int index) throws IOException {
    writer.write("    <imaer:featureMember>\n");
    writer.write("        <imaer:ReceptorPoint receptorPointId=\"" + index + "\" gml:id=\"CP." + index + "\">\n");
    writer.write("            <imaer:identifier><imaer:NEN3610ID><imaer:namespace>NL.IMAER</imaer:namespace>");
    writer.write("<imaer:localId>CP." + index + "</imaer:localId></imaer:NEN3610ID></imaer:identifier>\n");
    writer.write("            <imaer:GM_Point><gml:Point srsName=\"urn:ogc:def:crs:EPSG::28992\" gml:id=\"CP." + index + ".POINT\">");
    writer.write("<gml:pos>" + (137558 + index) + ".0 " + (456251 + index) + ".0</gml:pos></gml:Point></imaer:GM_Point>\n");
    writer.write("            <imaer:representation><gml:Polygon srsName=\"urn:ogc:def:crs:EPSG::28992\" gml:id=\"NL.IMAER.REPR." + index + "\">");
    writer.write("<gml:exterior><gml:LinearRing><gml:posList>137589.0 456305.0 137620.0 456251.0 137589.0 456197.0 ");
    writer.write("137527.0 456197.0 137496.0 456251.0 137527.0 456305.0 137589.0 456305.0</gml:posList>");
    writer.write("</gml:LinearRing></gml:exterior></gml:Polygon></imaer:representation>\n");
    writer.write("            <imaer:result><imaer:CalculationResult resultType=\"DEPOSITION\" substance=\"NH3\">");
    writer.write("<imaer:value>" + (8546.77 + index * 0.01) + "</imaer:value></imaer:CalculationResult></imaer:result>\n");
    writer.write("            <imaer:result><imaer:CalculationResult resultType=\"DEPOSITION\" substance=\"NOX\">");
    writer.write("<imaer:value>" + (968.3 + index * 0.01) + "</imaer:value></imaer:CalculationResult></imaer:result>\n");
    writer.write("        </imaer:ReceptorPoint>\n");
    writer.write("    </imaer:featureMember>\n");
  }

  private static void writeGmlFooter(final Writer writer, final int receptorCount) throws IOException {
    for (int i = 1; i <= 2; i++) {
      final int id = receptorCount + i;
      writer.write("    <imaer:featureMember>\n");
      writer.write("        <imaer:CalculationPoint gml:id=\"CP." + id + "\">\n");
      writer.write("            <imaer:identifier><imaer:NEN3610ID><imaer:namespace>NL.IMAER</imaer:namespace>");
      writer.write("<imaer:localId>CP." + id + "</imaer:localId></imaer:NEN3610ID></imaer:identifier>\n");
      writer.write("            <imaer:GM_Point><gml:Point srsName=\"urn:ogc:def:crs:EPSG::28992\" gml:id=\"CP." + id + ".POINT\">");
      writer.write("<gml:pos>207413.0 475162.0</gml:pos></gml:Point></imaer:GM_Point>\n");
      writer.write("            <imaer:label>Calc point " + i + "</imaer:label>\n");
      writer.write("        </imaer:CalculationPoint>\n");
      writer.write("    </imaer:featureMember>\n");
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
