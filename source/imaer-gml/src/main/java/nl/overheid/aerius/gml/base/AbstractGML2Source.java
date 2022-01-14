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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.source.IsGmlEmissionSource;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Utility class to convert to and from GML objects (specific for emission sources).
 *
 * @Param <T> The GML version specific emission source
 * @param <S> The specific type of source characteristics that are to be read from the gml
 */
public abstract class AbstractGML2Source<T extends IsGmlEmissionSource, S extends SourceCharacteristics> {

  private final GMLConversionData conversionData;
  private final GML2Geometry gml2geometry;
  private final IsGML2SourceVisitor<T> visitor;
  private final GML2SourceCharacteristics<S> gml2SourceCharacteristics;

  /**
   * @param conversionData The data to use when converting. Should be filled.
   */
  protected AbstractGML2Source(final GMLConversionData conversionData, final IsGML2SourceVisitor<T> visitor,
      final GML2SourceCharacteristics<S> gml2SourceCharacteristics) {
    this.conversionData = conversionData;
    this.gml2geometry = new GML2Geometry(conversionData.getSrid());
    this.visitor = visitor;
    this.gml2SourceCharacteristics = gml2SourceCharacteristics;
  }

  public List<EmissionSourceFeature> fromGML(final List<FeatureMember> members) {
    final List<EmissionSourceFeature> sources = new ArrayList<>();
    final AtomicInteger id = new AtomicInteger(1);

    for (final FeatureMember member : members) {
      handleMember(member, sources, id);
    }
    return sources;
  }

  private void handleMember(final FeatureMember member, final List<EmissionSourceFeature> sources, final AtomicInteger id) {
    if (member instanceof IsGmlEmissionSource) {
      try {
        final EmissionSourceFeature feature = handleSourceMember(member, id);
        if (feature != null) {
          sources.add(feature);
        }
      } catch (final AeriusException e) {
        conversionData.getErrors().add(e);
      }
    }
  }

  private EmissionSourceFeature handleSourceMember(final FeatureMember member, final AtomicInteger idTracker) throws AeriusException {
    final EmissionSourceFeature returnSourceFeature = new EmissionSourceFeature();
    final Geometry geometry = toGeometry(member);
    returnSourceFeature.setGeometry(geometry);
    final EmissionSource emissionSource = toEmissionSource((T) member, geometry);
    if (emissionSource == null) {
      return null;
    } else {
      returnSourceFeature.setProperties(emissionSource);
      returnSourceFeature.setId(String.valueOf(idTracker.getAndIncrement()));
      return returnSourceFeature;
    }
  }

  /**
   * Convert from a GML-object to an EmissionSource. Based on the exact type of input, a specific EmissionValues will be used.
   *
   * @param source The GML-object to convert.
   * @return The EmissionSource represented by the GML-object.
   * @throws AeriusException In case of errors converting.
   */
  public EmissionSource toEmissionSource(final T source, final Geometry geometry) throws AeriusException {
    final EmissionSource returnSource = visitor.visit(source);
    if (returnSource != null) {
      fromGenericEmissionSource(source, returnSource, geometry);
      conversionData.putAndTrack(source.getId());
    }
    return returnSource;
  }

  private void fromGenericEmissionSource(final T source, final EmissionSource returnSource, final Geometry geometry) throws AeriusException {
    returnSource.setGmlId(source.getId());
    returnSource.setLabel(source.getLabel());
    returnSource.setDescription(source.getDescription());
    returnSource.setJurisdictionId(source.getJurisdictionId());
    returnSource.setSectorId(
        Integer.parseInt(conversionData.getCode(GMLLegacyCodeType.SECTOR, String.valueOf(source.getSectorId()), source.getLabel())));
    if (!(returnSource instanceof InlandShippingEmissionSource
        || returnSource instanceof MooringInlandShippingEmissionSource
        || returnSource instanceof MaritimeShippingEmissionSource
        || returnSource instanceof MooringMaritimeShippingEmissionSource
        || returnSource instanceof OffRoadMobileEmissionSource
        || returnSource instanceof PlanEmissionSource)) {
      final S sectorCharacteristics = conversionData.determineDefaultCharacteristicsBySectorId(source.getSectorId());
      if (source.getCharacteristics() == null) {
        // if characteristics weren't supplied in GML, use the sector default.
        returnSource.setCharacteristics(sectorCharacteristics);
      } else {
        returnSource.setCharacteristics(gml2SourceCharacteristics.fromGML(source.getCharacteristics(), sectorCharacteristics, geometry));
      }
    }
  }

  private Geometry toGeometry(final FeatureMember source) throws AeriusException {
    return gml2geometry.getGeometry(source);
  }
}
