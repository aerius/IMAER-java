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
package nl.overheid.aerius.gml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.gml.base.StringUtils;
import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.scenario.IsScenario;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasure;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.ImaerFileUtil;

/**
 * Builder class to create GML content from data objects.
 */
public class GMLWriter {

  /**
   * Mimetype to be used for GML.
   */
  public static final String GML_MIMETYPE = "application/gml+xml";
  /**
   * @deprecated name of GML file should be determined base on context not a generic name.
   */
  @Deprecated
  public static final String CURRENT_GML = "Huidige_Situatie.gml";
  /**
   * @deprecated name of GML file should be determined base on context not a generic name.
   */
  @Deprecated
  public static final String PROPOSED_GML = "Nieuwe_Situatie.gml";

  /**
   * Current GML version data is exported to.
   */
  public static final AeriusGMLVersion LATEST_WRITER_VERSION = InternalGMLWriter.LATEST_WRITER_VERSION;

  /**
   * Current namespace data is exported to.
   */
  public static final String LATEST_WRITER_NAMESPACE = InternalGMLWriter.LATEST_NAMESPACE;

  protected static final Logger LOG = LoggerFactory.getLogger(GMLWriter.class);
  private static final String GML_EXTENSION = ".gml";
  private static final String GML_FILE_PREFIX = "AERIUS";

  private final ReceptorGridSettings receptorGridSettings;
  private final ReferenceGenerator referenceGenerator;
  private Boolean formattedOutput = Boolean.TRUE;

  public GMLWriter(final ReceptorGridSettings rgs, final ReferenceGenerator referenceGenerator) {
    this.receptorGridSettings = rgs;
    this.referenceGenerator = referenceGenerator;
  }

  /**
   * Set to true to generate formatted GML.
   *
   * @param formattedOutput if true formatted output will be generated
   */
  public void setFormattedOutput(final Boolean formattedOutput) {
    this.formattedOutput = formattedOutput;
  }

  /**
   * Get a map of the GML representation (String) for each {@link EmissionSourceFeature} object.
   *
   * @param sources List of sources
   * @param year the year to be used to calculate the emissions on the sources
   * @throws AeriusException When exception occurred generating the GML.
   */
  public Map<EmissionSourceFeature, String> getGmlByEmissionSource(final List<EmissionSourceFeature> sources) throws AeriusException {
    final Map<EmissionSourceFeature, String> gmlPerSource = new HashMap<>();
    final InternalGMLWriter writer = createInternalWriter();
    for (final EmissionSourceFeature source : sources) {
      final FeatureMember feature = writer.emissionSourcesToFeatures(Collections.singletonList(source)).get(0);
      setGMLFeatureOnObject(writer, feature, gml -> gmlPerSource.put(source, gml));
    }
    return gmlPerSource;
  }

  /**
   * Sets the GML representation (String) on each {@link CalculationPointFeature} object.
   *
   * @param points List of points
   * @throws AeriusException When exception occurred generating the GML.
   */
  public Map<CalculationPointFeature, String> getGMLByCalculationPoint(final List<CalculationPointFeature> points) throws AeriusException {
    final Map<CalculationPointFeature, String> gmlPerCalculationPoint = new HashMap<>();
    final InternalGMLWriter writer = createInternalWriter();
    for (final CalculationPointFeature point : points) {
      final FeatureMember feature = writer.aeriusPointsToFeatures(Collections.singletonList(point), Collections.emptyList()).get(0);

      setGMLFeatureOnObject(writer, feature, gml -> gmlPerCalculationPoint.put(point, gml));
    }
    return gmlPerCalculationPoint;
  }

