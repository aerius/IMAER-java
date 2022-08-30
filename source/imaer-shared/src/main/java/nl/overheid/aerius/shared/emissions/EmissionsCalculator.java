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

import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.aerius.shared.domain.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceVisitor;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

class EmissionsCalculator implements EmissionSourceVisitor<Map<Substance, Double>> {

  private final FarmLodgingEmissionsCalculator farmLodgingEmissionsCalculator;
  private final FarmlandEmissionsCalculator farmlandEmissionsCalculator;
  private final PlanEmissionsCalculator planEmissionsCalculator;
  private final OffRoadMobileEmissionsCalculator offRoadMobileEmissionsCalculator;
  private final SRMRoadEmissionsCalculator srmRoadEmissionsCalculator;
  private final ADMSRoadEmissionsCalculator admsRoadEmissionsCalculator;
  private final InlandShippingEmissionsCalculator inlandShippingEmissionsCalculator;
  private final MaritimeShippingEmissionsCalculator maritimeShippingEmissionsCalculator;

  public EmissionsCalculator(final EmissionFactorSupplier emissionFactorSupplier, final GeometryCalculator geometryCalculator) {
    this(new FarmLodgingEmissionsCalculator(emissionFactorSupplier.farmLodging()),
        new FarmlandEmissionsCalculator(),
        new PlanEmissionsCalculator(emissionFactorSupplier.plan()),
        new OffRoadMobileEmissionsCalculator(emissionFactorSupplier.offRoadMobile()),
        new SRMRoadEmissionsCalculator(emissionFactorSupplier.road(), geometryCalculator),
        new ADMSRoadEmissionsCalculator(emissionFactorSupplier.road(), geometryCalculator),
        new InlandShippingEmissionsCalculator(emissionFactorSupplier.inlandShipping()),
        new MaritimeShippingEmissionsCalculator(emissionFactorSupplier.maritimeShipping()));
  }

  EmissionsCalculator(final FarmLodgingEmissionsCalculator farmLodgingEmissionsCalculator,
      final FarmlandEmissionsCalculator farmlandEmissionsCalculator,
      final PlanEmissionsCalculator planEmissionsCalculator,
      final OffRoadMobileEmissionsCalculator offRoadMobileEmissionsCalculator,
      final SRMRoadEmissionsCalculator srmRoadEmissionsCalculator,
      final ADMSRoadEmissionsCalculator admsRoadEmissionsCalculator,
      final InlandShippingEmissionsCalculator inlandShippingEmissionsCalculator,
      final MaritimeShippingEmissionsCalculator maritimeShippingEmissionsCalculator) {
    this.farmLodgingEmissionsCalculator = farmLodgingEmissionsCalculator;
    this.farmlandEmissionsCalculator = farmlandEmissionsCalculator;
    this.planEmissionsCalculator = planEmissionsCalculator;
    this.offRoadMobileEmissionsCalculator = offRoadMobileEmissionsCalculator;
    this.srmRoadEmissionsCalculator = srmRoadEmissionsCalculator;
    this.admsRoadEmissionsCalculator = admsRoadEmissionsCalculator;
    this.inlandShippingEmissionsCalculator = inlandShippingEmissionsCalculator;
    this.maritimeShippingEmissionsCalculator = maritimeShippingEmissionsCalculator;
  }

  @Override
  public Map<Substance, Double> visit(final FarmLodgingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return farmLodgingEmissionsCalculator.calculateEmissions(emissionSource);
  }

  @Override
  public Map<Substance, Double> visit(final FarmlandEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return farmlandEmissionsCalculator.calculateEmissions(emissionSource);
  }

  @Override
  public Map<Substance, Double> visit(final PlanEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return planEmissionsCalculator.calculateEmissions(emissionSource);
  }

  @Override
  public Map<Substance, Double> visit(final OffRoadMobileEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return offRoadMobileEmissionsCalculator.calculateEmissions(emissionSource);
  }

  @Override
  public Map<Substance, Double> visit(final SRM1RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return srmRoadEmissionsCalculator.calculateEmissions(emissionSource, feature.getGeometry());
  }

  @Override
  public Map<Substance, Double> visit(final SRM2RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return srmRoadEmissionsCalculator.calculateEmissions(emissionSource, feature.getGeometry());
  }

  @Override
  public Map<Substance, Double> visit(final ADMSRoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return admsRoadEmissionsCalculator.calculateEmissions(emissionSource, feature.getGeometry());
  }

  @Override
  public Map<Substance, Double> visit(final InlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return inlandShippingEmissionsCalculator.calculateEmissions(emissionSource, feature.getGeometry());
  }

  @Override
  public Map<Substance, Double> visit(final MooringInlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return inlandShippingEmissionsCalculator.calculateEmissions(emissionSource);
  }

  @Override
  public Map<Substance, Double> visit(final MaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return maritimeShippingEmissionsCalculator.calculateEmissions(emissionSource, feature.getGeometry());
  }

  @Override
  public Map<Substance, Double> visit(final MooringMaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return maritimeShippingEmissionsCalculator.calculateEmissions(emissionSource);
  }

  @Override
  public Map<Substance, Double> visit(final GenericEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    // Nothing to calculate, just return the existing values
    return emissionSource.getEmissions();
  }

}
