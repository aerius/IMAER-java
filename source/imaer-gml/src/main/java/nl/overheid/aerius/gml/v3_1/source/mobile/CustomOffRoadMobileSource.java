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
package nl.overheid.aerius.gml.v3_1.source.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlCustomOffRoadMobileSource;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_1.source.EmissionProperty;
import nl.overheid.aerius.gml.v3_1.source.characteristics.EmissionSourceCharacteristics;
import nl.overheid.aerius.gml.v3_1.source.characteristics.EmissionSourceCharacteristicsProperty;

/**
 *
 */
@XmlRootElement(name = "CustomOffRoadMobileSource", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "CustomOffRoadMobileSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissions", "characteristicsProperty",
    "vehicleSpecificationProperty"})
public class CustomOffRoadMobileSource extends AbstractOffRoadMobileSource implements IsGmlCustomOffRoadMobileSource {

  private List<EmissionProperty> emissions = new ArrayList<>();
  private EmissionSourceCharacteristics characteristics;
  private AbstractOffRoadVehicleSpecification offRoadVehicleSpecification;

  @Override
  @XmlElement(name = "emission", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissions() {
    return emissions;
  }

  public void setEmissions(final List<EmissionProperty> emissions) {
    this.emissions = emissions;
  }

  @Override
  @XmlTransient
  public EmissionSourceCharacteristics getCharacteristics() {
    return characteristics;
  }

  public void setCharacteristics(final EmissionSourceCharacteristics characteristics) {
    this.characteristics = characteristics;
  }

  @XmlElement(name = "emissionSourceCharacteristics", namespace = CalculatorSchema.NAMESPACE)
  public EmissionSourceCharacteristicsProperty getCharacteristicsProperty() {
    return characteristics == null ? null : new EmissionSourceCharacteristicsProperty(characteristics);
  }

  public void setCharacteristicsProperty(final EmissionSourceCharacteristicsProperty characteristics) {
    this.characteristics = characteristics == null ? null : characteristics.getProperty();
  }

  @Override
  @XmlTransient
  public AbstractOffRoadVehicleSpecification getOffRoadVehicleSpecification() {
    return offRoadVehicleSpecification;
  }

  public void setOffRoadVehicleSpecification(final AbstractOffRoadVehicleSpecification offRoadVehicleSpecification) {
    this.offRoadVehicleSpecification = offRoadVehicleSpecification;
  }

  @XmlElement(name = "offRoadVehicleSpecification", namespace = CalculatorSchema.NAMESPACE)
  public OffRoadVehicleSpecificationProperty getVehicleSpecificationProperty() {
    return offRoadVehicleSpecification == null ? null : new OffRoadVehicleSpecificationProperty(offRoadVehicleSpecification);
  }

  public void setVehicleSpecificationProperty(final OffRoadVehicleSpecificationProperty offRoadVehicleSpecification) {
    this.offRoadVehicleSpecification = offRoadVehicleSpecification == null ? null : offRoadVehicleSpecification.getProperty();
  }

}
