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
package nl.overheid.aerius.gml.v5_1.source.mobile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v40.IsGmlOffRoadMobileSource;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlSeeAlso({OffRoadMobileSource.class, CustomOffRoadMobileSource.class})
@XmlType(name = "OffRoadMobileSourceType", namespace = CalculatorSchema.NAMESPACE)
public class AbstractOffRoadMobileSource implements IsGmlOffRoadMobileSource {

  private String description;

  @Override
  @XmlElement(name = "description", namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

}
