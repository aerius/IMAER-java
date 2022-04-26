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
package nl.overheid.aerius.shared.domain.v2.characteristics;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.overheid.aerius.shared.domain.ops.DiurnalVariation;
import nl.overheid.aerius.shared.domain.ops.OPSLimits;
import nl.overheid.aerius.shared.domain.ops.OutflowDirectionType;
import nl.overheid.aerius.shared.domain.ops.OutflowVelocityType;

public class OPSSourceCharacteristics extends SourceCharacteristics {

  private static final long serialVersionUID = 1L;

  private HeatContentType heatContentType;

  /**
   * heatContent - heat content (MW)
   */
  private Double heatContent;

  /**
   * emissionTemperature (degrees)
   */
  private Double emissionTemperature;

  /**
   * outflowDiameter (m)
   */
  private Double outflowDiameter;

  /**
   * outflowVelocity (m/s)
   */
  private Double outflowVelocity;

  /**
   * OutflowDirection - Default value OutflowDirectionType.VERTICAL_FORCED
   */
  private OutflowDirectionType outflowDirection;

  /**
   * OutflowVelocityType - Default value OutflowVelocityType.ACTUAL_FLOW
   */
  private OutflowVelocityType outflowVelocityType;

  /**
   * emissionHeight - source emission height (m)
   */
  private double emissionHeight;

  /**
   * diameter - source diameter (m)
   */
  private Integer diameter;

  /**
   * spread - vertical spread of source height(s) (m)
   */
  private Double spread;

  /**
   * Diurnal Variation - specification for diurnal variation
   */
  private DiurnalVariation diurnalVariation;

  /**
   * particleSizeDistribution - code for distribution of particle sizes (0 for gasses, > 0 for particulate matter)
   */
  private int particleSizeDistribution;

  public HeatContentType getHeatContentType() {
    return heatContentType;
  }

  public void setHeatContentType(final HeatContentType heatContentType) {
    this.heatContentType = heatContentType;
  }

  @Min(value = OPSLimits.SOURCE_HEAT_CONTENT_MINIMUM, message = "ops heat_content > " + OPSLimits.SOURCE_HEAT_CONTENT_MINIMUM)
  @Max(value = OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM, message = "ops heat_content < " + OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM)
  public Double getHeatContent() {
    return heatContent;
  }

  public void setHeatContent(final Double heatContent) {
    this.heatContent = heatContent;
  }

  public Double getEmissionTemperature() {
    return emissionTemperature;
  }

  public void setEmissionTemperature(final Double emissionTemperature) {
    this.emissionTemperature = emissionTemperature;
  }

  public Double getOutflowDiameter() {
    return outflowDiameter;
  }

  public void setOutflowDiameter(final Double outflowDiameter) {
    this.outflowDiameter = outflowDiameter;
  }

  public Double getOutflowVelocity() {
    return outflowVelocity;
  }

  public void setOutflowVelocity(final Double outflowVelocity) {
    this.outflowVelocity = outflowVelocity;
  }

  public OutflowDirectionType getOutflowDirection() {
    return outflowDirection;
  }

  public void setOutflowDirection(final OutflowDirectionType outflowDirection) {
    this.outflowDirection = outflowDirection;
  }

  public OutflowVelocityType getOutflowVelocityType() {
    return outflowVelocityType;
  }

  public void setOutflowVelocityType(final OutflowVelocityType outflowVelocityType) {
    this.outflowVelocityType = outflowVelocityType;
  }

  @Min(value = OPSLimits.SOURCE_EMISSION_HEIGHT_MINIMUM, message = "ops height > " + OPSLimits.SOURCE_EMISSION_HEIGHT_MINIMUM)
  @Max(value = OPSLimits.SOURCE_EMISSION_HEIGHT_MAXIMUM, message = "ops height < " + OPSLimits.SOURCE_EMISSION_HEIGHT_MAXIMUM)
  public double getEmissionHeight() {
    return emissionHeight;
  }

  public void setEmissionHeight(final double emissionHeight) {
    this.emissionHeight = emissionHeight;
  }

  @Min(value = OPSLimits.SOURCE_DIAMETER_MINIMUM, message = "ops diameter > " + OPSLimits.SOURCE_DIAMETER_MINIMUM)
  @Max(value = OPSLimits.SOURCE_DIAMETER_MAXIMUM, message = "ops diameter < " + OPSLimits.SOURCE_DIAMETER_MAXIMUM)
  public Integer getDiameter() {
    return diameter;
  }

  public void setDiameter(final Integer diameter) {
    this.diameter = diameter;
  }

  @Min(value = OPSLimits.SOURCE_SPREAD_MINIMUM, message = "ops spread < " + OPSLimits.SOURCE_SPREAD_MINIMUM)
  @Max(value = OPSLimits.SOURCE_SPREAD_MAXIMUM, message = "ops spread > " + OPSLimits.SOURCE_SPREAD_MAXIMUM)
  public Double getSpread() {
    return spread;
  }

  public void setSpread(final Double spread) {
    this.spread = spread;
  }

  public DiurnalVariation getDiurnalVariation() {
    return diurnalVariation;
  }

  public void setDiurnalVariation(final DiurnalVariation diurnalVariation) {
    this.diurnalVariation = diurnalVariation;
  }

  @JsonIgnore
  public int getParticleSizeDistribution() {
    return particleSizeDistribution;
  }

  public void setParticleSizeDistribution(final int particleSizeDistribution) {
    this.particleSizeDistribution = particleSizeDistribution;
  }

  public <E extends OPSSourceCharacteristics> E copyTo(final E copy) {
    super.copyTo(copy);
    copy.setHeatContentType(heatContentType);
    copy.setHeatContent(heatContent);
    copy.setEmissionHeight(emissionHeight);
    copy.setDiameter(diameter);
    copy.setSpread(spread);
    copy.setDiurnalVariation(diurnalVariation);
    copy.setParticleSizeDistribution(particleSizeDistribution);
    copy.setHeatContentType(getHeatContentType());
    copy.setEmissionTemperature(emissionTemperature);
    copy.setOutflowDiameter(outflowDiameter);
    copy.setOutflowDirection(outflowDirection);
    copy.setOutflowVelocity(outflowVelocity);
    copy.setOutflowVelocityType(outflowVelocityType);
    return copy;
  }

}
