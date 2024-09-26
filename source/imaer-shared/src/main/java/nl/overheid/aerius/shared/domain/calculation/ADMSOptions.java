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
package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains ADMS specific options for a calculation.
 */
public class ADMSOptions implements Serializable {

  private static final long serialVersionUID = 3L;

  private double minMoninObukhovLength;
  private double surfaceAlbedo;
  private double priestleyTaylorParameter;
  private int metSiteId;
  private MetDatasetType metDatasetType;
  private List<String> metYears = new ArrayList<>();
  // Map<meteo year, MetSiteSurfaceCharacteristics>
  private Map<String, MetSurfaceCharacteristics> metSiteSurfaceCharacteristics = new HashMap<>();

  private boolean plumeDepletionNH3;
  private boolean plumeDepletionNOX;
  private boolean spatiallyVaryingRoughness;
  private boolean complexTerrain;

  public double getMinMoninObukhovLength() {
    return minMoninObukhovLength;
  }

  public void setMinMoninObukhovLength(final double minMoninObukhovLength) {
    this.minMoninObukhovLength = minMoninObukhovLength;
  }

  public double getSurfaceAlbedo() {
    return surfaceAlbedo;
  }

  public void setSurfaceAlbedo(final double surfaceAlbedo) {
    this.surfaceAlbedo = surfaceAlbedo;
  }

  public double getPriestleyTaylorParameter() {
    return priestleyTaylorParameter;
  }

  public void setPriestleyTaylorParameter(final double priestleyTaylorParameter) {
    this.priestleyTaylorParameter = priestleyTaylorParameter;
  }

  public int getMetSiteId() {
    return metSiteId;
  }

  public void setMetSiteId(final int metSiteId) {
    this.metSiteId = metSiteId;
  }

  public MetDatasetType getMetDatasetType() {
    return metDatasetType;
  }

  public void setMetDatasetType(final MetDatasetType metDatasetType) {
    this.metDatasetType = metDatasetType;
  }

  public List<String> getMetYears() {
    return metYears;
  }

  public void setMetYears(final List<String> metYears) {
    this.metYears = metYears;
  }

  public MetSurfaceCharacteristics getMetSiteCharacteristics(final String meteoYear) {
    return metSiteSurfaceCharacteristics == null || !metSiteSurfaceCharacteristics.containsKey(meteoYear)
        ? getLegacyMetSiteCharacteristics()
        : metSiteSurfaceCharacteristics.get(meteoYear);
  }

  public void setMetSiteCharacteristics(final Map<String, MetSurfaceCharacteristics> metSiteSurfaceCharacteristics) {
    this.metSiteSurfaceCharacteristics = metSiteSurfaceCharacteristics;
  }

  public void putMetSiteCharacteristics(final String metYear, final MetSurfaceCharacteristics msc) {
    this.metSiteSurfaceCharacteristics.put(metYear, msc);
  }

  public double getMsRoughness() {
    return getLegacyMetSiteCharacteristics().getRoughness();
  }

  @Deprecated
  public void setMsRoughness(final double msRoughness) {
    getLegacyMetSiteCharacteristics().setRoughness(msRoughness);
  }

  @Deprecated
  public double getMsMinMoninObukhovLength() {
    return getLegacyMetSiteCharacteristics().getMinMoninObukhovLength();
  }

  @Deprecated
  public void setMsMinMoninObukhovLength(final double msMinMoninObukhovLength) {
    getLegacyMetSiteCharacteristics().setMinMoninObukhovLength(msMinMoninObukhovLength);
  }

  @Deprecated
  public double getMsSurfaceAlbedo() {
    return getLegacyMetSiteCharacteristics().getSurfaceAlbedo();
  }

  @Deprecated
  public void setMsSurfaceAlbedo(final double msSurfaceAlbedo) {
    getLegacyMetSiteCharacteristics().setSurfaceAlbedo(msSurfaceAlbedo);
  }

  @Deprecated
  public double getMsPriestleyTaylorParameter() {
    return getLegacyMetSiteCharacteristics().getPriestleyTaylorParameter();
  }

  @Deprecated
  public void setMsPriestleyTaylorParameter(final double msPriestleyTaylorParameter) {
    getLegacyMetSiteCharacteristics().setPriestleyTaylorParameter(msPriestleyTaylorParameter);
  }

  private MetSurfaceCharacteristics getLegacyMetSiteCharacteristics() {
    return metSiteSurfaceCharacteristics.computeIfAbsent("", k -> new MetSurfaceCharacteristics());
  }

  public boolean isSpatiallyVaryingRoughness() {
    return spatiallyVaryingRoughness;
  }

  public void setSpatiallyVaryingRoughness(final boolean spatiallyVaryingRoughness) {
    this.spatiallyVaryingRoughness = spatiallyVaryingRoughness;
  }

  public boolean isComplexTerrain() {
    return complexTerrain;
  }

  public void setComplexTerrain(final boolean complexTerrain) {
    this.complexTerrain = complexTerrain;
  }

  public boolean isPlumeDepletionNH3() {
    return plumeDepletionNH3;
  }

  public void setPlumeDepletionNH3(final boolean plumeDepletionNH3) {
    this.plumeDepletionNH3 = plumeDepletionNH3;
  }

  public boolean isPlumeDepletionNOX() {
    return plumeDepletionNOX;
  }

  public void setPlumeDepletionNOX(final boolean plumeDepletionNOX) {
    this.plumeDepletionNOX = plumeDepletionNOX;
  }

  @Override
  public String toString() {
    return "ADMSOptions [minMoninObukhovLength=" + minMoninObukhovLength + ", surfaceAlbedo=" + surfaceAlbedo + ", priestleyTaylorParameter="
        + priestleyTaylorParameter + ", metSiteId=" + metSiteId + ", plumeDepletionNH3=" + plumeDepletionNH3 + ", plumeDepletionNOX="
        + plumeDepletionNOX + ", spatiallyVaryingRoughness=" + spatiallyVaryingRoughness + ", complexTerrain=" + complexTerrain + "]";
  }
}
