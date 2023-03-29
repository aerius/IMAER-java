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
package nl.overheid.aerius.gml.v5_1.togml;

import java.util.List;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v5_1.measure.EmissionReduction;
import nl.overheid.aerius.gml.v5_1.measure.EmissionReductionProperty;
import nl.overheid.aerius.gml.v5_1.measure.SRM1RoadMeasure;
import nl.overheid.aerius.gml.v5_1.measure.SRM1RoadMeasureArea;
import nl.overheid.aerius.gml.v5_1.measure.SRM1RoadMeasureProperty;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasure;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 *
 */
final class NSLMeasure2GML {

  private final Geometry2GML geometry2gml;

  public NSLMeasure2GML(final Geometry2GML geometry2gml) {
    this.geometry2gml = geometry2gml;
  }

  /**
   * Convert a NSLMeasure to a GML object.
   * @param measure The measure to convert to a GML object.
   * @return the GML object representing the NSLMeasure.
   * @throws AeriusException when the objects could not be converted to GML correctly.
   */
  public SRM1RoadMeasureArea toGML(final CIMLKMeasureFeature feature) throws AeriusException {
    final SRM1RoadMeasureArea gmlMeasure = new SRM1RoadMeasureArea();
    final CIMLKMeasure measure = feature.getProperties();
    final String id = GMLIdUtil.toValidGmlId(measure.getGmlId(), GMLIdUtil.MEASURE_PREFIX);

    gmlMeasure.setGeometry(geometry2gml, feature.getGeometry());
    gmlMeasure.setId(id);
    gmlMeasure.setDescription(measure.getDescription());
    gmlMeasure.setLabel(measure.getLabel());
    gmlMeasure.setJurisdictionId(measure.getJurisdictionId());

    gmlMeasure.setMeasures(toGML(measure.getVehicleMeasures()));

    return gmlMeasure;
  }

  private List<SRM1RoadMeasureProperty> toGML(final List<StandardVehicleMeasure> vehicleMeasures) {
    return vehicleMeasures.stream()
        .map(this::toGMLRoadMeasure)
        .map(SRM1RoadMeasureProperty::new)
        .collect(Collectors.toList());
  }

  private SRM1RoadMeasure toGMLRoadMeasure(final StandardVehicleMeasure vehicleMeasure) {
    final SRM1RoadMeasure roadMeasure = new SRM1RoadMeasure();
    roadMeasure.setVehicleType(vehicleMeasure.getVehicleTypeCode());
    roadMeasure.setRoadType(vehicleMeasure.getRoadTypeCode());
    roadMeasure.setReductions(toGMLReductions(vehicleMeasure.getEmissionReductions()));
    return roadMeasure;
  }

  private List<EmissionReductionProperty> toGMLReductions(final List<nl.overheid.aerius.shared.domain.v2.base.EmissionReduction> reductions) {
    return reductions.stream()
        .map(this::toGMLReduction)
        .map(EmissionReductionProperty::new)
        .collect(Collectors.toList());
  }

  private EmissionReduction toGMLReduction(final nl.overheid.aerius.shared.domain.v2.base.EmissionReduction measure) {
    final EmissionReduction reduction = new EmissionReduction();
    reduction.setSubstance(measure.getSubstance());
    reduction.setFactor(measure.getFactor());
    return reduction;
  }

}
