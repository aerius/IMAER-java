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
package nl.overheid.aerius.gml.v6_0.source.farmland;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.farmland.IsGmlFarmlandActivity;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlSeeAlso({StandardFarmlandActivity.class, CustomFarmlandActivity.class})
@XmlType(name = "AbstractFarmlandActivityType", namespace = CalculatorSchema.NAMESPACE)
public abstract class AbstractFarmlandActivity implements IsGmlFarmlandActivity {

  private String code;

  @Override
  @XmlAttribute(name = "activityType")
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

}
