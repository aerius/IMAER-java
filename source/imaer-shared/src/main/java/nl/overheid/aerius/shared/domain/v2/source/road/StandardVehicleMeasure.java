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
package nl.overheid.aerius.shared.domain.v2.source.road;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.EmissionReduction;

public class StandardVehicleMeasure implements Serializable {

  private static final long serialVersionUID = 4L;

  private String vehicleTypeCode;
  private String roadTypeCode;
  private final List<EmissionReduction> emissionReductions = new ArrayList<>();

  public String getVehicleTypeCode() {
    return vehicleTypeCode;
  }

  public void setVehicleTypeCode(final String vehicleTypeCode) {
    this.vehicleTypeCode = vehicleTypeCode;
  }

  public String getRoadTypeCode() {
    return roadTypeCode;
  }

  public void setRoadTypeCode(final String roadTypeCode) {
    this.roadTypeCode = roadTypeCode;
  }

  public List<EmissionReduction> getEmissionReductions() {
    return emissionReductions;
  }

  public void addEmissionReduction(final EmissionReduction emissionReduction) {
    this.emissionReductions.add(emissionReduction);
  }

  public OptionalDouble determineFactor(final String vehicleTypeCode, final String roadTypeCode, final Substance substance) {
    return appliesTo(vehicleTypeCode, roadTypeCode) && appliesTo(substance) ? determineFactor(substance) : OptionalDouble.empty();
  }

  private OptionalDouble determineFactor(final Substance substance) {
    return emissionReductions.stream()
        .map(reduction -> reduction.determineFactor(substance))
        .filter(OptionalDouble::isPresent)
        .mapToDouble(OptionalDouble::getAsDouble)
        .max();
  }

  public boolean appliesTo(final String vehicleTypeCode, final String roadTypeCode) {
    return vehicleTypeCode.equals(this.vehicleTypeCode) && roadTypeCode.equals(this.roadTypeCode);
  }

  public boolean appliesTo(final Substance substance) {
    return emissionReductions.stream().anyMatch(reduction -> reduction.appliesTo(substance));
  }

}
