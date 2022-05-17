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

import nl.overheid.aerius.shared.domain.meteo.Meteo;

/**
 * Calculation options related to the WNB theme.
 */
public class WNBCalculationOptions implements Serializable {

  private static final long serialVersionUID = 1L;

  private CalculationRoadOPS roadOPS = CalculationRoadOPS.DEFAULT;
  /**
   * If true apply the WNB 25km calculation limit. Default true for WNB.
   */
  private boolean useWNBMaxDistance;

  private boolean forceAggregation;

  private Meteo meteo;
  private boolean useReceptorHeights;
  private OPSOptions opsOptions;
  private String opsVersion;
  private String preSRMVersion;
  private String luVersion;
  private String depositionVelocityVersion;
  private String windveldenVersion;

  private boolean disableSubReceptors;

  /**
   * @return Returns true if WNB maximum distance calculation is to be applied.
   */
  public boolean isUseWNBMaxDistance() {
    return useWNBMaxDistance;
  }

  public void setUseWnbMaxDistance(final boolean uswWNBMaxDistance) {
    this.useWNBMaxDistance = uswWNBMaxDistance;
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
    return roadOPS;
  }

  public void setRoadOPS(final CalculationRoadOPS roadOPS) {
    this.roadOPS = roadOPS;
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

  public boolean isDisableSubReceptors() {
    return disableSubReceptors;
  }

  public void setDisableSubReceptors(final boolean disableSubReceptors) {
    this.disableSubReceptors = disableSubReceptors;
  }

  public OPSOptions getOpsOptions() {
    return opsOptions;
  }

  public void setOpsOptions(final OPSOptions opsOptions) {
    this.opsOptions = opsOptions;
  }

  public String getOpsVersion() {
    return opsVersion;
  }

  public void setOpsVersion(final String opsVersion) {
    this.opsVersion = opsVersion;
  }

  public String getPreSRMVersion() {
    return preSRMVersion;
  }

  public void setPreSRMVersion(final String preSRMVersion) {
    this.preSRMVersion = preSRMVersion;
  }

  public String getLuVersion() {
    return luVersion;
  }

  public void setLuVersion(final String luVersion) {
    this.luVersion = luVersion;
  }

  public String getDepositionVelocityVersion() {
    return depositionVelocityVersion;
  }

  public void setDepositionVelocityVersion(final String depositionVelocityVersion) {
    this.depositionVelocityVersion = depositionVelocityVersion;
  }

  public String getWindveldenVersion() {
    return windveldenVersion;
  }

  public void setWindveldenVersion(final String windveldenVersion) {
    this.windveldenVersion = windveldenVersion;
  }
}
