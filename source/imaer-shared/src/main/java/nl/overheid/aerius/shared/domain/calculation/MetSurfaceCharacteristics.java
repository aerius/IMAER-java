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

public class MetSurfaceCharacteristics implements Serializable {

  private static final long serialVersionUID = 1L;

  private double roughness;
  private double minMoninObukhovLength;
  private double surfaceAlbedo;
  private double priestleyTaylorParameter;
  private boolean windInSectors;

  MetSurfaceCharacteristics() {
  }

  private MetSurfaceCharacteristics(final double roughness, final double minMoninObukhovLength, final double surfaceAlbedo,
      final double priestleyTaylorParameter, final boolean windInSectors) {
    this.roughness = roughness;
    this.minMoninObukhovLength = minMoninObukhovLength;
    this.surfaceAlbedo = surfaceAlbedo;
    this.priestleyTaylorParameter = priestleyTaylorParameter;
    this.windInSectors = windInSectors;
  }

  public double getRoughness() {
    return roughness;
  }

  public double getMinMoninObukhovLength() {
    return minMoninObukhovLength;
  }

  public double getSurfaceAlbedo() {
    return surfaceAlbedo;
  }

  public double getPriestleyTaylorParameter() {
    return priestleyTaylorParameter;
  }

  public boolean isWindInSectors() {
    return windInSectors;
  }

  void setRoughness(final double roughness) {
    this.roughness = roughness;
  }

  void setMinMoninObukhovLength(final double minMoninObukhovLength) {
    this.minMoninObukhovLength = minMoninObukhovLength;
  }

  void setSurfaceAlbedo(final double surfaceAlbedo) {
    this.surfaceAlbedo = surfaceAlbedo;
  }

  void setPriestleyTaylorParameter(final double priestleyTaylorParameter) {
    this.priestleyTaylorParameter = priestleyTaylorParameter;
  }

  void setWindInSectors(final boolean windInSectors) {
    this.windInSectors = windInSectors;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private double roughness;
    private double minMoninObukhovLength;
    private double surfaceAlbedo;
    private double priestleyTaylorParameter;
    private boolean windInSectors;

    public Builder roughness(final double roughness) {
      this.roughness = roughness;
      return this;
    }

    public Builder minMoninObukhovLength(final double minMoninObukhovLength) {
      this.minMoninObukhovLength = minMoninObukhovLength;
      return this;
    }

    public Builder surfaceAlbedo(final double surfaceAlbedo) {
      this.surfaceAlbedo = surfaceAlbedo;
      return this;
    }

    public Builder priestleyTaylorParameter(final double priestleyTaylorParameter) {
      this.priestleyTaylorParameter = priestleyTaylorParameter;
      return this;
    }

    public Builder windInSectors(final boolean windInSectors) {
      this.windInSectors = windInSectors;
      return this;
    }

    public MetSurfaceCharacteristics build() {
      return new MetSurfaceCharacteristics(roughness, minMoninObukhovLength, surfaceAlbedo, priestleyTaylorParameter, windInSectors);
    }
  }
}
