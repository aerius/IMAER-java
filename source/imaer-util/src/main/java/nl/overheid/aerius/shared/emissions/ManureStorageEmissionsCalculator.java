/*
 * Crown copyright
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
package nl.overheid.aerius.shared.emissions;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.manure.AbstractManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.CustomManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.StandardManureStorage;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class ManureStorageEmissionsCalculator {

  private final ManureStorageEmissionFactorSupplier manureStorageEmissionFactorSupplier;

  public ManureStorageEmissionsCalculator(final ManureStorageEmissionFactorSupplier manureStorageEmissionFactorSupplier) {
    this.manureStorageEmissionFactorSupplier = manureStorageEmissionFactorSupplier;
  }

  public Map<Substance, Double> updateEmissions(final ManureStorageEmissionSource manureStorageSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final AbstractManureStorage manureStorage : manureStorageSource.getSubSources()) {
      final Map<Substance, BigDecimal> storageEmissions = calculateEmissions(manureStorage);
      storageEmissions.forEach(
          (key, value) -> {
            manureStorage.getEmissions().put(key, value.doubleValue());
            summed.merge(key, value, BigDecimal::add);
          });
    }

    final Map<Substance, Double> result = new EnumMap<>(Substance.class);

    summed.forEach((key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final AbstractManureStorage manureStorage) throws AeriusException {
    final Map<Substance, BigDecimal> emissions;

    if (manureStorage instanceof CustomManureStorage) {
      emissions = calculateEmissions((CustomManureStorage) manureStorage);
    } else if (manureStorage instanceof StandardManureStorage) {
      emissions = calculateEmissions((StandardManureStorage) manureStorage);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown manurestorage type");
    }
    return emissions;
  }

  private static Map<Substance, BigDecimal> calculateEmissions(final CustomManureStorage manureStorage) throws AeriusException {
    final Map<Substance, Double> emissionFactors = manureStorage.getEmissionFactors();
    final FarmEmissionFactorType emissionFactorType = manureStorage.getFarmEmissionFactorType();

    return FarmSourceEmissionsUtil.calculate(manureStorage, emissionFactors, emissionFactorType);
  }

  private Map<Substance, BigDecimal> calculateEmissions(final StandardManureStorage manureStorage) throws AeriusException {
    final Map<Substance, Double> emissionFactors =
        manureStorageEmissionFactorSupplier.getManureStorageEmissionFactors(manureStorage.getManureStorageCode());
    final FarmEmissionFactorType emissionFactorType =
        manureStorageEmissionFactorSupplier.getManureStorageEmissionFactorType(manureStorage.getManureStorageCode());

    return FarmSourceEmissionsUtil.calculate(manureStorage, emissionFactors, emissionFactorType);
  }
}
