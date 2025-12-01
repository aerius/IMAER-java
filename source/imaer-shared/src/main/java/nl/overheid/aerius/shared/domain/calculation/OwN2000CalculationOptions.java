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
 * Calculation options related to the OWN2000 theme.
 */
public class OwN2000CalculationOptions implements Serializable {

  private static final long serialVersionUID = 3L;

  private CalculationRoadOPS roadOPS = CalculationRoadOPS.DEFAULT;
  private PermitLowerBoundType permitLowerBoundType;
  private double permitLowerBoundValue;
  private boolean useMaxDistance;
  private boolean forceAggregation;
  private Meteo meteo;
  private boolean useReceptorHeights;
  private OPSOptions opsOptions;
  private String opsVersion;
  private String srmVersion;

  private SubReceptorsMode subReceptorsMode;
  private Integer subReceptorZoomLevel;
  private boolean splitSubReceptorWork;
  private int splitSubReceptorWorkDistance;
  private boolean repositionSubReceptors = true;

  /**
   * @return Returns the lower bound type used for permit calculations.
   */
  public PermitLowerBoundType getPermitLowerBoundType() {
    return permitLowerBoundType;
  }

  /**
   * Set the lower bound type used for permit calculations.
   */
  public void setPermitLowerBoundType(final PermitLowerBoundType permitLowerBoundType) {
    this.permitLowerBoundType = permitLowerBoundType;
  }

  /**
   * @return Returns the lower bound value used for permit calculations.
   */
  public double getPermitLowerBoundValue() {
    return permitLowerBoundValue;
  }

  /**
   * Set the lower bound value used for permit calculations.
   */
  public void setPermitLowerBoundValue(final double permitLowerBoundValue) {
    this.permitLowerBoundValue = permitLowerBoundValue;
  }

  /**
   * @return Returns true if OwN2000 maximum distance 25km calculation is to be applied.
   */
  public boolean isUseMaxDistance() {
    return useMaxDistance;
  }

  /**
   * Set if the OWN2000 25km calculation distance limit should be applied. Default true for OWN2000.
   */
  public void setUseMaxDistance(final boolean useMaxDistance) {
    this.useMaxDistance = useMaxDistance;
  }

  /**
   * @return Returns true if aggregation should be applied to all sectors.
   */
  public boolean isForceAggregation() {
    return forceAggregation;
  }

  /**
   * By default some sectors are exempt from aggregation. This forces aggregation even for those sectors.
   */
  public void setForceAggregation(final boolean forceAggregation) {
    this.forceAggregation = forceAggregation;
  }

  /**
   * @return Returns the way roads should be calculated with OPS.
   */
  public CalculationRoadOPS getRoadOPS() {
    return roadOPS;
  }

  /**
   * Set the way roads should be calculated with OPS.
   *
   * @param roadOPS
   */
  public void setRoadOPS(final CalculationRoadOPS roadOPS) {
    this.roadOPS = roadOPS;
  }

  /**
   * @return Returns the Meteo configuration to be used. Only relevant for SRM calculations.
   */
  public Meteo getMeteo() {
    return meteo;
  }

  /**
   * Set the Meteo configuration to be used. Only relevant for SRM calculations.
   *
   * @param meteo
   */
  public void setMeteo(final Meteo meteo) {
    this.meteo = meteo;
  }

  /**
   * @return Returns if supplied receptor point heights should be used. If false any supplied receptor height will be removed.
   */
  public boolean isUseReceptorHeights() {
    return useReceptorHeights;
  }

  /**
   * Set if supplied receptor heights should be used.
   *
   * @param useReceptorHeights
   */
  public void setUseReceptorHeights(final boolean useReceptorHeights) {
    this.useReceptorHeights = useReceptorHeights;
  }

  /**
   * @return Returns the way sub receptors should be used.
   */
  public SubReceptorsMode getSubReceptorsMode() {
    return subReceptorsMode;
  }

  /**
   * Set the way sub receptors should be used.
   *
   * @param subReceptorsMode
   */
  public void setSubReceptorsMode(final SubReceptorsMode subReceptorsMode) {
    this.subReceptorsMode = subReceptorsMode;
  }

  /**
   * @return Returns the zoom level that should be used to calculate subreceptors on. Default is zoom level 1.
   */
  public Integer getSubReceptorZoomLevel() {
    return subReceptorZoomLevel;
  }

  /**
   * Set the sub receptor zoom level to use to calculate subreceptors on.
   *
   * @param subReceptorsZoomLevel
   */
  public void setSubReceptorZoomLevel(final Integer subReceptorsZoomLevel) {
    this.subReceptorZoomLevel = subReceptorsZoomLevel;
  }

  /**
   * @return Returns true if OPS calculations should be split in two for subreceptor calculations.
   */
  public boolean isSplitSubReceptorWork() {
    return splitSubReceptorWork;
  }

  /**
   * Set if OPS calculations should be split in two calculations for subreceptors.
   * This options is only valid to be true under certain conditions, like subreceptors are calculated.
   *
   * @param splitSubReceptorWork
   */
  public void setSplitSubReceptorWork(final boolean splitSubReceptorWork) {
    this.splitSubReceptorWork = splitSubReceptorWork;
  }

  /**
   * @return Returns the distance sources should be split on when subreceptors work should be split.
   */
  public int getSplitSubReceptorWorkDistance() {
    return splitSubReceptorWorkDistance;
  }

  /**
   * Set the distance sources should be split on. Distance is in meters.
   *
   * @param distance
   */
  public void setSplitSubReceptorWorkDistance(final int distance) {
    this.splitSubReceptorWorkDistance = distance;
  }

  public boolean isRepositionSubReceptors() {
    return repositionSubReceptors;
  }

  public void setRepositionSubReceptors(final boolean repositionSubReceptors) {
    this.repositionSubReceptors = repositionSubReceptors;
  }

  /**
   * @return Returns OPS specific options.
   */
  public OPSOptions getOpsOptions() {
    return opsOptions;
  }

  /**
   * Set OPS specific options.
   *
   * @param opsOptions
   */
  public void setOpsOptions(final OPSOptions opsOptions) {
    this.opsOptions = opsOptions;
  }

  /**
   * @return Returns the OPS version that should be used for calculation.
   */
  public String getOpsVersion() {
    return opsVersion;
  }

  /**
   * Set the OPS version that should be used for calculation.
   *
   * @param opsVersion
   */
  public void setOpsVersion(final String opsVersion) {
    this.opsVersion = opsVersion;
  }

  /**
   * @return Returns the SRM version that should be used for calculation.
   */
  public String getSrmVersion() {
    return srmVersion;
  }

  /**
   * Set the SRM version that should be used for calculation.
   *
   * @param srmVersion
   */
  public void setSrmVersion(final String srmVersion) {
    this.srmVersion = srmVersion;
  }
}
