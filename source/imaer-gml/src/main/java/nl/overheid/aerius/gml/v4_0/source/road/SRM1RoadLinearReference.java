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
package nl.overheid.aerius.gml.v4_0.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.v40.IsGmlSRM1RoadLinearReference;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadSpeedType;

/**
 *
 */
@XmlRootElement(name = "SRM1LinearReference", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "SRM1LinearReferenceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"speedProfile", "tunnelFactor"})
public class SRM1RoadLinearReference extends AbstractLinearReference implements IsGmlSRM1RoadLinearReference {

  private RoadSpeedType speedProfile;
  private Double tunnelFactor;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public RoadSpeedType getSpeedProfile() {
    return speedProfile;
  }

  public void setSpeedProfile(final RoadSpeedType speedProfile) {
    this.speedProfile = speedProfile;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getTunnelFactor() {
    return tunnelFactor;
  }

  public void setTunnelFactor(final Double tunnelFactor) {
    this.tunnelFactor = tunnelFactor;
  }

}
