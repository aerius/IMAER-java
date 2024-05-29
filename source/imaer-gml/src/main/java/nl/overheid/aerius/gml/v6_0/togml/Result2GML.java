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
package nl.overheid.aerius.gml.v6_0.togml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.geo.Polygon;
import nl.overheid.aerius.gml.v6_0.result.AbstractCalculationPoint;
import nl.overheid.aerius.gml.v6_0.result.CIMLKCalculationPoint;
import nl.overheid.aerius.gml.v6_0.result.CalculationPointCorrection;
import nl.overheid.aerius.gml.v6_0.result.CalculationPointCorrectionProperty;
import nl.overheid.aerius.gml.v6_0.result.CriticalLevel;
import nl.overheid.aerius.gml.v6_0.result.CriticalLevelProperty;
import nl.overheid.aerius.gml.v6_0.result.CustomCalculationPoint;
import nl.overheid.aerius.gml.v6_0.result.EntityReference;
import nl.overheid.aerius.gml.v6_0.result.EntityReferenceProperty;
import nl.overheid.aerius.gml.v6_0.result.NcaCustomCalculationPoint;
import nl.overheid.aerius.gml.v6_0.result.ReceptorPoint;
import nl.overheid.aerius.gml.v6_0.result.Result;
import nl.overheid.aerius.gml.v6_0.result.ResultProperty;
import nl.overheid.aerius.gml.v6_0.result.SubPoint;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.geo.HexagonUtil;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.NcaPointHeightSupplier;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 * Utility class to convert Deposition objects to GML objects.
 */
final class Result2GML {

  private final Geometry2GML geometry2gml;
  private final HexagonZoomLevel zoomLevel1;

  public Result2GML(final Geometry2GML geometry2gml, final HexagonZoomLevel zoomLevel1) {
    this.geometry2gml = geometry2gml;
    this.zoomLevel1 = zoomLevel1;
  }

  /**
   * Convert a AeriusPoint (custom or not) to a GML object.
   * @param point The point to convert to a GML object.
   * @param substances The substances to use when setting depositions.
   * @param corrections The corrections to add to receptors.
   * @return the GML object representing the AeriusPoint.
   * @throws AeriusException when the objects could not be converted to GML correctly.
   */
  public AbstractCalculationPoint toGML(final CalculationPointFeature feature, final Substance[] substances, final List<CIMLKCorrection> corrections)
      throws AeriusException {
    final CalculationPoint point = feature.getProperties();
    final AbstractCalculationPoint returnPoint = determineSpecificType(point, feature.getGeometry());
    //set the generic properties.
    returnPoint.setGeometry(geometry2gml, feature.getGeometry());
    returnPoint.setId(determineId(point));
    returnPoint.setLabel(point.getLabel());
    returnPoint.setDescription(point.getDescription());
    returnPoint.setJurisdictionId(point.getJurisdictionId());
    if (returnPoint.getRepresentation() != null) {
      returnPoint.getRepresentation().getGmlPolygon().setId(CalculatorSchema.GML_ID_NAMESPACE + ".REPR."
          + point.getId());
    }
    //set emission results (if not 0 and only for the right substances).
    returnPoint.setResults(getResults(point, substances));
    returnPoint.setCorrections(getCorrections(point, corrections));
    return returnPoint;
  }

  private String determineId(final CalculationPoint point) {
    //avoid conflicting IDs by using a prefix.
    if (point instanceof final nl.overheid.aerius.shared.domain.v2.point.SubPoint subPoint) {
      return GMLIdUtil.toValidGmlId(point.getGmlId(), GMLIdUtil.SUB_POINT_PREFIX, subPoint.getReceptorId() + "_" + subPoint.getSubPointId());
    } else {
      return GMLIdUtil.toValidGmlId(point.getGmlId(), GMLIdUtil.POINT_PREFIX, String.valueOf(point.getId()));
    }
  }

