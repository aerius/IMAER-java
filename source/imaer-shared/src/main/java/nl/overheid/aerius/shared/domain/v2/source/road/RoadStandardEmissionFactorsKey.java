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

import java.util.Objects;

/**
 * Key that can be used to obtain emission factors for roads.
 * Intended to reduce the number of arguments needed when determining emission factors.
 */
public class RoadStandardEmissionFactorsKey {

  private final String roadAreaCode;
  private final String roadTypeCode;
  private final String standardVehicleCode;
  private final Integer maximumSpeed;
  private final Boolean strictEnforcement;
  private final Double gradient;

  public RoadStandardEmissionFactorsKey(final String roadAreaCode, final String roadTypeCode, final String standardVehicleCode,
      final Integer maximumSpeed, final Boolean strictEnforcement, final Double gradient) {
    this.roadAreaCode = roadAreaCode;
    this.roadTypeCode = roadTypeCode;
    this.standardVehicleCode = standardVehicleCode;
    this.maximumSpeed = maximumSpeed;
    this.strictEnforcement = strictEnforcement;
    this.gradient = gradient;
  }

  public String getRoadAreaCode() {
    return roadAreaCode;
  }

  public String getRoadTypeCode() {
    return roadTypeCode;
  }

  public String getStandardVehicleCode() {
    return standardVehicleCode;
  }

  public Integer getMaximumSpeed() {
    return maximumSpeed;
  }

  public Boolean getStrictEnforcement() {
    return strictEnforcement;
  }

  public Double getGradient() {
    return gradient;
  }

  public RoadStandardEmissionFactorsKey copyWith(final Integer maxSpeed, final Double gradient) {
    return new RoadStandardEmissionFactorsKey(roadAreaCode, roadTypeCode, standardVehicleCode, maxSpeed, strictEnforcement, gradient);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roadAreaCode, roadTypeCode, standardVehicleCode, maximumSpeed, strictEnforcement, gradient);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final RoadStandardEmissionFactorsKey other = (RoadStandardEmissionFactorsKey) obj;
    return Objects.equals(roadAreaCode, other.roadAreaCode) && Objects.equals(roadTypeCode, other.roadTypeCode)
        && Objects.equals(standardVehicleCode, other.standardVehicleCode) && Objects.equals(maximumSpeed, other.maximumSpeed)
        && Objects.equals(strictEnforcement, other.strictEnforcement) && Objects.equals(gradient, other.gradient);
  }

}
