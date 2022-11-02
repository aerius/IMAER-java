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

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.opengis.gml.v_3_2_1.ObjectFactory;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.Definitions;
import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.GMLVersionWriter;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_1.togml.GMLVersionWriterV51;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.scenario.IsScenario;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Class to create GML content from data objects.
 */
final class InternalGMLWriter {

  /**
   * Current GML version data is exported to.
   */
  static final AeriusGMLVersion LATEST_WRITER_VERSION = AeriusGMLVersion.V5_1;

  static final String LATEST_NAMESPACE = CalculatorSchema.NAMESPACE;

  private static final Logger LOG = LoggerFactory.getLogger(InternalGMLWriter.class);
  private static final String GML_ENCODING = StandardCharsets.UTF_8.name();

  // can't use the Context list of substances, as this includes NOXNH3.
  // IF YOU CHANGE THE ORDER A TEST WILL FAIL!
  private static final Substance[] EMISSION_SUBSTANCES = {Substance.NH3, Substance.NOX, Substance.PM10, Substance.NO2};
  // While PM25 isn't a valid emission substance (OPS at least can't handle it), it is a valid result substance.
  // NO2 can't be used by OPS, but should be usable for SMR2.
  // IF YOU CHANGE THE ORDER A TEST WILL FAIL!
  private static final Substance[] RESULT_SUBSTANCES = {Substance.NH3, Substance.NOX, Substance.PM10, Substance.NO2, Substance.PM25};

  private final GMLVersionWriter writer;
  private final ReferenceGenerator referenceGenerator;
  private final Boolean formattedOutput;

  InternalGMLWriter(final ReceptorGridSettings rgs, final ReferenceGenerator referenceGenerator, final Boolean formattedOutput) {
    writer = new GMLVersionWriterV51(rgs.getZoomLevel1(), GMLSchema.getSRSName(rgs.getEPSG().getSrid()));
    this.referenceGenerator = referenceGenerator;
    this.formattedOutput = formattedOutput;
  }

  GMLVersionWriter getWriter() {
    return writer;
  }

  /**
   * Write a list of emission sources to GML.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param sources Convert these sources to GML.
   * @param metaData Object containing the metadata like version, db-version and target year.
   * @throws AeriusException When the GML in the inputstream could not be converted to objects.
   */
  void writeEmissionSources(final OutputStream outputStream, final List<EmissionSourceFeature> sources, final MetaDataInput metaDataInput)
      throws AeriusException {
    final MetaData metaData = writer.metaData2GML(metaDataInput);
    final List<FeatureMember> featureMembers = emissionSourcesToFeatures(sources);
    toXMLString(outputStream, featureMembers, metaData, null);
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
  void writeEmissionSource(final OutputStream outputStream, final EmissionSourceFeature source) throws AeriusException {
    final FeatureMember featureMember = writer.source2GML(source, EMISSION_SUBSTANCES).get(0);

    toXMLString(outputStream, featureMember, true);
  }

  /**
   * Write a list of receptor points to GML.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param receptors Convert these receptor points to GML.
   * @param metaData Object containing the metadata like version, db-version and target year.
   * @throws AeriusException When the GML in the inputstream could not be converted to objects.
   */
  void writeAeriusPoints(final OutputStream outputStream, final List<CalculationPointFeature> receptors, final MetaDataInput metaDataInput)
      throws AeriusException {
    final List<FeatureMember> featureMembers = aeriusPointsToFeatures(receptors, Collections.emptyList());
    toXMLString(outputStream, featureMembers, metaDataInput == null ? null : writer.metaData2GML(metaDataInput), null);
  }

  /**
   * Convert a scenario to GML.
   *
   * @param outputStream The outputstream to use when writing the GML.
   * @param sources Convert these sources to GML.
   * @param aeriusPoints Convert these points to GML.
   * @param metaDataInput Object containing the information needed for GML metadata.
   * @param sectorId the sectorid, null for total results
   * @throws AeriusException When the objects could not be converted to GML.
   */
  void write(final OutputStream outputStream, final IsScenario scenario, final MetaDataInput metaDataInput) throws AeriusException {
    final List<FeatureMember> featureMembers = emissionSourcesToFeatures(scenario.getSources());
    featureMembers.addAll(buildingsToFeatures(scenario.getBuildings()));
    featureMembers.addAll(aeriusPointsToFeatures(scenario.getCalculationPoints(), scenario.getNslCorrections()));
    featureMembers.addAll(nslDispersionLinesToFeatures(scenario.getNslDispersionLines()));
    featureMembers.addAll(nslMeasuresToFeatures(scenario.getNslMeasures()));
    ensureUniqueIds(featureMembers);
    metaDataInput.setName(scenario.getName());
    metaDataInput.setSituationType(scenario.getSituationType());
    metaDataInput.setNettingFactor(scenario.getNettingFactor());
    final MetaData metaDataImpl = writer.metaData2GML(metaDataInput);
    referenceGenerator.updateReferenceIfNeeded(metaDataImpl.getReference()).ifPresent(newReference -> {
      metaDataImpl.setReference(newReference);
      metaDataInput.getScenarioMetaData().setReference(newReference);
    });
    final Definitions definitions = writer.definitions2GML(scenario.getDefinitions());
    toXMLString(outputStream, featureMembers, metaDataImpl, definitions);
  }

  private void toXMLString(final OutputStream outputStream, final List<FeatureMember> featureMembers, final MetaData metaData,
      final Definitions definitions) throws AeriusException {
    final FeatureCollection collection = writer.createFeatureCollection();
    collection.setFeatureMembers(featureMembers);
    collection.setMetaData(metaData);
    collection.setDefinitions(definitions);

    toXMLString(outputStream, collection, false);
  }

  void toXMLString(final OutputStream outputStream, final Object jaxbElement, final boolean fragment) throws AeriusException {
    try {
      // create context/marshaller based on our jaxbElement.
      final JAXBContext jaxbContext = JAXBContextCache.find(jaxbElement.getClass(), ObjectFactory.class);
      final Marshaller marshaller = jaxbContext.createMarshaller();

      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
      // set the targetNameSpaceLocation (namespace and actual location separated by a space, can be multiple combinations)
      marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, writer.getNameSpace() + " " + writer.getPublicSchemaLocation());
      marshaller.setProperty(Marshaller.JAXB_ENCODING, GML_ENCODING);

      if (fragment) {
        marshaller.marshal(
            new JAXBElement(
                new QName(writer.getNameSpace(), jaxbElement.getClass().getSimpleName()),
                jaxbElement.getClass(), jaxbElement),
            outputStream);
      } else {
        marshaller.marshal(jaxbElement, outputStream);
      }
    } catch (final JAXBException e) {
      LOG.error("Building GML failed", e);
      throw new AeriusException(ImaerExceptionReason.GML_CREATION_FAILED, e.getLinkedException().getMessage());
    }
  }

