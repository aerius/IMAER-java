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
package nl.overheid.aerius.shared.emissions;

import java.util.Collection;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceVisitor;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

/**
 * Goal for this class is to update the total emission values for an {@link EmissionSourceFeature} object.
 *
 * This is done by visiting the emissionsource with an {@link EmissionSourceVisitor} to obtain the correct values.
 */
public class EmissionsUpdater {

  private final EmissionsCalculator emissionsCalculator;

  public EmissionsUpdater(final EmissionFactorSupplier emissionFactorSupplier, final GeometryCalculator geometryCalculator) {
    this(new EmissionsCalculator(emissionFactorSupplier, geometryCalculator));
  }

  EmissionsUpdater(final EmissionsCalculator emissionsCalculator) {
    this.emissionsCalculator = emissionsCalculator;
  }

  public void updateEmissions(final Collection<EmissionSourceFeature> emissionSourceFeatures) throws AeriusException {
    for (final EmissionSourceFeature emissionSourceFeature : emissionSourceFeatures) {
      this.updateEmissions(emissionSourceFeature);
    }
  }

  public void updateEmissions(final EmissionSourceFeature emissionSourceFeature) throws AeriusException {
    final Map<Substance, Double> emissionValues = emissionSourceFeature.accept(emissionsCalculator);
    emissionSourceFeature.getProperties().setEmissions(emissionValues);
  }

}
