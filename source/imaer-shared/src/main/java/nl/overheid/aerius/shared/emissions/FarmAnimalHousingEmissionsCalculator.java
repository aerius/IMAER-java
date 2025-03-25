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
package nl.overheid.aerius.shared.emissions;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Emissions calculator for farm animal housing.
 */
public class FarmAnimalHousingEmissionsCalculator {

  /**
   * Fraction that main system emission reduction is constrained by if there are any scrubbers.
   */
  private static final BigDecimal SCRUBBER_CONSTRAIN_FRACTION = BigDecimal.valueOf(0.3);

  private final FarmAnimalHousingEmissionFactorSupplier emissionFactorSupplier;

  public FarmAnimalHousingEmissionsCalculator(final FarmAnimalHousingEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final FarmAnimalHousingEmissionSource animalHousingSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final FarmAnimalHousing animalHousing : animalHousingSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForAnimalHousing = calculateEmissions(animalHousing);

      emissionsForAnimalHousing.forEach((key, value) -> animalHousing.getEmissions().put(key, value.doubleValue()));
      emissionsForAnimalHousing.forEach((key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);

    summed.forEach((key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final FarmAnimalHousing animalHousing) throws AeriusException {
    final Map<Substance, BigDecimal> emissions;

    if (animalHousing instanceof CustomFarmAnimalHousing) {
      emissions = calculateEmissions((CustomFarmAnimalHousing) animalHousing);
    } else if (animalHousing instanceof StandardFarmAnimalHousing) {
      emissions = calculateEmissions((StandardFarmAnimalHousing) animalHousing);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown animal housing type");
    }

    return emissions;
  }

  Map<Substance, BigDecimal> calculateEmissions(final CustomFarmAnimalHousing customAnimalHousing) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfAnimals = BigDecimal.valueOf(customAnimalHousing.getNumberOfAnimals());
    final BigDecimal periodFactor = customAnimalHousing.getFarmEmissionFactorType() == FarmEmissionFactorType.PER_ANIMAL_PER_DAY
        ? BigDecimal.valueOf(customAnimalHousing.getNumberOfDays())
        : BigDecimal.ONE;

    customAnimalHousing.getEmissionFactors().forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfAnimals).multiply(periodFactor)));
    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardFarmAnimalHousing standardAnimalHousing) throws AeriusException {
    // Get emission of base housing type
    final Map<Substance, BigDecimal> emissions = getFlatEmission(standardAnimalHousing);

    // Reduce emissions based on additional systems
    processAdditionalSystems(emissions, standardAnimalHousing.getAdditionalSystems(), standardAnimalHousing.getAnimalHousingCode());

    final BigDecimal periodFactor =
        emissionFactorSupplier
            .getAnimalHousingEmissionFactorType(standardAnimalHousing.getAnimalHousingCode()) == FarmEmissionFactorType.PER_ANIMAL_PER_DAY
                ? BigDecimal.valueOf(standardAnimalHousing.getNumberOfDays())
                : BigDecimal.ONE;

    emissions.forEach((key, value) -> emissions.put(key, value.multiply(periodFactor)));
    return emissions;
  }

  private Map<Substance, BigDecimal> getFlatEmission(final StandardFarmAnimalHousing standardAnimalHousing) {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final BigDecimal numberOfAnimals = BigDecimal.valueOf(standardAnimalHousing.getNumberOfAnimals());
    final Map<Substance, Double> emissionFactors = determineEmissionFactorsAnimalHousing(standardAnimalHousing);

    emissionFactors.forEach((key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(numberOfAnimals)));
    return emissions;
  }

  private Map<Substance, Double> determineEmissionFactorsAnimalHousing(final StandardFarmAnimalHousing standardAnimalHousing) {
    final String animalHousingCode = standardAnimalHousing.getAnimalHousingCode();
    final Map<Substance, Double> housingEmissionFactors = emissionFactorSupplier.getAnimalHousingEmissionFactors(animalHousingCode);

    if (isAnyAdditionalSystemScrubber(standardAnimalHousing)) {
      final String basicHousingCode = emissionFactorSupplier.getAnimalBasicHousingCode(animalHousingCode);
      return basicHousingCode == null || basicHousingCode.equals(animalHousingCode)
          ? housingEmissionFactors
          : obtainConstrainedEmissionFactors(housingEmissionFactors, emissionFactorSupplier.getAnimalHousingEmissionFactors(basicHousingCode));
    } else {
      return housingEmissionFactors;
    }
  }

  private boolean isAnyAdditionalSystemScrubber(final StandardFarmAnimalHousing standardHousing) {
    for (final AdditionalHousingSystem additionalSystem : standardHousing.getAdditionalSystems()) {
      if (isAdditionalSystemScrubber(additionalSystem)) {
        return true;
      }
    }
    return false;
  }

  private boolean isAdditionalSystemScrubber(final AdditionalHousingSystem additionalSystem) {
    return (additionalSystem instanceof StandardAdditionalHousingSystem
        && emissionFactorSupplier
            .isAdditionalHousingSystemAirScrubber(((StandardAdditionalHousingSystem) additionalSystem).getAdditionalSystemCode()))
        || (additionalSystem instanceof CustomAdditionalHousingSystem
            && ((CustomAdditionalHousingSystem) additionalSystem).isAirScrubber());
  }

  /**
   * Dutch calculation rule:
   * When an air scrubber is applied as an additional system in combination with a housing system
   * where the emission factor is lower than 30% of the emission factor for ammonia for a basic housing system:
   * ammonia emission = ammonia emissionfactor basic housing system x (100% - reductionpercentage ammonia scrubber) x 0.3
   *
   * In essence this means the ammonia emissionfactor of the housing system is constrained to a maximum of 30% of the associated
   * basic housing system.
   */
  private static Map<Substance, Double> obtainConstrainedEmissionFactors(final Map<Substance, Double> systemEmissionFactors,
      final Map<Substance, Double> basicEmissionFactors) {
    final Map<Substance, Double> constrained = new EnumMap<>(Substance.class);
    systemEmissionFactors.forEach((substance, factor) -> {
      if (substance == Substance.NH3) {
        final BigDecimal basicFactor = BigDecimal.valueOf(basicEmissionFactors.getOrDefault(substance, factor)).multiply(SCRUBBER_CONSTRAIN_FRACTION);
        constrained.put(substance, Math.max(factor, basicFactor.doubleValue()));
      } else {
        constrained.put(substance, factor);
      }
    });
    return constrained;
  }

  private void processAdditionalSystems(final Map<Substance, BigDecimal> emissions, final List<AdditionalHousingSystem> additionalSystems,
      final String animalHousingCode) throws AeriusException {
    for (final AdditionalHousingSystem additionalSystem : additionalSystems) {
      final Map<Substance, BigDecimal> reductionFractions;
      if (additionalSystem instanceof StandardAdditionalHousingSystem) {
        reductionFractions = toBigDecimalMap(emissionFactorSupplier.getAdditionalHousingSystemReductionFractions(
            ((StandardAdditionalHousingSystem) additionalSystem).getAdditionalSystemCode(), animalHousingCode));
      } else if (additionalSystem instanceof CustomAdditionalHousingSystem) {
        reductionFractions = toBigDecimalMap(
            ((CustomAdditionalHousingSystem) additionalSystem).getEmissionReductionFactors());
      } else {
        throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown animal additional housing type");
      }

      for (final Entry<Substance, BigDecimal> entry : emissions.entrySet()) {
        final Substance substance = entry.getKey();

        if (reductionFractions.containsKey(substance)) {
          // Remaining emission = original emission * remaining fraction = original emission * (1 - reduction fraction)
          final BigDecimal reducedValue = entry.getValue().multiply(BigDecimal.ONE.subtract(reductionFractions.get(substance)));
          entry.setValue(reducedValue);
        }
      }
    }
  }

  private static Map<Substance, BigDecimal> toBigDecimalMap(final Map<Substance, Double> original) {
    final Map<Substance, BigDecimal> result = new EnumMap<>(Substance.class);

    original.forEach((key, value) -> result.put(key, BigDecimal.valueOf(value)));
    return result;
  }

}
