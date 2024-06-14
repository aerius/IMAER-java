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
package nl.overheid.aerius.util;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.CIMLKCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointVisitor;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint;
import nl.overheid.aerius.shared.domain.v2.point.SubPoint;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Class to copy CalculationPointFeature.
 *
 * Will not copy any properties that can be deemed a result, like any emission results.
 */
public class CalculationPointDuplicator implements CalculationPointVisitor<CalculationPointFeature> {

  @Override
  public CalculationPointFeature visit(final ReceptorPoint calculationPoint, final IsFeature feature) throws AeriusException {
    return duplicate(feature, constructDuplicateReceptor(calculationPoint));
  }

  @Override
  public CalculationPointFeature visit(final CustomCalculationPoint calculationPoint, final IsFeature feature) throws AeriusException {
    return duplicate(feature, constructDuplicateCustomPoint(calculationPoint));
  }

  @Override
  public CalculationPointFeature visit(final NcaCustomCalculationPoint calculationPoint, final IsFeature feature) throws AeriusException {
    return duplicate(feature, constructDuplicateNcaPoint(calculationPoint));
  }

  @Override
  public CalculationPointFeature visit(final CIMLKCalculationPoint calculationPoint, final IsFeature feature) throws AeriusException {
    return duplicate(feature, constructDuplicateCimlkPoint(calculationPoint));
  }

  @Override
  public CalculationPointFeature visit(final SubPoint calculationPoint, final IsFeature feature) throws AeriusException {
    return duplicate(feature, constructDuplicateSubPoint(calculationPoint));
  }

  protected ReceptorPoint constructDuplicateReceptor(final ReceptorPoint originalPoint) {
    final ReceptorPoint duplicateReceptor = new ReceptorPoint();
    duplicateReceptor.setReceptorId(originalPoint.getReceptorId());
    // Do not copy edge effect: this is considered a result.
    copyBaseProperties(originalPoint, duplicateReceptor);
    return duplicateReceptor;
  }

  protected CustomCalculationPoint constructDuplicateCustomPoint(final CustomCalculationPoint originalPoint) {
    final CustomCalculationPoint duplicateCustomPoint = new CustomCalculationPoint();
    copyCustomProperties(originalPoint, duplicateCustomPoint);
    return duplicateCustomPoint;
  }

  protected CustomCalculationPoint constructDuplicateNcaPoint(final NcaCustomCalculationPoint originalPoint) {
    final NcaCustomCalculationPoint duplicateNcaPoint = new NcaCustomCalculationPoint();
    duplicateNcaPoint.setRoadLocalFractionNO2(originalPoint.getRoadLocalFractionNO2());
    duplicateNcaPoint.setEntityReferences(originalPoint.getEntityReferences());
    copyCustomProperties(originalPoint, duplicateNcaPoint);
    return duplicateNcaPoint;
  }

  protected CustomCalculationPoint constructDuplicateCimlkPoint(final CIMLKCalculationPoint originalPoint) {
    final CIMLKCalculationPoint duplicateCimlkPoint = new CIMLKCalculationPoint();
    duplicateCimlkPoint.setMonitorSubstance(originalPoint.getMonitorSubstance());
    duplicateCimlkPoint.setRejectionGrounds(originalPoint.getRejectionGrounds());
    copyCustomProperties(originalPoint, duplicateCimlkPoint);
    return duplicateCimlkPoint;
  }

  protected SubPoint constructDuplicateSubPoint(final SubPoint originalPoint) {
    final SubPoint duplicateSubPoint = new SubPoint();
    duplicateSubPoint.setSubPointId(originalPoint.getSubPointId());
    duplicateSubPoint.setReceptorId(originalPoint.getReceptorId());
    duplicateSubPoint.setLevel(originalPoint.getLevel());
    copyBaseProperties(originalPoint, duplicateSubPoint);
    return duplicateSubPoint;
  }

  protected void copyCustomProperties(final CustomCalculationPoint originalPoint, final CustomCalculationPoint duplicatePoint) {
    duplicatePoint.setCustomPointId(originalPoint.getCustomPointId());
    duplicatePoint.setHeight(originalPoint.getHeight());
    duplicatePoint.setAssessmentCategory(originalPoint.getAssessmentCategory());
    copyBaseProperties(originalPoint, duplicatePoint);
  }

  protected void copyBaseProperties(final CalculationPoint originalPoint, final CalculationPoint duplicatePoint) {
    duplicatePoint.setDescription(originalPoint.getDescription());
    duplicatePoint.setGmlId(originalPoint.getGmlId());
    duplicatePoint.setJurisdictionId(originalPoint.getJurisdictionId());
    duplicatePoint.setLabel(originalPoint.getLabel());
  }

  private static CalculationPointFeature duplicate(final IsFeature originalFeature, final CalculationPoint duplicateProperties) {
    final CalculationPointFeature duplicateFeature = new CalculationPointFeature();
    duplicateFeature.setId(originalFeature.getId());
    final Point originalPoint = (Point) originalFeature.getGeometry();
    duplicateFeature.setGeometry(originalPoint == null ? null : new Point(originalPoint.getX(), originalPoint.getY()));
    duplicateFeature.setProperties(duplicateProperties);
    return duplicateFeature;
  }

}
