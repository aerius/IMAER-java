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
package nl.overheid.aerius.validation;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceVisitor;
import nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Visitor telling per emissionsource type if subsources are expected to have emissions or not.
 *
 * If the source type has no subsources, it shouldn't really matter what is returned.
 *
 * Road sources are generally expected not to have emissions on subsource level.
 */
class SubSourceExpectsEmissionsVisitor implements EmissionSourceVisitor<Boolean> {

  @Override
  public Boolean visit(final FarmAnimalHousingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final FarmlandEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final ManureStorageEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final OffRoadMobileEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final ColdStartEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final SRM1RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return false;
  }

  @Override
  public Boolean visit(final SRM2RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return false;
  }

  @Override
  public Boolean visit(final ADMSRoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return false;
  }

  @Override
  public Boolean visit(final InlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final MooringInlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final MaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final MooringMaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

  @Override
  public Boolean visit(final GenericEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return true;
  }

}
