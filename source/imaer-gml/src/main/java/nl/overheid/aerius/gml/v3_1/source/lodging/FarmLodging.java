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
package nl.overheid.aerius.gml.v3_1.source.lodging;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.lodging.IsGmlStandardFarmLodging;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardFarmLodging", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardFarmLodgingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"lodgingSystemDefinitionCode", "fodderMeasures",
    "lodgingSystems"})
public class FarmLodging extends AbstractFarmLodging implements IsGmlStandardFarmLodging {

  private String code;
  private String lodgingSystemDefinitionCode;
  private List<LodgingFodderMeasureProperty> fodderMeasures = new ArrayList<>();
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
  @XmlElement(name = "fodderMeasure", namespace = CalculatorSchema.NAMESPACE)
  public List<LodgingFodderMeasureProperty> getFodderMeasures() {
    return fodderMeasures;
  }

  public void setFodderMeasures(final List<LodgingFodderMeasureProperty> fodderMeasures) {
    this.fodderMeasures = fodderMeasures;
  }

  @Override
  @XmlElement(name = "lodgingSystem", namespace = CalculatorSchema.NAMESPACE)
  public List<LodgingSystemProperty> getLodgingSystems() {
    return lodgingSystems;
  }

  public void setLodgingSystems(final List<LodgingSystemProperty> lodgingSystems) {
    this.lodgingSystems = lodgingSystems;
  }

}
