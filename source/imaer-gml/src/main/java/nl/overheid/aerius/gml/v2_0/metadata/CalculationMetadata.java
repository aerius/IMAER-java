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
package nl.overheid.aerius.gml.v2_0.metadata;

import java.util.List;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 *
 */
@XmlType(name = "CalculationMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"calculationType", "substances", "resultTypes",
    "maximumRange", "researchArea"})
public class CalculationMetadata {

  private CalculationTypeLegacy calculationType;
  private List<Substance> substances;
  private List<EmissionResultType> resultTypes;
  private Double maximumRange;
  private Boolean researchArea;

  @XmlElement(name = "type", namespace = CalculatorSchema.NAMESPACE)
  public CalculationTypeLegacy getCalculationType() {
    return calculationType;
  }

  public void setCalculationType(final CalculationTypeLegacy calculationType) {
    this.calculationType = calculationType;
  }

  public void setCalculationType(final CalculationType calculationType) {
    setCalculationType(CalculationTypeLegacy.to(calculationType));
  }

  @XmlElement(name = "substance", namespace = CalculatorSchema.NAMESPACE)
  public List<Substance> getSubstances() {
    return substances;
  }

  public void setSubstances(final List<Substance> substances) {
    this.substances = substances;
  }

  @XmlElement(name = "resultType", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionResultType> getResultTypes() {
    return resultTypes;
  }

  public void setResultTypes(final List<EmissionResultType> resultTypes) {
    this.resultTypes = resultTypes;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getMaximumRange() {
    return maximumRange;
  }

  public void setMaximumRange(final Double maximumRange) {
    this.maximumRange = maximumRange;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Boolean getResearchArea() {
    return researchArea;
  }

  public void setResearchArea(final Boolean researchArea) {
    this.researchArea = researchArea;
  }

  /**
   * Legacy type to convert IMAER calculation type to internal {@link CalculationType} enum.
   */
  public enum CalculationTypeLegacy {

    /**
     * Calculate deposition at custom points (fixed set of receptors or user defined points).
     */
    CUSTOM_POINTS(CalculationType.CUSTOM_POINTS),
    /**
     * Calculate deposition in nature areas.
     */
    NATURE_AREA(CalculationType.NATURE_AREA),
    /**
     * Calculate for the Dutch NB-wet (Nature Compliance Act) compliance.
     */
    PERMIT(CalculationType.WNB),
    /**
     * Calculate deposition in a radius around the sources.
     */
    RADIUS(CalculationType.RADIUS);

    private final CalculationType calculationType;

    private CalculationTypeLegacy(final CalculationType calculationType) {
      this.calculationType = calculationType;
    }

    public CalculationType from() {
      return calculationType;
    }

    public static CalculationTypeLegacy to(final CalculationType calculationType) {
      return Stream.of(values()).filter(e -> e.calculationType == calculationType).findFirst().orElse(null);
    }
  }
}
