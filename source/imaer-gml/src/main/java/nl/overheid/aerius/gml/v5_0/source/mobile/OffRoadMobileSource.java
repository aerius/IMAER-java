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
package nl.overheid.aerius.gml.v5_0.source.mobile;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v40.IsGmlStandardOffRoadMobileSource;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardOffRoadMobileSource", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardOffRoadMobileSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"literFuelPerYear", "operatingHoursPerYear",
    "literAdBluePerYear"})
public class OffRoadMobileSource extends AbstractOffRoadMobileSource implements IsGmlStandardOffRoadMobileSource {

  private String code;
  private Integer literFuelPerYear;
  private Integer operatingHoursPerYear;
  private Integer literAdBluePerYear;

  @Override
  @XmlAttribute(name = "offRoadMobileSourceType")
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getLiterFuelPerYear() {
    return literFuelPerYear;
  }

  public void setLiterFuelPerYear(final Integer literFuelPerYear) {
    this.literFuelPerYear = literFuelPerYear;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getOperatingHoursPerYear() {
    return operatingHoursPerYear;
  }

  public void setOperatingHoursPerYear(final Integer operatingHoursPerYear) {
    this.operatingHoursPerYear = operatingHoursPerYear;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getLiterAdBluePerYear() {
    return literAdBluePerYear;
  }

  public void setLiterAdBluePerYear(final Integer literAdBluePerYear) {
    this.literAdBluePerYear = literAdBluePerYear;
  }

}
