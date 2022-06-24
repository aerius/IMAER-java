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

/**
 * Contains ADMS specific options for a calculation.
 */
public class ADMSOptions implements Serializable {

  private static final long serialVersionUID = 1L;

  private double minMoninObukhovLength;
  private double surfaceAlbedo;
  private double priestleyTaylorParameter;
  private int metSiteId;
  private double msRoughness;
  private double msMinMoninObukhovLength;
  private double msSurfaceAlbedo;
  private double msPriestleyTaylorParameter;
  private boolean plumeDepletionNH3;
  private boolean plumeDepletionNOX;
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

  public double getMsRoughness() {
    return msRoughness;
  }

  public void setMsRoughness(double msRoughness) {
    this.msRoughness = msRoughness;
  }

  public double getMsMinMoninObukhovLength() {
    return msMinMoninObukhovLength;
  }

  public void setMsMinMoninObukhovLength(double msMinMoninObukhovLength) {
    this.msMinMoninObukhovLength = msMinMoninObukhovLength;
  }

  public double getMsSurfaceAlbedo() {
    return msSurfaceAlbedo;
  }

  public void setMsSurfaceAlbedo(double msSurfaceAlbedo) {
    this.msSurfaceAlbedo = msSurfaceAlbedo;
  }

  public double getMsPriestleyTaylorParameter() {
    return msPriestleyTaylorParameter;
  }

  public void setMsPriestleyTaylorParameter(double msPriestleyTaylorParameter) {
    this.msPriestleyTaylorParameter = msPriestleyTaylorParameter;
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
        + priestleyTaylorParameter + ", metSiteId=" + metSiteId + ", msRoughness=" + msRoughness + ", msMinMoninObukhovLength=" + msMinMoninObukhovLength
        + ", msSurfaceAlbedo=" + msSurfaceAlbedo + ", msPriestleyTaylorParameter=" + msPriestleyTaylorParameter + ", plumeDepletionNH3="
        + plumeDepletionNH3 + ", plumeDepletionNOX=" + plumeDepletionNOX + ", complexTerrain=" + complexTerrain + "]";
  }
}