  private AbstractCalculationPoint determineSpecificType(final CalculationPoint aeriusPoint, final Point point) throws AeriusException {
    final AbstractCalculationPoint returnPoint;
    if (aeriusPoint instanceof final nl.overheid.aerius.shared.domain.v2.point.CIMLKCalculationPoint cimlkPoint) {
      returnPoint = fromAeriusCalculationPoint(cimlkPoint);
    } else if (aeriusPoint instanceof final nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint ncaPoint) {
      returnPoint = fromNcaCustomPoint(ncaPoint);
    } else if (aeriusPoint instanceof final nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint customPoint) {
      returnPoint = fromCustomPoint(customPoint);
    } else if (aeriusPoint instanceof final nl.overheid.aerius.shared.domain.v2.point.SubPoint subPoint) {
      returnPoint = fromSubPoint(subPoint);
    } else {
      returnPoint = fromReceptorPoint((nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint) aeriusPoint, point);
    }
    return returnPoint;
  }

  private AbstractCalculationPoint fromAeriusCalculationPoint(final nl.overheid.aerius.shared.domain.v2.point.CIMLKCalculationPoint aeriusPoint) {
    //treat as a custom calculation point with added properties
    final CIMLKCalculationPoint point = new CIMLKCalculationPoint();
    point.setRejectionGrounds(aeriusPoint.getRejectionGrounds());
    point.setMonitorSubstance(aeriusPoint.getMonitorSubstance());
    setCustomProperties(aeriusPoint, point);
    return point;
  }

  private AbstractCalculationPoint fromNcaCustomPoint(final nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint aeriusPoint) {
    final NcaCustomCalculationPoint returnPoint = new NcaCustomCalculationPoint();
    returnPoint.setRoadLocalFractionNO2(aeriusPoint.getRoadLocalFractionNO2());
    setCustomProperties(aeriusPoint, returnPoint);
    returnPoint.setHeight(NcaPointHeightSupplier.getHeight(aeriusPoint));
    returnPoint.setEntityReferences(fromEntityReferences(aeriusPoint.getEntityReferences()));
    return returnPoint;
  }

  private List<EntityReferenceProperty> fromEntityReferences(final List<nl.overheid.aerius.shared.domain.v2.point.EntityReference> entityReferences) {
    final List<EntityReferenceProperty> converted = new ArrayList<>();
    if (entityReferences != null) {
      for (final nl.overheid.aerius.shared.domain.v2.point.EntityReference entityReference : entityReferences) {
        converted.add(new EntityReferenceProperty(convert(entityReference)));
      }
    }
    return converted;
  }

  private EntityReference convert(final nl.overheid.aerius.shared.domain.v2.point.EntityReference entityReference) {
    final EntityReference gmlEntityReference = new EntityReference();
    gmlEntityReference.setEntityType(entityReference.getEntityType().name());
    gmlEntityReference.setCode(entityReference.getCode());
    gmlEntityReference.setDescription(entityReference.getDescription());
    if (entityReference.getCriticalLevels() != null) {
      for (final Entry<EmissionResultKey, Double> entry : entityReference.getCriticalLevels().entrySet()) {
        gmlEntityReference.getCriticalLevels().add(new CriticalLevelProperty(convert(entry.getKey(), entry.getValue())));
      }
    }
    return gmlEntityReference;
  }

  private CriticalLevel convert(final EmissionResultKey key, final Double value) {
    final CriticalLevel criticalLevel = new CriticalLevel();
    criticalLevel.setResultType(key.getEmissionResultType());
    criticalLevel.setSubstance(key.getSubstance());
    criticalLevel.setValue(value);
    return criticalLevel;
  }

  private AbstractCalculationPoint fromCustomPoint(final nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint aeriusPoint) {
    //treat as a custom calculation point
    //The proper representation of a custompoint would be a circle with a surface of 1ha.
    //Unclear at this point if this is required for GML and if it should be implemented by a GML polygon, arc or circle.
    //for now, just let the representation be as it isn't as specific as the hexagon.
    final CustomCalculationPoint returnPoint = new CustomCalculationPoint();
    setCustomProperties(aeriusPoint, returnPoint);
    return returnPoint;
  }

  private void setCustomProperties(final nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint aeriusPoint,
      final AbstractCalculationPoint returnPoint) {
    returnPoint.setAssessmentCategory(aeriusPoint.getAssessmentCategory() == null ? null : aeriusPoint.getAssessmentCategory().name());
    returnPoint.setHeight(aeriusPoint.getHeight());
  }

