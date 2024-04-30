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
package nl.overheid.aerius.gml.v6_0.source.housing;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.housing.IsGmlCustomAdditionalHousingSystem;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.source.EmissionProperty;

/**
 *
 */
@XmlRootElement(name = "CustomAdditionalHousingSystem", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "CustomAdditionalHousingSystemType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"description",
    "airScrubber", "emissionReductionFactors"})
public class CustomAdditionalHousingSystem extends AbstractAdditionalHousingSystem implements IsGmlCustomAdditionalHousingSystem {

  private String description;
  private Boolean airScrubber;
  private List<EmissionProperty> emissionReductionFactors = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Boolean getAirScrubber() {
    return airScrubber;
  }

  public void setAirScrubber(final Boolean airScrubber) {
    this.airScrubber = airScrubber;
  }

  @Override
  @XmlElement(name = "emissionReductionFactor", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionReductionFactors() {
    return emissionReductionFactors;
  }

  public void setEmissionReductionFactors(final List<EmissionProperty> emissionReductionFactors) {
    this.emissionReductionFactors = emissionReductionFactors;
  }

}
