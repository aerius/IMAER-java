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
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.overheid.aerius.shared.ImaerConstants;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class FarmLodgingEmissionsCalculator {

  /**
   * Conversion from percentage to fraction.
   */
  private static final int PERCENTAGE_TO_FRACTION = 100;

  private static final double ROUND_FODDER = 5.0;

  private final FarmLodgingEmissionFactorSupplier emissionFactorSupplier;

  public FarmLodgingEmissionsCalculator(final FarmLodgingEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final FarmLodgingEmissionSource lodgingSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final FarmLodging lodging : lodgingSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForLodging = calculateEmissions(lodging);
      emissionsForLodging.forEach(
          (key, value) -> lodging.getEmissions().put(key, value.doubleValue()));
      emissionsForLodging.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final FarmLodging lodging) throws AeriusException {
    final Map<Substance, BigDecimal> emissions;
    if (lodging instanceof CustomFarmLodging) {
      emissions = calculateEmissions((CustomFarmLodging) lodging);
    } else if (lodging instanceof StandardFarmLodging) {
      emissions = calculateEmissions((StandardFarmLodging) lodging);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown farmlodging type");
    }

    if (lodging.getFarmEmissionFactorType() ==  FarmEmissionFactorType.PER_ANIMAL_PER_DAY) {
      emissions.replaceAll((substance, emission) -> emission.multiply(BigDecimal.valueOf(lodging.getNumberOfDays())));
    }
    return emissions;
  }

  Map<Substance, BigDecimal> calculateEmissions(final CustomFarmLodging customLodging) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfAnimals = BigDecimal.valueOf(customLodging.getNumberOfAnimals());
    customLodging.getEmissionFactors().forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfAnimals)));
    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardFarmLodging standardLodging) {
    // Get emission of base lodging type
    final Map<Substance, BigDecimal> emissions = getFlatEmission(standardLodging);

    // Add emissions based on additional systems
    processAdditionalSystems(emissions, standardLodging.getAdditionalLodgingSystems());
    // Reduce emission based on reductive systems
    processReductiveSystems(emissions, standardLodging.getReductiveLodgingSystems());
    // Reduce emission based on fodder measures
    processFodderMeasures(emissions, standardLodging.getFodderMeasures(), standardLodging.getFarmLodgingCode());
    return emissions;
  }

  private Map<Substance, BigDecimal> getFlatEmission(final StandardFarmLodging standardLodging) {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final BigDecimal numberOfAnimals = BigDecimal.valueOf(standardLodging.getNumberOfAnimals());
    final Map<Substance, Double> emissionFactors = determineEmissionFactorsLodging(standardLodging);
    emissionFactors.forEach(
        (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(numberOfAnimals)));
    return emissions;
  }

  private Map<Substance, Double> determineEmissionFactorsLodging(final StandardFarmLodging standardLodging) {
    final String lodgingCode = standardLodging.getFarmLodgingCode();
    if (emissionFactorSupplier.canLodgingEmissionFactorsBeConstrained(lodgingCode)
        && isAnyLodgingSystemScrubber(standardLodging)) {
      return emissionFactorSupplier.getLodgingConstrainedEmissionFactors(lodgingCode);
    } else {
      return emissionFactorSupplier.getLodgingEmissionFactors(lodgingCode);
    }
  }

  private boolean isAnyLodgingSystemScrubber(final StandardFarmLodging standardLodging) {
    final boolean hasAdditionalScrubber = standardLodging.getAdditionalLodgingSystems().stream()
        .map(LodgingSystem::getLodgingSystemCode)
        .anyMatch(emissionFactorSupplier::isAdditionalSystemScrubber);
    return hasAdditionalScrubber || standardLodging.getReductiveLodgingSystems().stream()
        .map(LodgingSystem::getLodgingSystemCode)
        .anyMatch(emissionFactorSupplier::isReductiveSystemScrubber);
  }

  private void processAdditionalSystems(final Map<Substance, BigDecimal> emissions, final List<AdditionalLodgingSystem> additionalSystems) {
    for (final AdditionalLodgingSystem additionalSystem : additionalSystems) {
      final Map<Substance, Double> emissionFactors = emissionFactorSupplier
          .getAdditionalSystemEmissionFactors(additionalSystem.getLodgingSystemCode());
      final BigDecimal numberOfAnimals = BigDecimal.valueOf(additionalSystem.getNumberOfAnimals());
      emissionFactors.forEach(
          (key, value) -> emissions.merge(key, BigDecimal.valueOf(value).multiply(numberOfAnimals), (v1, v2) -> v1.add(v2)));
    }
  }

  private void processReductiveSystems(final Map<Substance, BigDecimal> emissions, final List<ReductiveLodgingSystem> reductiveSystems) {
    for (final ReductiveLodgingSystem reductiveSystem : reductiveSystems) {
      final Map<Substance, Double> remainingFractions = emissionFactorSupplier
          .getReductiveSystemRemainingFractions(reductiveSystem.getLodgingSystemCode());
      for (final Entry<Substance, BigDecimal> entry : emissions.entrySet()) {
        final Substance substance = entry.getKey();
        if (remainingFractions.containsKey(substance)) {
          // Remaining emission = original emission * remaining fraction
          final BigDecimal reducedValue = entry.getValue().multiply(BigDecimal.valueOf(remainingFractions.get(substance)));
          entry.setValue(reducedValue);
        }
      }
    }
  }

  private void processFodderMeasures(final Map<Substance, BigDecimal> emissions, final List<LodgingFodderMeasure> fodderMeasures,
      final String farmLodgingCode) {
    final Map<Substance, BigDecimal> reductionFactorsFodder = getReductionFactorCombinedFodderMeasures(fodderMeasures, farmLodgingCode);
    for (final Entry<Substance, BigDecimal> entry : emissions.entrySet()) {
      final Substance substance = entry.getKey();
      if (reductionFactorsFodder.containsKey(substance)) {
        // Remaining emission = original emission * (1 - reduction factor)
        final BigDecimal reducedValue = entry.getValue().multiply(BigDecimal.ONE.subtract(reductionFactorsFodder.get(substance)));
        entry.setValue(reducedValue);
      }
    }
  }

  Map<Substance, BigDecimal> getReductionFactorCombinedFodderMeasures(final List<LodgingFodderMeasure> fodderMeasures,
      final String farmLodgingCode) {
    final Map<Substance, BigDecimal> reductionFactors = new EnumMap<>(Substance.class);
    if (fodderMeasures.size() == 1) {
      final Map<Substance, Double> remainderFractions = emissionFactorSupplier
          .getFodderRemainingFractionTotal(fodderMeasures.get(0).getFodderMeasureCode());
      remainderFractions.forEach(
          (key, value) -> reductionFactors.put(key, BigDecimal.ONE.subtract(BigDecimal.valueOf(value))));
    } else if (!fodderMeasures.isEmpty()) {
      // Only measures with the same animal category as the main category may be inputted by the user. Thus we can assume
      // the ammonia proportions are the same for each measure; we'll simply take 'm from the first.
      final String firstFodderMeasureCode = fodderMeasures.get(0).getFodderMeasureCode();
      if (emissionFactorSupplier.canFodderApplyToLodging(firstFodderMeasureCode, farmLodgingCode)) {
        final Map<Substance, BigDecimal> emissionReductionFractionFloor = toBigDecimalMap(
            emissionFactorSupplier.getFodderProportionFloor(firstFodderMeasureCode, farmLodgingCode));
        final Map<Substance, BigDecimal> emissionReductionFractionCellar = toBigDecimalMap(
            emissionFactorSupplier.getFodderProportionCellar(firstFodderMeasureCode, farmLodgingCode));
        fodderMeasures.forEach(fodderMeasure -> {
          multiply(emissionReductionFractionFloor, emissionFactorSupplier.getFodderRemainingFractionFloor(fodderMeasure.getFodderMeasureCode()));
          multiply(emissionReductionFractionCellar, emissionFactorSupplier.getFodderRemainingFractionCellar(fodderMeasure.getFodderMeasureCode()));
        });
        for (final Entry<Substance, BigDecimal> entry : emissionReductionFractionFloor.entrySet()) {
          // Reduction factor = 1 - remainder fraction floor - remainder fraction cellar
          final BigDecimal reductionFactor = BigDecimal.ONE.subtract(entry.getValue()).subtract(emissionReductionFractionCellar.get(entry.getKey()));
          final BigDecimal roundedReductionFactor = BigDecimal.valueOf(
              Math.round(reductionFactor.doubleValue() * PERCENTAGE_TO_FRACTION / ROUND_FODDER))
              .divide(BigDecimal.valueOf(PERCENTAGE_TO_FRACTION)).multiply(BigDecimal.valueOf(ROUND_FODDER)).setScale(2);
          reductionFactors.put(entry.getKey(), roundedReductionFactor);
        }
      }
    }

    return reductionFactors;
  }

  private Map<Substance, BigDecimal> toBigDecimalMap(final Map<Substance, Double> original) {
    final Map<Substance, BigDecimal> result = new EnumMap<>(Substance.class);
    original.forEach(
        (key, value) -> result.put(key, BigDecimal.valueOf(value)));
    return result;
  }

  private void multiply(final Map<Substance, BigDecimal> targetMap, final Map<Substance, Double> multiplyWith) {
    multiplyWith.forEach(
        (keyMultiplyWith, valueMultiplyWith) -> targetMap.computeIfPresent(keyMultiplyWith,
            (key, original) -> original.multiply(BigDecimal.valueOf(valueMultiplyWith))));
  }

}
