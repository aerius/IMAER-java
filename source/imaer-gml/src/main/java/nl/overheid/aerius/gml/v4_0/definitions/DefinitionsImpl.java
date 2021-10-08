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
package nl.overheid.aerius.gml.v4_0.definitions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.Definitions;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;

@XmlType(name = "DefinitionsType", namespace = CalculatorSchema.NAMESPACE)
public class DefinitionsImpl implements Definitions {

  private List<CustomDiurnalVariationProperty> customDiurnalVariationProperties = new ArrayList<>();

  @XmlElement(name = "customDiurnalVariation", namespace = CalculatorSchema.NAMESPACE)
  public List<CustomDiurnalVariationProperty> getCustomDiurnalVariationProperties() {
    return customDiurnalVariationProperties;
  }

  public void setCustomDiurnalVariationProperties(final List<CustomDiurnalVariationProperty> properties) {
    customDiurnalVariationProperties = properties;
  }

  @Override
  @XmlTransient
  public List<CustomDiurnalVariation> getCustomDiurnalVariations() {
    return customDiurnalVariationProperties.stream()
        .map(CustomDiurnalVariationProperty::getProperty)
        .collect(Collectors.toList());
  }

  public void setCustomDiurnalVariations(final List<CustomDiurnalVariation> customDiurnalVariations) {
    if (customDiurnalVariations == null) {
      customDiurnalVariationProperties = new ArrayList<>();
    } else {
      customDiurnalVariationProperties = customDiurnalVariations.stream()
          .map(CustomDiurnalVariationProperty::new)
          .collect(Collectors.toList());
    }
  }

}