  /**
   * Sets the GML representation (String) on each {@link NSLDispersionLineFeature} object.
   *
   * @param dispersionLines List of NSL dispersion lines
   * @throws AeriusException When exception occurred generating the GML.
   */
  public Map<NSLDispersionLineFeature, String> getGmlByNslDispersionLines(final List<NSLDispersionLineFeature> dispersionLines)
      throws AeriusException {
    final Map<NSLDispersionLineFeature, String> gmlPerDispersionLine = new HashMap<>();
    final InternalGMLWriter writer = createInternalWriter();
    for (final NSLDispersionLineFeature dispersionLine : dispersionLines) {
      final FeatureMember feature = writer.nslDispersionLinesToFeatures(Collections.singletonList(dispersionLine)).get(0);

      setGMLFeatureOnObject(writer, feature, gml -> gmlPerDispersionLine.put(dispersionLine, gml));
    }
    return gmlPerDispersionLine;
  }

  /**
   * Sets the GML representation (String) on each {@link NSLMeasure} object.
   *
   * @param measures List of NSL measures
   * @throws AeriusException When exception occurred generating the GML.
   */
  public Map<NSLMeasureFeature, String> getGmlByNslMeasures(final List<NSLMeasureFeature> measures) throws AeriusException {
    final Map<NSLMeasureFeature, String> gmlPerMeasure = new HashMap<>();
    final InternalGMLWriter writer = createInternalWriter();
    for (final NSLMeasureFeature measure : measures) {
      final FeatureMember feature = writer.nslMeasuresToFeatures(Collections.singletonList(measure)).get(0);

      setGMLFeatureOnObject(writer, feature, gml -> gmlPerMeasure.put(measure, gml));
    }
    return gmlPerMeasure;
  }

  /**
   * Sets the GML representation (String) on each {@link NSLCorrection} object.
   *
   * @param corrections List of NSL corrections
   * @throws AeriusException When exception occurred generating the GML.
   */
  public Map<NSLCorrection, String> getGmlByNslCorrections(final List<NSLCorrection> corrections) throws AeriusException {
    final Map<NSLCorrection, String> gmlPerCorrection = new HashMap<>();
    final InternalGMLWriter writer = createInternalWriter();
    for (final NSLCorrection correction : corrections) {
      final Object jaxbObject = writer.nslCorrectionsToGmlObjects(Collections.singletonList(correction)).get(0);

      setGMLFeatureOnObject(writer, jaxbObject, gml -> gmlPerCorrection.put(correction, gml));
    }
    return gmlPerCorrection;
  }

