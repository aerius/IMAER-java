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
package nl.overheid.aerius.gml.base.source.farmland;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2Farmland<T extends IsGmlFarmlandEmissionSource> extends AbstractGML2Specific<T, FarmlandEmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Farmland.class);

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2Farmland(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public FarmlandEmissionSource convert(final T source) throws AeriusException {
    final FarmlandEmissionSource emissionSource = new FarmlandEmissionSource();
    for (final IsGmlProperty<IsGmlFarmlandActivity> landActivityProperty : source.getActivities()) {
      final IsGmlFarmlandActivity gmlActivity = landActivityProperty.getProperty();
      final AbstractFarmlandActivity activity = getFarmlandActivity(gmlActivity);
      activity.setActivityCode(gmlActivity.getCode());
      for (final IsGmlProperty<IsGmlEmission> emissionProperty : gmlActivity.getEmissions()) {
        final IsGmlEmission emission = emissionProperty.getProperty();
        activity.getEmissions().put(emission.getSubstance(), emission.getValue());
      }
      emissionSource.getSubSources().add(activity);
    }
    return emissionSource;
  }

  private static AbstractFarmlandActivity getFarmlandActivity(IsGmlFarmlandActivity gmlActivity) throws AeriusException {
    Class<?> subType = Arrays.stream(AbstractFarmlandActivity.class.getAnnotations())
        .filter(JsonSubTypes.class::isInstance)
        .map(JsonSubTypes.class::cast)
        .flatMap(jsonSubTypes -> Arrays.stream(jsonSubTypes.value()))
        .filter(v -> v.name().equals(gmlActivity.getCode()))
        .map(JsonSubTypes.Type::value)
        .filter(AbstractFarmlandActivity.class::isAssignableFrom)
        .findFirst()
        .orElseThrow(() -> {
          LOG.error("Don't know how to treat farmland type: {}", gmlActivity.getCode());
          return new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
        });

    try {
      Object farmLandActivity = subType.getDeclaredConstructor().newInstance();
      return (AbstractFarmlandActivity) farmLandActivity;
    } catch (final ReflectiveOperationException e) {
      LOG.error("Unexpected reflection error for farmland type: {}", gmlActivity.getCode(), e);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
  }
}
