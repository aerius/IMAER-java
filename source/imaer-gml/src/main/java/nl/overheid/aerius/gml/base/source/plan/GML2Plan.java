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
package nl.overheid.aerius.gml.base.source.plan;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2Plan<T extends IsGmlPlanEmissionSource> extends AbstractGML2Specific<T, GenericEmissionSource> {

  private static final int DEFAULT_SECTOR = 9999;
  private static final String DESCRIPTION_PREFIX = "Plan";

  private final GML2Geometry gml2Geometry;

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2Plan(final GMLConversionData conversionData) {
    super(conversionData);
    this.gml2Geometry = new GML2Geometry(conversionData.getSrid());
  }

  @Override
  public GenericEmissionSource convert(final T source) throws AeriusException {
    for (final IsGmlProperty<IsGmlPlan> planProperty : source.getPlans()) {
      final IsGmlPlan plan = planProperty.getProperty();

      final GenericEmissionSource newSource = new GenericEmissionSource();
      newSource.setGmlId(source.getId() + "_" + source.getPlans().indexOf(planProperty));
      newSource.setSectorId(DEFAULT_SECTOR);
      newSource.setLabel(constructLabel(source.getLabel(), plan.getDescription()));

      final String planCode = plan.getCode();
      validatePlanCode(planCode, source.getId());
      newSource.setCharacteristics(getCharacteristics(planCode));
      newSource.setEmissions(getConversionData().determinePlanActivityEmissions(planCode, plan.getAmount()));

      final EmissionSourceFeature feature = new EmissionSourceFeature();
      feature.setProperties(newSource);
      feature.setGeometry(gml2Geometry.getGeometry(source));
      getConversionData().getExtraSources().add(feature);
    }
    return null;
  }

  private void validatePlanCode(final String code, final String sourceId) throws AeriusException {
    if (getConversionData().isInvalidPlanActivityCode(code)) {
      throw new AeriusException(ImaerExceptionReason.GML_UNKNOWN_PLAN_CODE, sourceId, code);
    }
  }

  private OPSSourceCharacteristics getCharacteristics(final String code) {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    final OPSSourceCharacteristics planCharacteristics = getConversionData().determinePlanActivityCharacteristics(code);
    if (planCharacteristics != null) {
      planCharacteristics.copyTo(characteristics);
    }
    return characteristics;
  }

  private String constructLabel(final String sourceLabel, final String subSourceDescription) {
    return constructLabelOf(DESCRIPTION_PREFIX, sourceLabel, subSourceDescription);
  }

}