  private void setGMLFeatureOnObject(final InternalGMLWriter writer, final Object feature, final Consumer<String> consumer)
      throws AeriusException {
    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
      writer.toXMLString(os, feature, true);
      consumer.accept(os.toString(StandardCharsets.UTF_8.name()));
    } catch (final IOException e) {
      // catch any exception and put them in a GML exception.
      LOG.error("Internal error occurred while encoding feature to GML: {}", feature, e);
      throw new AeriusException(ImaerExceptionReason.GML_CREATION_FAILED);
    }
  }

  /**
   * @deprecated use the write method with theme parameter
   */
  @Deprecated
  public File writeToFile(final File dir, final IsScenario scenario, final MetaDataInput metaData, final int fileId,
      final Optional<String> fileNamePart, final Optional<Date> fileNameDatePart) throws AeriusException {
    return writeToFile(dir, Theme.WNB, scenario, metaData, fileId, fileNamePart, fileNameDatePart);
  }

  /**
   * Build GML and write it directly to file.
   * The file will be generated in the supplied directory.
   *
   * @param dir The directory to write the file to.
   * @param theme theme this gml is generated for
   * @param scenario Scenario containing all scenario related data
   * @param metaData The metadata to write for this file.
   * @param fileId The ID that should be reflected in the filename.
   * @param fileNamePart The optional filename that will be incorporated in the resulting file's name.
   * @return The file generated.
   * @throws AeriusException When exception occurred generating the GML.
   */
  public File writeToFile(final File dir, final Theme theme, final IsScenario scenario, final MetaDataInput metaData, final int fileId,
      final Optional<String> fileNamePart, final Optional<Date> fileNameDatePart) throws AeriusException {
    final Path file = new File(dir, getFileName(scenario, fileId, fileNamePart, fileNameDatePart)).toPath();
    try (final OutputStream outputStream = Files.newOutputStream(file)) {
      write(outputStream, theme, scenario, metaData);
    } catch (final IOException e) {
      // catch any exception and put them in a GML exception.
      LOG.error("Internal error occurred.", e);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }

    LOG.info("File generated for {} in {} to {}.", scenario.getName(), metaData, file.toString());
    return file.toFile();
  }

  protected String getFileName(final IsScenario scenario, final int fileId, final Optional<String> fileNamePart,
      final Optional<Date> fileNameDatePart) {
    // filename has to be unique in ZIP, so assure the filename is unique for comparison modes by using the sourcelist id.
    final List<String> fileParts = new ArrayList<>();
    fileParts.add(Integer.toString(fileId));
    if (!StringUtils.isEmpty(scenario.getName())) {
      fileParts.add(scenario.getName());
    }
    if (fileNamePart.isPresent()) {
      fileParts.add(fileNamePart.get());
    }
    return ImaerFileUtil.getFileName(GML_FILE_PREFIX, GML_EXTENSION,
        fileParts.stream().collect(Collectors.joining("_")), fileNameDatePart.orElse(null));
  }

  /**
   * Write a list of emission sources to GML.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param theme theme this gml is generated for
   * @param sources Convert these sources to GML.
   * @param metaData Object containing the metadata like version, db-version and target year.
   * @throws AeriusException When the GML in the inputstream could not be converted to objects.
   */
  public void writeEmissionSources(final OutputStream outputStream, final Theme theme, final List<EmissionSourceFeature> sources,
      final MetaDataInput metaDataInput) throws AeriusException {
    createInternalWriter().writeEmissionSources(theme, outputStream, sources, metaDataInput);
  }

  /**
   * Convert an emission source to GML. Will always assume the source to yield one featureMember.
   * If it yields more, it will automatically use the first featureMember.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param source Convert source to GML.
   * @param year The year to use for emission calculations.
   * @throws AeriusException When the source could not be converted to GML.
   */
  public void writeEmissionSource(final OutputStream outputStream, final EmissionSourceFeature source) throws AeriusException {
    createInternalWriter().writeEmissionSource(outputStream, source);
  }

  /**
   * Write a list of receptor points to GML.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param receptors Convert these receptor points to GML.
   * @param metaData Object containing the metadata like version, db-version and target year.
   * @throws AeriusException When the GML in the inputstream could not be converted to objects.
   */
  public void writeAeriusPoints(final OutputStream outputStream, final Theme theme, final List<CalculationPointFeature> receptors,
      final MetaDataInput metaDataInput) throws AeriusException {
    createInternalWriter().writeAeriusPoints(outputStream, theme, receptors, metaDataInput);
  }

  /**
   * @deprecated use the write method with theme parameter
   */
  @Deprecated
  public void write(final OutputStream outputStream, final IsScenario scenario, final MetaDataInput metaDataInput)
      throws AeriusException {
    write(outputStream, Theme.WNB, scenario, metaDataInput);
  }

  /**
   * Convert a scenario to GML.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param theme theme this gml is generated for
   * @param scenario Convert this scenario to GML.
   * @param metaDataInput Object containing the information needed for GML metadata.
   * @throws AeriusException When the objects could not be converted to GML.
   */
  public void write(final OutputStream outputStream, final Theme theme, final IsScenario scenario, final MetaDataInput metaDataInput)
      throws AeriusException {
    createInternalWriter().write(outputStream, theme, scenario, metaDataInput);
  }

  private InternalGMLWriter createInternalWriter() {
    return new InternalGMLWriter(receptorGridSettings, referenceGenerator, formattedOutput);
  }

}
