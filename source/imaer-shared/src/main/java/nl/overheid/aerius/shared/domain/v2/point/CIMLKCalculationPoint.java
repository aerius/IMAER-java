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
package nl.overheid.aerius.shared.domain.v2.point;

import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMonitorSubstance;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKRejectionGrounds;
import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

public class CIMLKCalculationPoint extends CustomCalculationPoint {

  private static final long serialVersionUID = 1L;

  private CIMLKMonitorSubstance monitorSubstance;
  private CIMLKRejectionGrounds rejectionGrounds;

  public CIMLKMonitorSubstance getMonitorSubstance() {
    return monitorSubstance;
  }

  public void setMonitorSubstance(final CIMLKMonitorSubstance monitorSubstance) {
    this.monitorSubstance = monitorSubstance;
  }

  public CIMLKRejectionGrounds getRejectionGrounds() {
    return rejectionGrounds;
  }

  public void setRejectionGrounds(final CIMLKRejectionGrounds rejectionGrounds) {
    this.rejectionGrounds = rejectionGrounds;
  }

  @Override
  <T> T accept(final CalculationPointVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
