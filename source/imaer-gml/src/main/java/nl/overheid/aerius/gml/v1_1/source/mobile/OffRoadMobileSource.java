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
package nl.overheid.aerius.gml.v1_1.source.mobile;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlStandardOffRoadMobileSource;
import nl.overheid.aerius.gml.v1_1.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardOffRoadMobileSource", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardOffRoadMobileSourceType", namespace = CalculatorSchema.NAMESPACE)
public class OffRoadMobileSource extends AbstractOffRoadMobileSource implements IsGmlStandardOffRoadMobileSource {

  private int literFuelPerYear;
  private String code;

  @Override
  @XmlElement(name = "literFuelPerYear", namespace = CalculatorSchema.NAMESPACE)
  public int getLiterFuelPerYear() {
    return literFuelPerYear;
  }

  public void setLiterFuelPerYear(final int literFuelPerYear) {
    this.literFuelPerYear = literFuelPerYear;
  }

  @Override
  @XmlAttribute(name = "offRoadMobileSourceType")
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  public Integer getHoursIdlePerYear() {
    // Not available in this version
    return null;
  }

  @Override
  public Double getEngineDisplacement() {
    // Not available in this version
    return null;
  }

}
