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
package nl.overheid.aerius.gml.v5_0.source.road;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlSRM1Road;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "SRM1Road", namespace = CalculatorSchema.NAMESPACE, propOrder = {"tunnelFactor", "dynamicSegments"})
public class SRM1Road extends RoadEmissionSource implements IsGmlSRM1Road {

  private Double tunnelFactor;
  private List<SRM1RoadLinearReferenceProperty> dynamicSegments = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getTunnelFactor() {
    return tunnelFactor;
  }

  public void setTunnelFactor(final Double tunnelFactor) {
    this.tunnelFactor = tunnelFactor;
  }

  @Override
  @XmlElement(name = "partialChange", namespace = CalculatorSchema.NAMESPACE)
  public List<SRM1RoadLinearReferenceProperty> getDynamicSegments() {
    return dynamicSegments;
  }

  public void setDynamicSegments(final List<SRM1RoadLinearReferenceProperty> dynamicSegments) {
    this.dynamicSegments = dynamicSegments;
  }

}
