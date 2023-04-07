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
package nl.overheid.aerius.gml.base.measure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasure;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Utility class to convert from GML objects (specific for CIMLK measures).
 */
public class GML2CIMLKMeasure {

  private static final Logger LOG = LoggerFactory.getLogger(GML2CIMLKMeasure.class);

  private final GMLConversionData conversionData;
  private final GML2Geometry gml2geometry;

  /**
   * @param conversionData The data to use when converting. Should be filled.
   */
  public GML2CIMLKMeasure(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
    this.gml2geometry = new GML2Geometry(conversionData.getSrid());
  }

  public List<CIMLKMeasureFeature> fromGML(final List<FeatureMember> members) {
    final List<CIMLKMeasureFeature> measures = new ArrayList<>();
    for (final FeatureMember member : members) {
      if (member instanceof IsGmlSRM1RoadMeasureArea) {
        try {
          measures.add(fromGML((IsGmlSRM1RoadMeasureArea) member));
        } catch (final AeriusException e) {
          conversionData.getErrors().add(e);
        }
      }
    }
    return measures;
  }

  /**
   * Convert GML road measure areas to CIMLKMeasure.
   * @param gmlMeasure The GML-representation of a measure area.
   * @return The CIMLK measure.
   * @throws AeriusException When an exception occurs during parsing.
   */
  public CIMLKMeasureFeature fromGML(final IsGmlSRM1RoadMeasureArea gmlMeasure) throws AeriusException {
    final CIMLKMeasureFeature feature = new CIMLKMeasureFeature();
    feature.setGeometry(toGeometry(gmlMeasure));
    final CIMLKMeasure measure = new CIMLKMeasure();
    measure.setGmlId(gmlMeasure.getId());
    measure.setDescription(gmlMeasure.getDescription());
    measure.setLabel(gmlMeasure.getLabel());
    measure.setJurisdictionId(gmlMeasure.getJurisdictionId());
    measure.setVehicleMeasures(fromGMLMeasures(gmlMeasure.getMeasures()));
    feature.setProperties(measure);
    return feature;
  }

  private Polygon toGeometry(final IsGmlSRM1RoadMeasureArea origin) throws AeriusException {
    final Polygon polygon;
    try {
      final Geometry geometry = gml2geometry.getGeometry(origin);
      if (geometry instanceof Polygon) {
        polygon = (Polygon) geometry;
      } else {
        LOG.trace("Unexpected geometry type after conversion ({}), throwing AeriusException", geometry);
        throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, origin.getId());
      }
    } catch (final AeriusException e) {
      LOG.trace("Geometry exception, rethrown as AeriusException", e);
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, origin.getId());
    }
    return polygon;
  }

  private List<StandardVehicleMeasure> fromGMLMeasures(final List<? extends IsGmlProperty<IsGmlSRM1RoadMeasure>> roadMeasureProperties) {
    return roadMeasureProperties.stream()
        .map(IsGmlProperty::getProperty)
        .map(this::fromGML)
        .collect(Collectors.toList());
  }

  private StandardVehicleMeasure fromGML(final IsGmlSRM1RoadMeasure measure) {
    final StandardVehicleMeasure vehicleMeasure = new StandardVehicleMeasure();
    vehicleMeasure.setVehicleTypeCode(measure.getVehicleType());
    vehicleMeasure.setRoadTypeCode(measure.getRoadType());
    measure.getReductions().stream()
        .map(IsGmlProperty::getProperty)
        .map(this::fromGML)
        .forEach(vehicleMeasure::addEmissionReduction);
    return vehicleMeasure;
  }

  private nl.overheid.aerius.shared.domain.v2.base.EmissionReduction fromGML(final IsGMLEmissionReduction gmlReduction) {
    final nl.overheid.aerius.shared.domain.v2.base.EmissionReduction reduction = new nl.overheid.aerius.shared.domain.v2.base.EmissionReduction();
    reduction.setSubstance(gmlReduction.getSubstance());
    reduction.setFactor(gmlReduction.getFactor());
    return reduction;
  }

}
