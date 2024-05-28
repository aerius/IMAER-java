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
package nl.overheid.aerius.gml.v6_0.result;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlEntityReference;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "EntityReferenceType", propOrder = {"entityType", "code", "description", "criticalLevels"})
public class EntityReference implements IsGmlEntityReference {

  private String entityType;
  private String code;
  private String description;
  private List<CriticalLevelProperty> criticalLevels = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(final String entityType) {
    this.entityType = entityType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlElement(name = "criticalLevel", namespace = CalculatorSchema.NAMESPACE)
  public List<CriticalLevelProperty> getCriticalLevels() {
    return criticalLevels;
  }

  public void setCriticalLevels(final List<CriticalLevelProperty> criticalLevels) {
    this.criticalLevels = criticalLevels;
  }

}
