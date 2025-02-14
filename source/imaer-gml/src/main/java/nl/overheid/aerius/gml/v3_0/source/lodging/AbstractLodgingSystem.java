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
package nl.overheid.aerius.gml.v3_0.source.lodging;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.lodging.IsGmlLodgingSystem;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;

/**
 *
 */
@XmlSeeAlso({AdditionalLodgingSystem.class, ReductiveLodgingSystem.class})
@XmlType(name = "LodgingSystemType", namespace = CalculatorSchema.NAMESPACE)
public abstract class AbstractLodgingSystem implements IsGmlLodgingSystem {

  private String code;
  private String lodgingSystemDefinitionCode;

  @Override
  @XmlElement(name = "lodgingSystemType", namespace = CalculatorSchema.NAMESPACE)
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  @XmlElement(name = "farmLodgingSystemDefinitionType", namespace = CalculatorSchema.NAMESPACE)
  public String getLodgingSystemDefinitionCode() {
    return lodgingSystemDefinitionCode;
  }

  public void setLodgingSystemDefinitionCode(final String lodgingSystemDefinitionCode) {
    this.lodgingSystemDefinitionCode = lodgingSystemDefinitionCode;
  }

}
