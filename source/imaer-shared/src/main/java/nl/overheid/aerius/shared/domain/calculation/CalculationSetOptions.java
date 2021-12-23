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

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.meteo.Meteo;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;

/**
 * Data class for calculation options.
 */
public class CalculationSetOptions implements Serializable {

  private static final long serialVersionUID = 4L;

  private int calculationSetOptionsId;
  private CalculationType calculationType = CalculationType.PERMIT;
  private double calculateMaximumRange;
  private final ArrayList<Substance> substances = new ArrayList<>();
  private final Set<EmissionResultKey> emissionResultKeys = new HashSet<>();
  private boolean forceAggregation;
  private CalculationRoadOPS roadOPS = CalculationRoadOPS.DEFAULT;
  private int monitorSrm2Year;
  /**
   * If true apply the WNB 25km calculation limit. Default true for WNB.
   */
  private boolean useWNBMaxDistance;
  /**
   * Controls the stacking of Point sources.
   */
  private boolean stacking = true;
  private Meteo meteo;
  private boolean useReceptorHeights;
  private ConnectSuppliedOptions connectSuppliedOptions;
  private OPSOptions opsOptions;

  public int getCalculationSetOptionsId() {
    return calculationSetOptionsId;
  }

  public void setCalculationSetOptionsId(final int calculationSetOptionsId) {
    this.calculationSetOptionsId = calculationSetOptionsId;
  }

  public CalculationType getCalculationType() {
    return calculationType;
  }

  public ArrayList<Substance> getSubstances() {
    return substances;
  }

  public void setUseWnbMaxDistance(final boolean uswWNBMaxDistance) {
    this.useWNBMaxDistance = uswWNBMaxDistance;
  }

  /**
   * @return Returns true if WNB maximum distance calculation is to be applied.
   */
  public boolean isUseWNBMaxDistance() {
    return useWNBMaxDistance;
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

  public void setCalculationType(final CalculationType calculationType) {
    this.calculationType = calculationType;
  }

  /**
   * Returns true if the maximum range value is used by the calculation type. Meaning those types use the maximum range value to determine the
   * distance to calculate.
   * @return true if relevant
   */
  public boolean isMaximumRangeRelevant() {
    return calculationType == CalculationType.NATURE_AREA || calculationType == CalculationType.RADIUS;
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

  public boolean isForceAggregation() {
    return forceAggregation;
  }

  /**
   * By default some sectors are exempt from aggregation. This forces aggregation even for those sectors.
   */
  public void setForceAggregation(final boolean forceAggregation) {
    this.forceAggregation = forceAggregation;
  }

  public CalculationRoadOPS getRoadOPS() {
    if (calculationType != CalculationType.CUSTOM_POINTS) {
      return CalculationRoadOPS.DEFAULT;
    }
    return roadOPS;
  }

  public void setRoadOPS(final CalculationRoadOPS roadOPS) {
    this.roadOPS = roadOPS;
  }

  public boolean isIncludeMonitorSrm2Network() {
    return monitorSrm2Year > 0;
  }

  public int getMonitorSrm2Year() {
    return monitorSrm2Year;
  }

  public void setMonitorSrm2Year(final int monitorSrm2Year) {
    this.monitorSrm2Year = monitorSrm2Year;
  }

  public Meteo getMeteo() {
    return meteo;
  }

  public void setMeteo(final Meteo meteo) {
    this.meteo = meteo;
  }

  public boolean isUseReceptorHeights() {
    return useReceptorHeights;
  }

  public void setUseReceptorHeights(final boolean useReceptorHeights) {
    this.useReceptorHeights = useReceptorHeights;
  }

  public ConnectSuppliedOptions getConnectSuppliedOptions() {
    return connectSuppliedOptions;
  }

  public void setConnectSuppliedOptions(final ConnectSuppliedOptions connectSuppliedOptions) {
    this.connectSuppliedOptions = connectSuppliedOptions;
  }

  public OPSOptions getOpsOptions() {
    return opsOptions;
  }

  public void setOpsOptions(final OPSOptions opsOptions) {
    this.opsOptions = opsOptions;
  }

  @Override
  public String toString() {
    return "CalculationSetOptions [calculationSetOptionsId=" + calculationSetOptionsId + ", calculationType=" + calculationType
        + ", calculateMaximumRange=" + calculateMaximumRange + ", substances=" + substances + ", emissionResultKeys=" + emissionResultKeys
        + ", forceAggregation=" + forceAggregation + ", roadOPS=" + roadOPS + ", monitorSrm2Year=" + monitorSrm2Year + ", stacking=" + stacking
        + ", meteo=" + meteo + ", useReceptorHeights=" + useReceptorHeights + ", opsOptions=" + opsOptions + "]";
  }
}
