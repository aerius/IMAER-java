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
package nl.overheid.aerius.shared.domain.ops;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * OPS source
 */
public class OPSSourceCharacteristics implements Serializable {

  private static final long serialVersionUID = 1L;

  private HeatContentType heatContentType;

  /**
   * heatContent - heat content (MW)
   */
  private double heatContent;

  /**
   * emissionTemperature
   */
  private double emissionTemperature;

  /**
   * outflowDiameter
   */
  private double outflowDiameter;

  /**
   * outflowVelocity
   */
  private double outflowVelocity;

  /**
   * OutflowDirection - Default value OutflowDirectionType.VERTICAL_FORCED
   */
  private OutflowDirectionType outflowDirection;

  /**
   * emissionHeight - source emission height (m)
   */
  private double emissionHeight;

  /**
   * buildInfluence - enable length width and height fields
   */
  private boolean buildingInfluence;

  /**
   * buildingHeight - height of the building (m)
   */
  private double buildingHeight;

  /**
   * buildingLength - height of the building (m)
   */
  private double buildingLength;

  /**
   * buildingWidth - width of the building (m)
   */
  private double buildingWidth;

  /**
   * buildingOrientation - width of the building (degrees)
   */
  private Integer buildingOrientation;

  /**
   * diameter - source diameter (m)
   */
  private int diameter;

  /**
   * spread - vertical spread of source height(s) (m)
   */
  private double spread;

  /**
   * Diurnal Variation - specification for diurnal variation
   */
  private DiurnalVariation diurnalVariation;

  /**
   * particleSizeDistribution - code for distribution of particle sizes (0 for gasses, > 0 for particulate matter)
   */
  private int particleSizeDistribution;

  public OPSSourceCharacteristics() {
    setHeatContentType(HeatContentType.NOT_FORCED);
    setEmissionTemperature(OPSLimits.AVERAGE_SURROUNDING_TEMPERATURE);
    setDiurnalVariation(DiurnalVariation.UNKNOWN);
    setOutflowDiameter(OPSLimits.SCOPE_NO_BUILDING_OUTFLOW_DIAMETER_MINIMUM);
    setOutflowDirection(OutflowDirectionType.VERTICAL);
    setBuildingHeight(OPSLimits.SCOPE_BUILDING_HEIGHT_MINIMUM);
    setBuildingLength(OPSLimits.SCOPE_BUILDING_LENGTH_MINIMUM);
    setBuildingWidth(OPSLimits.SCOPE_BUILDING_WIDTH_MINIMUM);
    setBuildingOrientation((int) OPSLimits.SCOPE_BUILDING_ORIENTATION_MINIMUM);
  }

  /**
   * @param heatContent height content (MW)
   * @param emissionHeight source height (m)
   * @param diameter source diameter (m)
   * @param spread vertical spread of source height(s) (m)
   * @param diurnalVariation diurnal variation specification
   * @param particleSizeDistribution code for particle-size distribution (>= 0)
   */
  public OPSSourceCharacteristics(final double heatContent, final double emissionHeight, final int diameter, final double spread,
      final DiurnalVariation diurnalVariation, final int particleSizeDistribution) {
    this();
    this.heatContent = heatContent;
    this.emissionHeight = emissionHeight;
    this.diameter = diameter;
    this.spread = spread;
    setDiurnalVariation(diurnalVariation);
    this.particleSizeDistribution = particleSizeDistribution;
  }

  /**
   * Copies object.
   *
   * @return new instance of this object
   */
  public OPSSourceCharacteristics copy() {
    final OPSSourceCharacteristics copy = new OPSSourceCharacteristics(
        heatContent, emissionHeight, diameter, spread, diurnalVariation, particleSizeDistribution);
    copy.setHeatContentType(getHeatContentType());
    copy.setEmissionTemperature(emissionTemperature);
    copy.setOutflowDiameter(outflowDiameter);
    copy.setOutflowDirection(outflowDirection);
    copy.setOutflowVelocity(outflowVelocity);
    copy.setBuildingInfluence(isBuildingInfluence());
    copy.setBuildingHeight(buildingHeight);
    copy.setBuildingLength(buildingLength);
    copy.setBuildingWidth(buildingWidth);
    copy.setBuildingOrientation(buildingOrientation);
    return copy;
  }