  List<FeatureMember> aeriusPointsToFeatures(final List<CalculationPointFeature> points, final List<NSLCorrection> corrections)
      throws AeriusException {
    final List<FeatureMember> featureMembers = new ArrayList<>(points.size());
    for (final CalculationPointFeature point : points) {
      featureMembers.add(writer.result2GML(point, RESULT_SUBSTANCES, corrections));
    }
    return featureMembers;
  }

  List<FeatureMember> emissionSourcesToFeatures(final List<EmissionSourceFeature> sources)
      throws AeriusException {
    final List<FeatureMember> featureMembers = new ArrayList<>();
    for (final EmissionSourceFeature source : sources) {
      featureMembers.addAll(writer.source2GML(source, EMISSION_SUBSTANCES));
    }
    return featureMembers;
  }

  List<FeatureMember> buildingsToFeatures(final List<BuildingFeature> buildings)
      throws AeriusException {
    final List<FeatureMember> featureMembers = new ArrayList<>(buildings.size());
    for (final BuildingFeature building : buildings) {
      featureMembers.add(writer.building2GML(building));
    }
    return featureMembers;
  }

  List<FeatureMember> nslMeasuresToFeatures(final List<NSLMeasureFeature> measures)
      throws AeriusException {
    final List<FeatureMember> featureMembers = new ArrayList<>(measures.size());
    for (final NSLMeasureFeature measure : measures) {
      featureMembers.add(writer.nslMeasure2GML(measure));
    }
    return featureMembers;
  }

  List<FeatureMember> nslDispersionLinesToFeatures(final List<NSLDispersionLineFeature> dispersionLines)
      throws AeriusException {
    final List<FeatureMember> featureMembers = new ArrayList<>(dispersionLines.size());
    for (final NSLDispersionLineFeature dispersionLine : dispersionLines) {
      featureMembers.add(writer.nslDispersionLine2GML(dispersionLine));
    }
    return featureMembers;
  }

  List<Object> nslCorrectionsToGmlObjects(final List<NSLCorrection> corrections)
      throws AeriusException {
    final List<Object> featureMembers = new ArrayList<>(corrections.size());
    for (final NSLCorrection correction : corrections) {
      featureMembers.add(writer.nslCorrection2GML(correction));
    }
    return featureMembers;
  }

  void ensureUniqueIds(final List<FeatureMember> featureMembers) {
    final Set<String> foundIds = new HashSet<>();
    for (final FeatureMember featureMember : featureMembers) {
      final String originalId = featureMember.getId();
      int version = 1;
      while (foundIds.contains(featureMember.getId())) {
        final String newId = originalId + "_" + version;
        featureMember.setId(newId);
        version++;
      }
      foundIds.add(featureMember.getId());
    }
  }

}
