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
package nl.overheid.aerius.gml.v1_0.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 */
@XmlType(name = "NEN3610IDType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"namespace", "id", "version"})
public class NEN3610ID {

  private String namespace = CalculatorSchema.GML_ID_NAMESPACE;
  private String id;
  private String version;

  /**
   * Default constructor, needed for JAXB.
   */
  public NEN3610ID() {
  }

  /**
   * Convenience constructor.
   * @param id The ID to use.
   */
  public NEN3610ID(final String id) {
    this.id = id;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(final String namespace) {
    this.namespace = namespace;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "localId")
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "versionId")
  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

}