  private AbstractCalculationPoint fromSubPoint(final nl.overheid.aerius.shared.domain.v2.point.SubPoint aeriusPoint) {
    final SubPoint returnSubPoint = new SubPoint();
    returnSubPoint.setSubPointId(aeriusPoint.getSubPointId());
    returnSubPoint.setReceptorPointId(aeriusPoint.getReceptorId());
    returnSubPoint.setLevel(aeriusPoint.getLevel());
    //not adding a representation (yet)
    return returnSubPoint;
  }

  private AbstractCalculationPoint fromReceptorPoint(final nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint aeriusPoint, final Point point)
      throws AeriusException {
    //treat as receptor point.
    final ReceptorPoint returnReceptorPoint = new ReceptorPoint();
    returnReceptorPoint.setReceptorPointId(aeriusPoint.getId());
    returnReceptorPoint.setEdgeEffect(aeriusPoint.getEdgeEffect());
    //receptor are represented by a hexagon.
    final Geometry geometry = HexagonUtil.createHexagon(point, zoomLevel1);
    if (geometry instanceof final nl.overheid.aerius.shared.domain.v2.geojson.Polygon polygon) {
      returnReceptorPoint.setRepresentation(
          geometry2gml.toXMLPolygon(polygon, new Polygon()));
    }
    return returnReceptorPoint;
  }

  private static List<ResultProperty> getResults(final CalculationPoint aeriusPoint, final Substance[] substances) {
    final List<Substance> substanceList = Arrays.asList(substances);
    final List<ResultProperty> results = toResultProperties(aeriusPoint, substanceList);
    //sort the list so the substances ordering is preserved and the ordering is always the same (concentration before deposition).
    //some of the unittests expect this ordering, but for consistency sake it's better to return the results in the same order.
    Collections.sort(results, (o1, o2) -> {
      final Result result1 = o1.getProperty();
      final Result result2 = o2.getProperty();
      int compared = Integer.compare(
          substanceList.indexOf(result1.getSubstance()),
          substanceList.indexOf(result2.getSubstance()));
      if (compared == 0) {
        compared = result1.getResultType().name().compareTo(result2.getResultType().name());
      }
      return compared;
    });
    //to avoid having empty depositions tag, return null when empty.
    return results.isEmpty() ? null : results;
  }

  private static List<ResultProperty> toResultProperties(final CalculationPoint arp, final List<Substance> substanceList) {
    final List<ResultProperty> results = new ArrayList<>();
    toResultTypeProperty(arp, substanceList, results, EmissionResultType.CONCENTRATION);
    toResultTypeProperty(arp, substanceList, results, EmissionResultType.DEPOSITION);
    return results;
  }

  private static void toResultTypeProperty(final CalculationPoint arp, final List<Substance> substanceList, final List<ResultProperty> results,
      final EmissionResultType ert) {
    for (final Entry<EmissionResultKey, Double> entry : arp.getResults().entrySet()) {
      if (substanceList.contains(entry.getKey().getSubstance()) && entry.getValue() != 0 && entry.getKey().getEmissionResultType() == ert) {
        final Result result = new Result();
        result.setSubstance(entry.getKey().getSubstance());
        result.setResultType(entry.getKey().getEmissionResultType());
        result.setValue(entry.getValue());
        final ResultProperty resultProperty = new ResultProperty(result);
        results.add(resultProperty);
      }
    }
  }

  private List<CalculationPointCorrectionProperty> getCorrections(final CalculationPoint aeriusPoint, final List<CIMLKCorrection> corrections) {
    return corrections.stream()
        .filter(correction -> correction.getCalculationPointGmlId().equals(aeriusPoint.getGmlId()))
        .map(this::toGMLCorrection)
        .map(CalculationPointCorrectionProperty::new)
        .toList();
  }

  public CalculationPointCorrection toGMLCorrection(final CIMLKCorrection correction) {
    final CalculationPointCorrection gmlCorrection = new CalculationPointCorrection();
    gmlCorrection.setLabel(correction.getLabel());
    gmlCorrection.setDescription(correction.getDescription());
    gmlCorrection.setJurisdictionId(correction.getJurisdictionId());
    gmlCorrection.setResultType(correction.getResultType());
    gmlCorrection.setSubstance(correction.getSubstance());
    gmlCorrection.setValue(correction.getValue());
    return gmlCorrection;
  }

}
