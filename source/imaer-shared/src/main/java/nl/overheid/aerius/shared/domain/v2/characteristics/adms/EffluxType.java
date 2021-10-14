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
package nl.overheid.aerius.shared.domain.v2.characteristics.adms;

/**
 * Efflux type determines
 */
public enum EffluxType {
  VELOCITY(0, true, true, false, true, false, false),
  VOLUME(1, true, false, true, true, false, false),
  MOMENTUM(2, false, false, false, false, true, false),
  MASS(3, true, false, false, true, false, true);

  private final boolean actualOrNtp;
  private final boolean velocity;
  private final boolean volumeFlux;
  private final boolean rho;
  private final boolean fmbm;
  private final boolean massFlux;
  private final int type;

  EffluxType(final int type, final boolean actualOrNtp, final boolean velocity, final boolean volumeFlux, final boolean rho, final boolean fmbm,
      final boolean massFlux) {
    this.type = type;
    this.actualOrNtp = actualOrNtp;
    this.velocity = velocity;
    this.volumeFlux = volumeFlux;
    this.rho = rho;
    this.fmbm = fmbm;
    this.massFlux = massFlux;
  }

  public static EffluxType valueOf(final int effluxType) {
    for (final EffluxType et : values()) {
      if (et.type == effluxType) {
        return et;
      }
    }
    return null;
  }

  public boolean isActualOrNtp() {
    return actualOrNtp;
  }

  public boolean isVelocity() {
    return velocity;
  }

  public boolean isVolumeFlux() {
    return volumeFlux;
  }

  public boolean isRho() {
    return rho;
  }

  public boolean isFmbm() {
    return fmbm;
  }

  public boolean isMassFlux() {
    return massFlux;
  }
}
