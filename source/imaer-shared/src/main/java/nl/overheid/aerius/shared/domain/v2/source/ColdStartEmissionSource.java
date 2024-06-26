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
package nl.overheid.aerius.shared.domain.v2.source;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Emission source for Cold Start (koude start) emission sources.
 *
 * It uses the {@link Vehicles} instances, and interprets number of vehicles as number of cold starts.
 */
public class ColdStartEmissionSource extends EmissionSourceWithSubSources<Vehicles> {

  private static final long serialVersionUID = 2L;

  private boolean vehicleBasedCharacteristics;

  @Override
  <T> T accept(final EmissionSourceVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

  public boolean isVehicleBasedCharacteristics() {
    return vehicleBasedCharacteristics;
  }

  public void setVehicleBasedCharacteristics(final boolean vehicleBasedCharacteristics) {
    this.vehicleBasedCharacteristics = vehicleBasedCharacteristics;
  }

}
