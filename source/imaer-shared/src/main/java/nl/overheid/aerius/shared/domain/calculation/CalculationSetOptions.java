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
package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;

/**
 * Data class for calculation options.
 */
public class CalculationSetOptions implements Serializable {

  private static final long serialVersionUID = 5L;

  private int calculationSetOptionsId;
  private CalculationMethod calculationMethod = CalculationMethod.FORMAL_ASSESSMENT;
  private CalculationJobType calculationJobType;
  private double calculateMaximumRange;
  private boolean useInCombinationArchive;
  private final ArrayList<Substance> substances = new ArrayList<>();
  private final Set<EmissionResultKey> emissionResultKeys = new HashSet<>();
  private WNBCalculationOptions owN2000CalculationOptions = new WNBCalculationOptions();
  private RBLCalculationOptions rblCalculationOptions = new RBLCalculationOptions();
  private NCACalculationOptions ncaCalculationOptions = new NCACalculationOptions();
  private Serializable experimentalOptions;

  /**
   * Controls the stacking of Point sources.
   */
  private boolean stacking = true;
  private ConnectSuppliedOptions connectSuppliedOptions;

  public int getCalculationSetOptionsId() {
    return calculationSetOptionsId;
  }

  public void setCalculationSetOptionsId(final int calculationSetOptionsId) {
    this.calculationSetOptionsId = calculationSetOptionsId;
  }

  public CalculationMethod getCalculationMethod() {
    return calculationMethod;
  }

  public void setCalculationMethod(final CalculationMethod calculationMethod) {
    this.calculationMethod = calculationMethod;
  }

  public CalculationJobType getCalculationJobType() {
    return calculationJobType;
  }

  public void setCalculationJobType(final CalculationJobType calculationJobType) {
    this.calculationJobType = calculationJobType;
  }

  public ArrayList<Substance> getSubstances() {
    return substances;
  }

  public boolean isStacking() {
    return stacking;
  }

  public void setStacking(final boolean stacking) {
    this.stacking = stacking;
  }

  public Set<EmissionResultKey> getEmissionResultKeys() {
    return emissionResultKeys;
  }

  /**
   * Returns true if the maximum range value is used by the calculation method. Meaning those types use the maximum range value to determine the
   * distance to calculate.
   * @return true if relevant
   */
  public boolean isMaximumRangeRelevant() {
    return calculationMethod == CalculationMethod.NATURE_AREA || calculationMethod == CalculationMethod.QUICK_RUN;
  }

  /**
   * @return The maximum range for the calculation (in meters)
   */
  public double getCalculateMaximumRange() {
    return calculateMaximumRange;
  }

  /**
   * @param calculateMaximumRange The maximum range to set (in meters)
   */
  public void setCalculateMaximumRange(final double calculateMaximumRange) {
    this.calculateMaximumRange = calculateMaximumRange;
  }

  /**
   * @return Boolean indicating if the in combination archive should be used for an in combination calculation job.
   */
  public boolean isUseInCombinationArchive() {
    return useInCombinationArchive;
  }

  /**
   * @param useInCombinationArchive Boolean indicating if the in combination archive should be set for an in combination calculation job.
   */
  public void setUseInCombinationArchive(final boolean useInCombinationArchive) {
    this.useInCombinationArchive = useInCombinationArchive;
  }

  public ConnectSuppliedOptions getConnectSuppliedOptions() {
    return connectSuppliedOptions;
  }

  public void setConnectSuppliedOptions(final ConnectSuppliedOptions connectSuppliedOptions) {
    this.connectSuppliedOptions = connectSuppliedOptions;
  }

  public OwN2000CalculationOptions getOwN2000CalculationOptions() {
    return owN2000CalculationOptions;
  }

  /**
   * @deprecated replace calls with {@link #getOwN2000CalculationOptions()}
   */
  @Deprecated
  @JsonProperty("owN2000CalculationOptions")
  public WNBCalculationOptions getWnbCalculationOptions() {
    return owN2000CalculationOptions;
  }

  public RBLCalculationOptions getRblCalculationOptions() {
    return rblCalculationOptions;
  }

  public NCACalculationOptions getNcaCalculationOptions() {
    return ncaCalculationOptions;
  }

  /**
   * Experimental options for experimenting/testing purposes.
   * Can be used to add another set of options without having to change the imaer-shared library.
   *
   * Not intended for use in production code.
   */
  public Serializable getExperimentalOptions() {
    return experimentalOptions;
  }

  public void setExperimentalOptions(final Serializable experimentalOptions) {
    this.experimentalOptions = experimentalOptions;
  }

  @Override
  public String toString() {
    return "CalculationSetOptions [calculationSetOptionsId=" + calculationSetOptionsId + ", calculationMethod=" + calculationMethod
        + ", calculateMaximumRange=" + calculateMaximumRange + ", substances=" + substances + ", emissionResultKeys=" + emissionResultKeys
        + ", stacking=" + stacking + ", connectSuppliedOptions=" + connectSuppliedOptions + ", own2000CalculationOptions=" + owN2000CalculationOptions
        + ", rblCalculationOptions=" + rblCalculationOptions + ", ncaCalculationOptions=" + ncaCalculationOptions + "]";
  }
}