  @Override
  public boolean equals(final Object obj) {
    if ((obj != null) && (this.getClass() == obj.getClass())) {
      final OPSSourceCharacteristics opsc = (OPSSourceCharacteristics) obj;

      return (Math.abs(heatContent - opsc.heatContent) < 0.00001)
          && (Math.abs(emissionHeight - opsc.emissionHeight) < 0.00001)
          && (diameter == opsc.diameter)
          && (Math.abs(spread - opsc.spread) < 0.00001)
          && (diurnalVariation == opsc.diurnalVariation)
          && (particleSizeDistribution == opsc.particleSizeDistribution);
    }
    return false;
  }

  public double getEmissionTemperature() {
    return emissionTemperature;
  }

  public double getOutflowDiameter() {
    return outflowDiameter;
  }

  public double getOutflowVelocity() {
    return outflowVelocity;
  }

  public OutflowDirectionType getOutflowDirection() {
    return outflowDirection;
  }

  /**
   * return the type of outflow standard or custom
   *
   * @return heat content type
   */
  public HeatContentType getHeatContentType() {
    return heatContentType;
  }

  /**
   * @return the heat content
   */
  @Min(value = OPSLimits.SOURCE_HEAT_CONTENT_MINIMUM, message = "ops heat_content > " + OPSLimits.SOURCE_HEAT_CONTENT_MINIMUM)
  @Max(value = OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM, message = "ops heat_content < " + OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM)
  public double getHeatContent() {
    return heatContent;
  }

  /**
   * @return the emission height
   */
  @Min(value = OPSLimits.SOURCE_EMISSION_HEIGHT_MINIMUM, message = "ops height > " + OPSLimits.SOURCE_EMISSION_HEIGHT_MINIMUM)
  @Max(value = OPSLimits.SOURCE_EMISSION_HEIGHT_MAXIMUM, message = "ops height < " + OPSLimits.SOURCE_EMISSION_HEIGHT_MAXIMUM)
  public double getEmissionHeight() {
    return emissionHeight;
  }

  /**
   * @return the diameter
   */
  @Min(value = OPSLimits.SOURCE_DIAMETER_MINIMUM, message = "ops diameter > " + OPSLimits.SOURCE_DIAMETER_MINIMUM)
  @Max(value = OPSLimits.SOURCE_DIAMETER_MAXIMUM, message = "ops diameter < " + OPSLimits.SOURCE_DIAMETER_MAXIMUM)
  public int getDiameter() {
    return diameter;
  }

  /**
   * @return the s (Source height distribution)
   */
  @Min(value = OPSLimits.SOURCE_SPREAD_MINIMUM, message = "ops spread < " + OPSLimits.SOURCE_SPREAD_MINIMUM)
  @Max(value = OPSLimits.SOURCE_SPREAD_MAXIMUM, message = "ops spread > " + OPSLimits.SOURCE_SPREAD_MAXIMUM)
  public double getSpread() {
    return spread;
  }

  /**
   *
   * @return buildingHeight
   */
  public double getBuildingHeight() {
    return buildingHeight;
  }

  /**
   *
   * @return buildingWidth
   */
  public double getBuildingWidth() {
    return buildingWidth;
  }

  /**
   *
   * @return buildingLength
   */
  public double getBuildingLength() {
    return buildingLength;
  }

  public Integer getBuildingOrientation() {
    return buildingOrientation;
  }

  public DiurnalVariation getDiurnalVariation() {
    return diurnalVariation;
  }

  /**
   * @return the particle-size distribution
   */
  @Min(value = OPSLimits.SOURCE_PARTICLE_SIZE_DISTRIBUTION_MINIMUM, message = "ops particle_size_distribution > "
      + OPSLimits.SOURCE_PARTICLE_SIZE_DISTRIBUTION_MINIMUM)
  @Max(value = OPSLimits.SOURCE_PARTICLE_SIZE_DISTRIBUTION_MAXIMUM, message = "ops particle_size_distribution < "
      + OPSLimits.SOURCE_PARTICLE_SIZE_DISTRIBUTION_MAXIMUM)
  public int getParticleSizeDistribution() {
    return particleSizeDistribution;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + new Double(heatContent).hashCode();
    result = (prime * result) + new Double(emissionHeight).hashCode();
    result = (prime * result) + diameter;
    result = (prime * result) + new Double(spread).hashCode();
    result = (prime * result) + getDiurnalVariation().hashCode();
    result = (prime * result) + particleSizeDistribution;
    return result;
  }

