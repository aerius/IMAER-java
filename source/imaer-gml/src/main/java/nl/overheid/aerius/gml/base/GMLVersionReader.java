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
package nl.overheid.aerius.gml.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

/**
 * Interface to specific AERIUS GML version readers.
 */
public interface GMLVersionReader {

  /**
   * Get the {@link EmissionSourceFeature} objects from the GML data objects.
   * @param members The GML data objects to convert.
   * @return The list of EmissionSourceFeature objects
   */
  List<EmissionSourceFeature> sourcesFromGML(List<FeatureMember> members);

  /**
   * Get the {@link CalculationPointFeature} objects from the GML data objects.
   * @param members GML data objects to convert
   * @param includeResults if true also read results from GML.
   * @return new AeriusPoint object
   */
  List<CalculationPointFeature> calculationPointsFromGML(List<FeatureMember> members, boolean includeResults);

  /**
   * Get the {@link CIMLKMeasureFeature} objects from the GML data objects.
   * @param members The GML data objects to convert.
   * @return The list of CIMLKMeasure objects
   */
  default List<CIMLKMeasureFeature> cimlkMeasuresFromGML(final List<FeatureMember> members) {
    return Collections.emptyList();
  }

  /**
   * Get the {@link CIMLKDispersionLineFeature} objects from the GML data objects.
   * @param members The GML data objects to convert.
   * @return The list of CIMLKDispersionLine objects
   */
  default List<CIMLKDispersionLineFeature> cimlkDispersionLinesFromGML(final List<FeatureMember> members) {
    return Collections.emptyList();
  }

  /**
   * Get the {@link CIMLKCorrection} objects from the GML data objects.
   * @param members The GML data objects to convert.
   * @return The list of CIMLKCorrection objects
   */
  default List<CIMLKCorrection> cimlkCorrectionsFromGML(final List<FeatureMember> members) {
    return Collections.emptyList();
  }

  /**
   * Get the {@link BuildingFeature} objects from the GML data objects.
   * @param members The GML data objects to convert.
   * @return The list of BuildingFeature objects
   */
  default List<BuildingFeature> buildingsFromGML(final List<FeatureMember> members) {
    return new ArrayList<>();
  }

  /**
   * Get the Definition object from the GML data object.
   * @param definitions The GML data object to convert
   * @return The domain object that was converted.
   */
  default nl.overheid.aerius.shared.domain.v2.scenario.Definitions definitionsFromGML(final Definitions definitions) {
    return new nl.overheid.aerius.shared.domain.v2.scenario.Definitions();
  }

}
