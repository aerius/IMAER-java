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
package nl.overheid.aerius.gml.v3_0.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlNSLCalculationPoint;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMonitorSubstance;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLRejectionGrounds;

/**
 *
 */
@XmlType(name = "NSLCalculationPointType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"rejectionGrounds", "monitorSubstance"})
public class NSLCalculationPoint extends AbstractCalculationPoint implements IsGmlNSLCalculationPoint {

  private NSLRejectionGrounds rejectionGrounds;
  private NSLMonitorSubstance monitorSubstance;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public NSLRejectionGrounds getRejectionGrounds() {
    return rejectionGrounds;
  }

  public void setRejectionGrounds(final NSLRejectionGrounds rejectionGrounds) {
    this.rejectionGrounds = rejectionGrounds;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public NSLMonitorSubstance getMonitorSubstance() {
    return monitorSubstance;
  }

  public void setMonitorSubstance(final NSLMonitorSubstance monitorSubstance) {
    this.monitorSubstance = monitorSubstance;
  }

}
