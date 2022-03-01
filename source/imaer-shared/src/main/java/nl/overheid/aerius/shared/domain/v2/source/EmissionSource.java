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
package nl.overheid.aerius.shared.domain.v2.source;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

@JsonTypeInfo(property = "emissionSourceType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = GenericEmissionSource.class, name = EmissionSourceType.Names.GENERIC),
    @Type(value = FarmLodgingEmissionSource.class, name = EmissionSourceType.Names.FARM_LODGE),
    @Type(value = FarmlandEmissionSource.class, name = EmissionSourceType.Names.FARMLAND),
    @Type(value = PlanEmissionSource.class, name = EmissionSourceType.Names.PLAN),
    @Type(value = OffRoadMobileEmissionSource.class, name = EmissionSourceType.Names.OFFROAD_MOBILE),
    @Type(value = SRM1RoadEmissionSource.class, name = EmissionSourceType.Names.SRM1_ROAD),
    @Type(value = SRM2RoadEmissionSource.class, name = EmissionSourceType.Names.SRM2_ROAD),
    @Type(value = ADMSRoadEmissionSource.class, name = EmissionSourceType.Names.ADMS_ROAD),
    @Type(value = InlandShippingEmissionSource.class, name = EmissionSourceType.Names.SHIPPING_INLAND),
    @Type(value = MooringInlandShippingEmissionSource.class, name = EmissionSourceType.Names.SHIPPING_INLAND_DOCKED),
    @Type(value = InlandMaritimeShippingEmissionSource.class, name = EmissionSourceType.Names.SHIPPING_MARITIME_INLAND),
    @Type(value = MaritimeMaritimeShippingEmissionSource.class, name = EmissionSourceType.Names.SHIPPING_MARITIME_MARITIME),
    @Type(value = MooringMaritimeShippingEmissionSource.class, name = EmissionSourceType.Names.SHIPPING_MARITIME_DOCKED),
})
public abstract class EmissionSource implements Serializable {

  private static final long serialVersionUID = 1L;

  private String gmlId;
  private int sectorId;
  private String label;
  private String description;
  private Integer jurisdictionId;
  private SourceCharacteristics characteristics;
  private Map<Substance, Double> emissions = new HashMap<>();

  public String getGmlId() {
    return gmlId;
  }

  public void setGmlId(final String gmlId) {
    this.gmlId = gmlId;
  }

  public int getSectorId() {
    return sectorId;
  }

  public void setSectorId(final int sectorId) {
    this.sectorId = sectorId;
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

  @Valid
  public SourceCharacteristics getCharacteristics() {
    return characteristics;
  }

  public void setCharacteristics(final SourceCharacteristics characteristics) {
    this.characteristics = characteristics;
  }

  public Map<Substance, Double> getEmissions() {
    return emissions;
  }

  public void setEmissions(final Map<Substance, Double> emissions) {
    this.emissions = emissions;
  }

  /**
   * Method should call visitor.visit(this, feature).
   * @param visitor visitor
   * @param <T> visitor specific data type
   * @return returns object from the visitor.
   * @throws AeriusException error
   */
  abstract <T> T accept(EmissionSourceVisitor<T> visitor, IsFeature feature) throws AeriusException;

}