  public void setEmissionTemperature(final double emissionTemperature) {
    this.emissionTemperature = emissionTemperature;
  }

  public void setOutflowDiameter(final double outflowDiameter) {
    this.outflowDiameter = outflowDiameter;
  }

  public void setOutflowVelocity(final double outflowVelocity) {
    this.outflowVelocity = outflowVelocity;
  }

  public void setOutflowDirection(final OutflowDirectionType outflowDirection) {
    this.outflowDirection = outflowDirection;
  }

  /**
   *
   * @param heatContentType
   */
  public void setHeatContentType(final HeatContentType heatContentType) {
    this.heatContentType = heatContentType;
  }

  /**
   * @param heatContent the heat content to set
   */
  public void setHeatContent(final double heatContent) {
    this.heatContent = heatContent;
  }

  /**
   * @param height the height to set
   */
  public void setEmissionHeight(final double height) {
    this.emissionHeight = height;
  }

  /**
   * @param diameter the diameter to set
   */
  public void setDiameter(final int diameter) {
    this.diameter = diameter;
  }

  /**
   * @param spread the spread to set
   */
  public void setSpread(final double spread) {
    this.spread = spread;
  }

  public void setDiurnalVariation(final DiurnalVariation diurnalVariation) {
    this.diurnalVariation = diurnalVariation == null ? DiurnalVariation.UNKNOWN : diurnalVariation;
  }

  /**
   * @param particleSizeDistribution
   *          the particle-size distribution to set
   */
  public void setParticleSizeDistribution(final int particleSizeDistribution) {
    this.particleSizeDistribution = particleSizeDistribution;
  }

  public void setBuildingHeight(final double buildingHeight) {
    this.buildingHeight = buildingHeight;
  }

  /**
   * The effective emission height could be a function of the building height, the emission height, or a combination of both.
   *
   * OPS only takes an emission height parameter and doesn't incorporate any building height, which
   * can - in practice - interfere with an emission's deposition. The formulae used here to correct the emission height 'appear' to
   * accurately correct how a building with a certain height affects deposition.
   *
   * The formula that used to be here was incorrect. Research is being done what the proper formula/method would be.
   * Until that research is done, we're just returning the emission height.
   *
   * @return The effective emission height
   */
  public double getEffectiveHeight() {
    return emissionHeight;
  }

  public boolean isUserdefinedHeatContent() {
    return getHeatContentType() == HeatContentType.NOT_FORCED;
  }

  public boolean isBuildingInfluence() {
    return buildingInfluence;
  }

  public void setBuildingInfluence(final boolean buildingInfluence) {
    this.buildingInfluence = buildingInfluence;
  }

  public void setBuildingWidth(final double buildingWidth) {
    this.buildingWidth = buildingWidth;
  }

  public void setBuildingLength(final double buildingLength) {
    this.buildingLength = buildingLength;
  }

  public void setBuildingOrientation(final Integer buildingOrientation) {
    this.buildingOrientation = buildingOrientation;
  }

  @Override
  public String toString() {
    return "OPSSourceCharacteristics ["
        + ", heatContentType=" + heatContentType + ", heatContent=" + heatContent
        + ", buildInfluence=" + isBuildingInfluence() + ",  buildingLength= " + buildingLength + ", buildingWidth=" + buildingWidth
        + ", buildingHeight=" + buildingHeight + ", buildingOrientation=" + buildingOrientation
        + ", emissionHeight=" + emissionHeight + ", diameter=" + diameter
        + ", spread=" + spread + ", diurnalVariationSpecification=" + diurnalVariation
        + ", particleSizeDistribution=" + particleSizeDistribution
        + "]";
  }
}
