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
package nl.overheid.aerius.shared.domain.v2.point;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

@JsonTypeInfo(property = "calculationPointType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = ReceptorPoint.class, name = CalculationPointType.Names.RECEPTOR),
    @Type(value = CustomCalculationPoint.class, name = CalculationPointType.Names.CUSTOM_CALCULATION_POINT),
    @Type(value = NSLCalculationPoint.class, name = CalculationPointType.Names.NSL_CALCULATION_POINT),
    @Type(value = SubPoint.class, name = CalculationPointType.Names.SUB_POINT)
})
public abstract class CalculationPoint implements Serializable {

  private static final long serialVersionUID = 1L;

  private String gmlId;
  private String label;
  private String description;
  private Integer jurisdictionId;
  private Map<EmissionResultKey, Double> results = new HashMap<>();

  public String getGmlId() {
    return gmlId;
  }

  public void setGmlId(final String gmlId) {
    this.gmlId = gmlId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Integer getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(final Integer jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  public Map<EmissionResultKey, Double> getResults() {
    return results;
  }

  public void setResults(final Map<EmissionResultKey, Double> results) {
    this.results = results;
  }

  @JsonIgnore
  public abstract int getId();

  /**
   * Method should call visitor.visit(this, feature).
   * @param visitor visitor
   * @param <T> visitor specific data type
   * @return returns object from the visitor.
   * @throws AeriusException error
   */
  abstract <T> T accept(CalculationPointVisitor<T> visitor, IsFeature feature) throws AeriusException;

}
