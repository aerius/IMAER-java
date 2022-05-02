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
import java.util.List;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.characteristics.IsGmlCustomDiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomDiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomDiurnalVariationType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Utility class to convert from GML objects (specific for Definitions).
 */
public class GML2Definitions {

  private final GMLConversionData conversionData;

  /**
   * @param conversionData The data to use when converting. Should be filled.
   */
  public GML2Definitions(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
  }

  /**
   * Convert GML definitions to a domain object.
   * @param gmlDefinitions The GML-representation of definitions.
   * @return The domain object Definitions.
   */
  public nl.overheid.aerius.shared.domain.v2.scenario.Definitions fromGML(final Definitions gmlDefinitions) {
    final nl.overheid.aerius.shared.domain.v2.scenario.Definitions definitions = new nl.overheid.aerius.shared.domain.v2.scenario.Definitions();
    try {
      if (gmlDefinitions != null) {
        final List<CustomDiurnalVariation> customDiurnalVariations = new ArrayList<>();
        for (final IsGmlCustomDiurnalVariation gmlCustomDiurnalVariation : gmlDefinitions.getCustomDiurnalVariations()) {
          customDiurnalVariations.add(convert(gmlCustomDiurnalVariation));
        }
        definitions.setCustomDiurnalVariations(customDiurnalVariations);
      }
    } catch (final AeriusException e) {
      conversionData.getErrors().add(e);
    }
    return definitions;
  }

  private CustomDiurnalVariation convert(final IsGmlCustomDiurnalVariation gmlCustomDiurnalVariation) throws AeriusException {
    final CustomDiurnalVariation diurnalVariation = new CustomDiurnalVariation();
    final CustomDiurnalVariationType customType = convertCustomDiurnalVariationType(gmlCustomDiurnalVariation.getCustomType(),
        gmlCustomDiurnalVariation.getValues());
    diurnalVariation.setGmlId(gmlCustomDiurnalVariation.getId());
    diurnalVariation.setLabel(gmlCustomDiurnalVariation.getLabel());
    diurnalVariation.setType(customType);
    diurnalVariation.setValues(gmlCustomDiurnalVariation.getValues().stream().map(x -> (double) x).collect(Collectors.toList()));
    return diurnalVariation;
  }

  private CustomDiurnalVariationType convertCustomDiurnalVariationType(final String gmlCustomType, final List<Double> values)
      throws AeriusException {
    final CustomDiurnalVariationType customType = CustomDiurnalVariationType.safeValueOf(gmlCustomType);
    if (customType == null) {
      throw new AeriusException(ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_TYPE_UNKNOWN, gmlCustomType);
    } else if (customType.getExpectedNumberOfValues() != values.size()) {
      throw new AeriusException(ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_INVALID_COUNT,
          String.valueOf(customType.getExpectedNumberOfValues()),
          String.valueOf(values.size()));
    }
    final double valuesSum = customType.sum(values);
    final double expectedSum = customType.getExpectedTotalNumberOfValues();
    if (Math.abs(expectedSum - valuesSum) >= CustomDiurnalVariation.ALLOWED_EPSILON) {
      throw new AeriusException(ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_INVALID_SUM,
          String.valueOf(expectedSum),
          String.valueOf(valuesSum));
    }
    return customType;
  }

}
