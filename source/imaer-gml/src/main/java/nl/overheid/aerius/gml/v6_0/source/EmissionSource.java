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
package nl.overheid.aerius.gml.v6_0.source;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlEmissionSource;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v6_0.source.characteristics.AbstractSourceCharacteristics;
import nl.overheid.aerius.gml.v6_0.source.characteristics.EmissionSourceCharacteristicsProperty;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(name = "EmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "description", "characteristicsProperty",
    "jurisdictionId", "emissionSourceGeometryProperty", "emissionValues"})
public class EmissionSource extends FeatureMemberImpl implements IsGmlEmissionSource {

  private String label;
  private String description;
  private int sectorId;
  private AbstractSourceCharacteristics characteristics;
  private Integer jurisdictionId;
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
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
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
  public AbstractSourceCharacteristics getCharacteristics() {
    return characteristics;
  }

  public void setCharacteristics(final AbstractSourceCharacteristics characteristics) {
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
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(final Integer jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
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
}
