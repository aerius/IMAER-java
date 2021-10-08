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
package nl.overheid.aerius.gml.v1_1.source;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlEmissionSource;
import nl.overheid.aerius.gml.v1_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v1_1.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v1_1.source.characteristics.EmissionSourceCharacteristics;
import nl.overheid.aerius.gml.v1_1.source.characteristics.EmissionSourceCharacteristicsProperty;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(name = "EmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "characteristicsProperty",
    "emissionSourceGeometryProperty", "emissionValues"})
public class EmissionSource extends FeatureMemberImpl implements IsGmlEmissionSource {

  private String label;
  private int sectorId;
  private EmissionSourceCharacteristics characteristics;
  private List<EmissionProperty> emissionValues = new ArrayList<>();

  @Override
  public boolean isValidGeometry(final GeometryType type) {
    return type == GeometryType.POINT || type == GeometryType.LINESTRING || type == GeometryType.POLYGON;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  @Override
  @XmlAttribute
  public int getSectorId() {
    return sectorId;
  }

  public void setSectorId(final int sectorId) {
    this.sectorId = sectorId;
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
  @XmlElement(name = "emission", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionValues() {
    return emissionValues;
  }

  public void setEmissionValues(final List<EmissionProperty> emissionValues) {
    this.emissionValues = emissionValues;
  }

  @XmlElement(name = "geometry", namespace = CalculatorSchema.NAMESPACE)
  public EmissionSourceGeometryProperty getEmissionSourceGeometryProperty() {
    return new EmissionSourceGeometryProperty(super.getEmissionSourceGeometry());
  }

  /**
   * Method doesn't actually set the property, but sets the EmissionSourceGeometry of this class based on it.
   * @param emissionSourceGeometryProperty The property to use.
   */
  public void setEmissionSourceGeometryProperty(final EmissionSourceGeometryProperty emissionSourceGeometryProperty) {
    super.setEmissionSourceGeometry(emissionSourceGeometryProperty == null ? null : emissionSourceGeometryProperty.getProperty());
  }

  @XmlTransient
  @Override
  public String getDescription() {
    // Not available in this version
    return null;
  }

  @XmlTransient
  @Override
  public Integer getJurisdictionId() {
    // Not available in this version
    return null;
  }
}
