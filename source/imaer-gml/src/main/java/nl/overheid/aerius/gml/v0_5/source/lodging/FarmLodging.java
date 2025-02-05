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
package nl.overheid.aerius.gml.v0_5.source.lodging;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.lodging.IsGmlLodgingFodderMeasure;
import nl.overheid.aerius.gml.base.source.lodging.IsGmlStandardFarmLodging;
import nl.overheid.aerius.gml.v0_5.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardFarmLodging", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardFarmLodgingType", namespace = CalculatorSchema.NAMESPACE)
public class FarmLodging extends AbstractFarmLodging implements IsGmlStandardFarmLodging {

  private String code;
  private String lodgingSystemDefinitionCode;
  private List<LodgingSystemProperty> lodgingSystems = new ArrayList<>();

  @Override
  @XmlAttribute(name = "farmLodgingType")
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

  @Override
  @XmlElement(name = "lodgingSystem", namespace = CalculatorSchema.NAMESPACE)
  public List<LodgingSystemProperty> getLodgingSystems() {
    return lodgingSystems;
  }

  public void setLodgingSystems(final List<LodgingSystemProperty> lodgingSystems) {
    this.lodgingSystems = lodgingSystems;
  }

  @XmlTransient
  @Override
  public List<? extends IsGmlProperty<IsGmlLodgingFodderMeasure>> getFodderMeasures() {
    // Not available in this version
    return List.of();
  }

}
