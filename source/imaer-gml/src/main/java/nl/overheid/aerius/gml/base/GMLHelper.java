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
package nl.overheid.aerius.gml.base;

import java.util.List;

import nl.overheid.aerius.gml.base.source.ship.v31.GMLInlandShippingSupplier;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.validation.ValidationHelper;

/**
 * Helper interface for retrieving data or operations that were done in the database.
 */
public interface GMLHelper extends GMLInlandShippingSupplier, GMLLegacyCodesSupplier, GMLCharacteristicsSupplier, GMLSectorSupplier {

  /**
   * Recalculates the emissions on all given emission sources for the given emission keys.
   *
   * @param year year to calculate emissions for
   * @param emissionSourceList emission sources to calculate emissions
   * @throws AeriusException
   */
  void enforceEmissions(int year, List<EmissionSourceFeature> emissionSourceList) throws AeriusException;

  /**
   * @return Returns the specific receptor grid configuration.
   * @throws AeriusException
   */
  ReceptorGridSettings getReceptorGridSettings() throws AeriusException;

  /**
   * @return Returns a validation helper.
   */
  ValidationHelper getValidationHelper();
}
