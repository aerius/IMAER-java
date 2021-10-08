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
package nl.overheid.aerius.shared.domain.v2.nsl;

import java.io.Serializable;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

public class NSLCorrection implements Serializable {

  private static final long serialVersionUID = 3L;

  private String label;
  private Integer jurisdictionId;
  private String calculationPointGmlId;
  private Substance substance;
  private EmissionResultType resultType;
  private double value;
  private String description;

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public Integer getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(final Integer jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  public String getCalculationPointGmlId() {
    return calculationPointGmlId;
  }

  public void setCalculationPointGmlId(final String calculationPointGmlId) {
    this.calculationPointGmlId = calculationPointGmlId;
  }

  public Substance getSubstance() {
    return substance;
  }

  public void setSubstance(final Substance substance) {
    this.substance = substance;
  }

  public EmissionResultType getResultType() {
    return resultType;
  }

  public void setResultType(final EmissionResultType resultType) {
    this.resultType = resultType;
  }

  public double getValue() {
    return value;
  }

  public void setValue(final double value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public boolean shouldApplyTo(final EmissionResultKey resultKey) {
    return resultKey.getEmissionResultType() == resultType && resultKey.getSubstance() == substance;
  }

}
