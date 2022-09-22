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
package nl.overheid.aerius.gml.v5_1.togml;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v5_1.source.Emission;
import nl.overheid.aerius.gml.v5_1.source.EmissionProperty;
import nl.overheid.aerius.gml.v5_1.source.characteristics.EmissionSourceCharacteristics;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
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
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 * Util class to convert {@link EmissionSource} to GML object.
 */
final class Source2GML implements EmissionSourceVisitor<nl.overheid.aerius.gml.v5_1.source.EmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(Source2GML.class);

  private final Geometry2GML geometry2gml;

  public Source2GML(final Geometry2GML geometry2gml) {
    this.geometry2gml = geometry2gml;
  }

  /**
   * Convert a emissionsource to a GML-object.
   * Based on the actual emissionvalues object of the emissionSource, a specific GML-object will be chosen.
   * @param source The source to convert.
   * @param substances The substances to use when determining emission values.
   * @return The converted GML-object (can be a subclass)
   * @throws AeriusException when objects could not be converted to GML.
   */
  public List<FeatureMember> toGML(final EmissionSourceFeature source, final Substance[] substances) throws AeriusException {
    if (source.getGeometry() == null) {
      throw new IllegalArgumentException("Emissionsource without geometry not allowed: " + source);
    }
    final List<FeatureMember> members = new ArrayList<>();
    // For now assume no more road networks. If these DO reappear, add all related features to the members list.
    members.add(toGMLDefault(source, substances));
    return members;
  }

  private nl.overheid.aerius.gml.v5_1.source.EmissionSource toGMLDefault(final EmissionSourceFeature sourceFeature,
      final Substance[] substances) throws AeriusException {
    final EmissionSource source = sourceFeature.getProperties();
    //use a specific prefix for ID to achieve unique IDs
    final String gmlId = GMLIdUtil.toValidGmlId(source.getGmlId(), GMLIdUtil.SOURCE_PREFIX, sourceFeature.getId());
    source.setGmlId(gmlId);
    final nl.overheid.aerius.gml.v5_1.source.EmissionSource returnSource = sourceFeature.accept(this);
    //set the generic properties.
    returnSource.setGeometry(geometry2gml, sourceFeature.getGeometry());
    returnSource.setId(gmlId);

    returnSource.setLabel(source.getLabel());
    returnSource.setDescription(source.getDescription());
    returnSource.setJurisdictionId(source.getJurisdictionId());
    //getSector always returns a sector, even if it was null.
    returnSource.setSectorId(source.getSectorId());

    toGMLCharacteristics(sourceFeature, source, returnSource);

    //always set emissionvalues (even for things like farm/road where we won't use them when importing)
    returnSource.setEmissionValues(getEmissions(source, substances));
    if (returnSource.getEmissionValues() == null) {
      throw new IllegalArgumentException("Emissionsource without emission not allowed: " + source);
    }
    return returnSource;
  }

  private void toGMLCharacteristics(final EmissionSourceFeature sourceFeature, final EmissionSource source,
      final nl.overheid.aerius.gml.v5_1.source.EmissionSource returnSource) throws AeriusException {
    if (source.getCharacteristics() instanceof OPSSourceCharacteristics) {
      returnSource.setCharacteristics(SourceCharacteristics2GML.toGML((OPSSourceCharacteristics) source.getCharacteristics(), true));
      //ensure spread isn't exported for pointsources.
      if (sourceFeature.getGeometry() instanceof Point) {
        ((EmissionSourceCharacteristics) returnSource.getCharacteristics()).setSpread(null);
      }
      //ensure diurnal variation isn't exported for sources other then generic ones.
      if (!(source instanceof GenericEmissionSource)) { //
        ((EmissionSourceCharacteristics) returnSource.getCharacteristics()).setDiurnalVariation(null);
      }
    }
    // For ADMS, don't export characteristics for road.
    // It does not really use any characteristic but the diurnal variation, and that's specified directly on the object.
    if (source.getCharacteristics() instanceof ADMSSourceCharacteristics
        && !(source instanceof ADMSRoadEmissionSource)) {
      returnSource.setCharacteristics(SourceCharacteristics2GML.toGML((ADMSSourceCharacteristics) source.getCharacteristics()));
    }
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final GenericEmissionSource emissionSource, final IsFeature feature) {
    return new nl.overheid.aerius.gml.v5_1.source.EmissionSource();
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final MooringMaritimeShippingEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new MaritimeMooring2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final MaritimeShippingEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new MaritimeRoute2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final InlandShippingEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new InlandRoute2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final MooringInlandShippingEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new InlandMooring2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final OffRoadMobileEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new OffRoad2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final PlanEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new Plan2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final FarmLodgingEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new Farm2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final FarmlandEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new Farmland2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final SRM1RoadEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new Road2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final SRM2RoadEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new Road2GML().convert(emissionSource);
  }

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource visit(final ADMSRoadEmissionSource emissionSource, final IsFeature feature)
      throws AeriusException {
    return new Road2GML().convert(emissionSource);
  }

  private List<EmissionProperty> getEmissions(
      final EmissionSource source, final Substance[] substances) {
    final List<EmissionProperty> emissions = new ArrayList<>(substances.length);
    for (final Substance substance : substances) {
      //always export for each substance found
      //it'll result in a bit more elements, but it's clearer to user
      //if all emissions would be 0 it'd result in emissions being empty as well, which conflicts with XSD.
      final Emission emission = new Emission();
      emission.setSubstance(substance);
      emission.setValue(source.getEmissions().getOrDefault(substance, 0.0));
      emissions.add(new EmissionProperty(emission));

    }
    return emissions;
  }

}
