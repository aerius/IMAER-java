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
package nl.overheid.aerius.gml.v1_0.source.road;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlRoadNetwork;
import nl.overheid.aerius.gml.v1_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v1_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v1_0.base.ReferenceType;
import nl.aerius.shared.domain.geojson.GeometryType;

/**
 *
 */
@XmlType(name = "RoadNetworkType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "references"})
public class RoadNetwork extends FeatureMemberImpl implements IsGmlRoadNetwork {

  private String label;
  private ArrayList<ReferenceType> references = new ArrayList<ReferenceType>();

  @Override
  public boolean isValidGeometry(final GeometryType type) {
    //no geometries are actually allowed for this one...
    return false;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  @XmlElement(name = "element", namespace = CalculatorSchema.NAMESPACE)
  public ArrayList<ReferenceType> getReferences() {
    return references;
  }

  public void setReferences(final ArrayList<ReferenceType> references) {
    this.references = references;
  }

}
