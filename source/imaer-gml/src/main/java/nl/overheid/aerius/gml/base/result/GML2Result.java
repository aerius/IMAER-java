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
package nl.overheid.aerius.gml.base.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.NcaPointHeightSupplier;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.AssessmentCategory;
import nl.overheid.aerius.shared.domain.v2.point.CIMLKCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.EntityReference;
import nl.overheid.aerius.shared.domain.v2.point.EntityType;
import nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint;
import nl.overheid.aerius.shared.domain.v2.point.SubPoint;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.ReceptorUtil;

/**
 * Utility class to convert GML objects to result objects.
 */
public class GML2Result {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Result.class);

  private final GMLConversionData conversionData;
  private final GML2Geometry gml2geometry;
  private final ReceptorUtil receptorUtil;

  public GML2Result(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
    gml2geometry = new GML2Geometry(conversionData.getSrid());
    this.receptorUtil = conversionData.getReceptorUtil();
  }

  public List<CalculationPointFeature> fromGML(final List<FeatureMember> members, final boolean includeResults) {
    final List<CalculationPointFeature> calculationPoints = new ArrayList<>();
    for (final FeatureMember member : members) {
      if (member instanceof final IsGmlCalculationPoint point) {
        try {
          calculationPoints.add(fromGML(point, includeResults));
        } catch (final AeriusException e) {
          conversionData.getErrors().add(e);
        }
      }
    }
    return calculationPoints;
  }

  /**
   * Convert GML Calculation/Result points to AeriusPoints (custom or not).
   * @param calculationPoint The GML-representation of a calculation point.
   * @param includeResults if true get the results from the GML point
   * @return The receptor point.
   * @throws AeriusException When an exception occurs parsing.
   */
  public CalculationPointFeature fromGML(final IsGmlCalculationPoint calculationPoint, final boolean includeResults) throws AeriusException {
    final CalculationPointFeature feature = new CalculationPointFeature();
    feature.setId(calculationPoint.getId());
    final Point point = determineGeometry(calculationPoint);
    feature.setGeometry(point);
    final CalculationPoint returnPoint = determineSpecificType(calculationPoint);
    fillAeriusPoint(calculationPoint, returnPoint);
    if (includeResults) {
      includeResults(returnPoint, calculationPoint);
    }
    feature.setProperties(returnPoint);
    return feature;
  }

  private CalculationPoint determineSpecificType(final IsGmlCalculationPoint calculationPoint) throws AeriusException {
    final CalculationPoint returnPoint;
    if (calculationPoint instanceof final IsGmlReceptorPoint receptor) {
      returnPoint = createReceptorPoint(receptor);
    } else if (calculationPoint instanceof final IsGmlSubPoint subPoint) {
      returnPoint = createSubPoint(subPoint);
    } else if (calculationPoint instanceof final IsGmlCIMLKCalculationPoint cimlkPoint) {
      returnPoint = createCIMLKCalculationPoint(cimlkPoint);
    } else if (calculationPoint instanceof final IsGmlNcaCustomCalculationPoint ncaPoint) {
      returnPoint = createNcaCustomCalculationPoint(ncaPoint);
    } else if (calculationPoint instanceof final IsGmlCustomCalculationPoint customPoint) {
      returnPoint = createCustomPoint(customPoint);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Could not handle calculation point: " + calculationPoint.getClass());
    }
    returnPoint.setGmlId(calculationPoint.getId());

    return returnPoint;
  }

  private Point determineGeometry(final IsGmlCalculationPoint origin) throws AeriusException {
    final Point point;
    if (origin instanceof final IsGmlReceptorPoint receptor) {
      point = toReceptorPoint(receptor);
    } else {
      point = toGeometry(origin);
    }
    return point;
  }

  private Point toGeometry(final IsGmlCalculationPoint origin) throws AeriusException {
    try {
      final Geometry geometry = gml2geometry.getGeometry(origin);
      if (geometry instanceof final Point point) {
        return point;
      } else {
        LOG.trace("Unexpected geometry type after conversion ({}), throwing AeriusException", geometry);
        throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, origin.getId());
      }
    } catch (final AeriusException e) {
      LOG.trace("Geometry exception, rethrown as AeriusException", e);
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, origin.getId());
    }
  }

  private Point toReceptorPoint(final IsGmlReceptorPoint origin) {
    return receptorUtil.getPointFromReceptorId(origin.getReceptorPointId());
  }

  private void fillAeriusPoint(final IsGmlCalculationPoint origin, final CalculationPoint target) {
    target.setLabel(origin.getLabel());
    target.setDescription(origin.getDescription());
    target.setJurisdictionId(origin.getJurisdictionId());
  }

  private ReceptorPoint createReceptorPoint(final IsGmlReceptorPoint origin) {
    final ReceptorPoint target = new ReceptorPoint();
    target.setReceptorId(origin.getReceptorPointId());
    return target;
  }

  private SubPoint createSubPoint(final IsGmlSubPoint origin) {
    final SubPoint target = new SubPoint();
    target.setSubPointId(origin.getSubPointId());
    target.setReceptorId(origin.getReceptorPointId());
    target.setLevel(origin.getLevel());
    return target;
  }

  private CustomCalculationPoint createCustomPoint(final IsGmlCustomCalculationPoint origin) {
    final CustomCalculationPoint target = new CustomCalculationPoint();
    setCustomProperties(origin, target);
    return target;
  }

  private CIMLKCalculationPoint createCIMLKCalculationPoint(final IsGmlCIMLKCalculationPoint origin) {
    final CIMLKCalculationPoint target = new CIMLKCalculationPoint();
    target.setRejectionGrounds(origin.getRejectionGrounds());
    target.setMonitorSubstance(origin.getMonitorSubstance());
    setCustomProperties(origin, target);
    return target;
  }

  private NcaCustomCalculationPoint createNcaCustomCalculationPoint(final IsGmlNcaCustomCalculationPoint origin) {
    final NcaCustomCalculationPoint target = new NcaCustomCalculationPoint();
    target.setRoadLocalFractionNO2(origin.getRoadLocalFractionNO2());
    setCustomProperties(origin, target);
    target.setHeight(NcaPointHeightSupplier.getHeight(target.getAssessmentCategory(), origin.getHeight()));
    target.setEntityReferences(createEntityReferences(origin.getEntityReferences()));
    return target;
  }

  private void setCustomProperties(final IsGmlCalculationPoint origin, final CustomCalculationPoint target) {
    target.setAssessmentCategory(AssessmentCategory.safeValueOf(origin.getAssessmentCategory()));
    target.setHeight(origin.getHeight());
  }

  private List<EntityReference> createEntityReferences(final List<? extends IsGmlProperty<IsGmlEntityReference>> entityReferenceProperties) {
    final List<EntityReference> converted = new ArrayList<>();
    if (entityReferenceProperties != null) {
      for (final IsGmlProperty<IsGmlEntityReference> entityReferenceProperty : entityReferenceProperties) {
        converted.add(convert(entityReferenceProperty));
      }
    }
    return converted;
  }

  private EntityReference convert(final IsGmlProperty<IsGmlEntityReference> entityReferenceProperty) {
    final IsGmlEntityReference gmlObject = entityReferenceProperty.getProperty();
    final EntityReference convert = new EntityReference();
    convert.setEntityType(EntityType.safeValueOf(gmlObject.getEntityType()));
    convert.setCode(gmlObject.getCode());
    convert.setDescription(gmlObject.getDescription());
    convert.setCriticalLevels(convert(gmlObject.getCriticalLevels()));
    return convert;
  }

  private Map<EmissionResultKey, Double> convert(final List<? extends IsGmlProperty<IsGmlCriticalLevel>> criticalLevelProperties) {
    final Map<EmissionResultKey, Double> converted = new HashMap<>();
    if (criticalLevelProperties != null) {
      for (final IsGmlProperty<IsGmlCriticalLevel> criticalLevelProperty : criticalLevelProperties) {
        final IsGmlCriticalLevel gmlCriticalLevel = criticalLevelProperty.getProperty();
        final EmissionResultKey key = EmissionResultKey.safeValueOf(gmlCriticalLevel.getSubstance(), gmlCriticalLevel.getResultType());
        if (key != null) {
          converted.put(key, gmlCriticalLevel.getValue());
        }
      }
    }
    return converted;
  }

  /**
   * Get results from GML object.
   * @param resultPoint
   * @param calculationPoint
   */
  private void includeResults(final CalculationPoint resultPoint, final IsGmlCalculationPoint calculationPoint) {
    for (final IsGmlProperty<IsGmlResult> result : calculationPoint.getResults()) {
      final IsGmlResult p = result.getProperty();
      resultPoint.getResults().put(EmissionResultKey.safeValueOf(p.getSubstance(), p.getResultType()), p.getValue());
    }
  }

}
