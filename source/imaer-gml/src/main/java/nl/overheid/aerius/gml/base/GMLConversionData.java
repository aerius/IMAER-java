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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.Conversion;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.conversion.MobileSourceOffRoadConversion;
import nl.overheid.aerius.gml.base.conversion.PlanConversion;
import nl.overheid.aerius.gml.base.source.ship.v31.InlandShippingUtil;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.characteristics.CharacteristicsType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.InlandMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.AeriusException.Reason;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.ReceptorUtil;

/**
 * Data object to keep track of conversion data.
 */
public class GMLConversionData {

  private final List<AeriusException> errors;
  private final List<AeriusException> warnings;
  private final Set<String> idMap = new HashSet<>();
  private final GMLLegacyCodeConverter legacyCodeConverter;
  private final int srid;
  private final ReceptorUtil receptorUtil;
  private final Map<EmissionSourceFeature, InlandShippingEmissionSource> extraMooringInlandRoutes = new LinkedHashMap<>();
  private final Map<EmissionSourceFeature, InlandMaritimeShippingEmissionSource> extraMooringMaritimeInlandRoutes = new LinkedHashMap<>();
  private final Map<EmissionSourceFeature, MaritimeMaritimeShippingEmissionSource> extraMooringMaritimeMaritimeRoutes = new LinkedHashMap<>();
  private final List<EmissionSourceFeature> extraSources = new ArrayList<>();
  private final List<BuildingFeature> extraBuildings = new ArrayList<>();
  private final GMLCharacteristicsSupplier characteristicsSupplier;
  private final InlandShippingUtil inlandRouteUtil;

  /**
   * @param gmlHelper Inland shipping supplier, and characteristics supplier for default characteristics
   * @param legacyCodeConverter The converter for old category codes to use.
   * @param errors
   * @param warnings
   * @throws AeriusException
   */
  public GMLConversionData(final GMLHelper helper, final GMLLegacyCodeConverter legacyCodeConverter, final List<AeriusException> errors,
      final List<AeriusException> warnings) throws AeriusException {
    this.legacyCodeConverter = legacyCodeConverter;
    this.characteristicsSupplier = helper;
    this.srid = helper.getReceptorGridSettings().getEpsg().getSrid();
    this.receptorUtil = new ReceptorUtil(helper.getReceptorGridSettings());
    this.errors = errors;
    this.warnings = warnings;
    inlandRouteUtil = new InlandShippingUtil(helper, warnings);
  }

  public int getSrid() {
    return srid;
  }

  public ReceptorUtil getReceptorUtil() {
    return receptorUtil;
  }

  /**
   * @return the errors
   */
  public List<AeriusException> getErrors() {
    return errors;
  }

  /**
   * @return the warnings
   */
  public List<AeriusException> getWarnings() {
    return warnings;
  }

  public CharacteristicsType getCharacteristicsType() {
    return characteristicsSupplier.getCharacteristicsType();
  }

  /**
   * Checks if given id is unique and if not adds it to the list of exceptions.
   * @param gmlId Feature Member String id
   */
  public void putAndTrack(final String gmlId) {
    if (idMap.contains(gmlId)) {
      errors.add(new AeriusException(ImaerExceptionReason.GML_ID_NOT_UNIQUE, gmlId, gmlId));
    } else {
      idMap.add(gmlId);
    }
  }

  /**
   * @param codeType The code type to convert.
   * @param oldCode The old code to try and convert. Will be returned if not converted.
   * @param forSource The label of the source for which the code is converted, used for warnings.
   * @return The proper code to use.
   */
  public String getCode(final GMLLegacyCodeType codeType, final String oldCode, final String forSource) {
    String returnCode;
    final Conversion conversion = legacyCodeConverter.getConversion(codeType, oldCode);
    if (conversion != null) {
      returnCode = conversion.getNewValue();
      if (conversion.isIssueWarning()) {
        warnings.add(new AeriusException(getReason(codeType), oldCode, returnCode, forSource));
      }
    } else {
      returnCode = oldCode;
    }
    return returnCode;
  }

  public int estimageOffRoadOperatingHours(final String oldCode, final int literFuelPerYear) {
    final MobileSourceOffRoadConversion conversion = legacyCodeConverter.getMobileSourceOffRoadConversion(oldCode);
    int estimation = 0;
    if (conversion != null) {
      estimation = conversion.estimageOffRoadOperatingHours(literFuelPerYear);
    }
    return estimation;
  }

  public boolean isInvalidPlanActivityCode(final String oldCode) {
    return legacyCodeConverter.getPlanConversion(oldCode) == null;
  }

  public Map<Substance, Double> determinePlanActivityEmissions(final String oldCode, final int amount) {
    final PlanConversion conversion = legacyCodeConverter.getPlanConversion(oldCode);
    return conversion == null ? Map.of() : conversion.calculateEmissionsForActivity(amount);
  }

  public OPSSourceCharacteristics determinePlanActivityCharacteristics(final String oldCode) {
    final PlanConversion conversion = legacyCodeConverter.getPlanConversion(oldCode);
    return conversion == null ? null : conversion.getCharacteristics();
  }

  private Reason getReason(final GMLLegacyCodeType codeType) {
    final Reason reason;
    switch (codeType) {
    case ON_ROAD_MOBILE_SOURCE:
      reason = ImaerExceptionReason.GML_INVALID_ROAD_CATEGORY_MATCH;
      break;
    case OFF_ROAD_MOBILE_SOURCE:
      reason = ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED;
      break;
    default:
      reason = ImaerExceptionReason.GML_INVALID_CATEGORY_MATCH;
      break;
    }
    return reason;
  }

  public Map<EmissionSourceFeature, InlandShippingEmissionSource> getInlandRoutes() {
    return extraMooringInlandRoutes;
  }

  public Map<EmissionSourceFeature, InlandMaritimeShippingEmissionSource> getMaritimeInlandRoutes() {
    return extraMooringMaritimeInlandRoutes;
  }

  public Map<EmissionSourceFeature, MaritimeMaritimeShippingEmissionSource> getMaritimeMaritimeRoutes() {
    return extraMooringMaritimeMaritimeRoutes;
  }

  public List<EmissionSourceFeature> getExtraSources() {
    return extraSources;
  }

  public List<BuildingFeature> getExtraBuildings() {
    return extraBuildings;
  }

  public InlandWaterway determineInlandWaterway(final String label, final Geometry route) throws AeriusException {
    return inlandRouteUtil.determineInlandWaterWayType(label, route);
  }

  public <S extends SourceCharacteristics> S determineDefaultCharacteristicsBySectorId(final int sectorId) {
    return characteristicsSupplier.determineDefaultCharacteristicsBySectorId(sectorId);
  }
}
