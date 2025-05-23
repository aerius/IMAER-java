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
package nl.overheid.aerius.gml.v5_1.metadata;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsCalculationMetaData;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.calculation.CalculationJobType;
import nl.overheid.aerius.shared.domain.calculation.CalculationMethod;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 *
 */
@XmlType(name = "CalculationMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"calculationType", "calculationMethod",
    "calculationJobType", "substances", "resultTypes", "maximumRange", "researchArea", "monitorSrm2Year", "options", "otherSituations"})
public class CalculationMetadata implements IsCalculationMetaData {

  private String calculationType;
  private String calculationMethod;
  private String calculationJobType;
  private List<Substance> substances;
  private List<EmissionResultType> resultTypes;
  private Double maximumRange;
  private Boolean researchArea;
  private Integer monitorSrm2Year;
  private List<CalculationOptionProperty> options;
  private List<OtherSituationMetadataProperty> otherSituations = new ArrayList<>();

  @Override
  @XmlElement(name = "type", namespace = CalculatorSchema.NAMESPACE)
  public String getCalculationType() {
    return calculationType;
  }

  public void setCalculationType(final String calculationType) {
    this.calculationType = calculationType;
  }

  @Override
  @XmlElement(name = "method", namespace = CalculatorSchema.NAMESPACE)
  public String getCalculationMethod() {
    return calculationMethod;
  }

  public void setCalculationMethod(final CalculationMethod calculationMethod) {
    this.calculationMethod = calculationMethod.type();
  }

  public void setCalculationMethod(final String calculationMethod) {
    this.calculationMethod = calculationMethod;
  }

  @Override
  @XmlElement(name = "jobType", namespace = CalculatorSchema.NAMESPACE)
  public String getCalculationJobType() {
    return calculationJobType;
  }

  public void setCalculationJobType(final String calculationJobType) {
    this.calculationJobType = calculationJobType;
  }

  public void setCalculationJobType(final CalculationJobType calculationJobType) {
    this.calculationJobType = calculationJobType == null ? null : calculationJobType.name();
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

  @Override
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

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getMonitorSrm2Year() {
    return monitorSrm2Year;
  }

  public void setMonitorSrm2Year(final Integer monitorSrm2Year) {
    this.monitorSrm2Year = monitorSrm2Year;
  }

  @Override
  @XmlElement(name = "option", namespace = CalculatorSchema.NAMESPACE)
  public List<CalculationOptionProperty> getOptions() {
    return options;
  }

  public void setOptions(final List<CalculationOptionProperty> options) {
    this.options = options;
  }

  @XmlElement(name = "otherSituation", namespace = CalculatorSchema.NAMESPACE)
  public List<OtherSituationMetadataProperty> getOtherSituations() {
    return otherSituations;
  }

  public void setOtherSituations(final List<OtherSituationMetadataProperty> otherSituations) {
    this.otherSituations = otherSituations;
  }

}
