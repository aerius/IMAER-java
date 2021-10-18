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
 * This enum defines the efflux parameters that must be specified for each efflux type.
 * For example this can be used to guide user input on the data that needs to be available for a specific efflux type.
 */
public enum EffluxType {
  VELOCITY(0, true, true, false, true, false, false),
  VOLUME(1, true, false, true, true, false, false),
  MOMENTUM(2, false, false, false, false, true, false),
  MASS(3, true, false, false, true, false, true);

  /**
   * ADMS .apl configuration parameter identifying the efflux type.
   */
  private final int type;
  /**
   * If true the efflux can be specified at the temperature or at normal temperature and pressure (NTP).
   */
  private final boolean actualOrNtp;
  /**
   * If true the velocity must be specified.
   */
  private final boolean velocity;
  /**
   * If true the volume flux must be specified.
   */
  private final boolean volumeFlux;
  /**
   * If true the temperature or density of the release must be specified.
   */
  private final boolean tRhoAmbient;
  /**
   * If true the Momentum Flux (Fm) and Buoyancy Flux (Fb) values must be specified.
   */
  private final boolean fmbm;
  /**
   * If true the mass flux value must be specified.
   */
  private final boolean massFlux;

  EffluxType(final int type, final boolean actualOrNtp, final boolean velocity, final boolean volumeFlux, final boolean rho, final boolean fmbm,
      final boolean massFlux) {
    this.type = type;
    this.actualOrNtp = actualOrNtp;
    this.velocity = velocity;
    this.volumeFlux = volumeFlux;
    this.tRhoAmbient = rho;
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

  public boolean isTRhoAmbient() {
    return tRhoAmbient;
  }

  public boolean isFmbm() {
    return fmbm;
  }

  public boolean isMassFlux() {
    return massFlux;
  }
}
