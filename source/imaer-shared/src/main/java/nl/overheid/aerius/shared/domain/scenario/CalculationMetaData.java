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
package nl.overheid.aerius.shared.domain.scenario;

import java.io.Serializable;
import java.util.List;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 * Meta data of options for a calculation.
 */
public class CalculationMetaData implements Serializable {
  private static final long serialVersionUID = 1L;

  private CalculationType calculationType;
  private List<Substance> substances;
  private List<EmissionResultType> resultTypes;

  /**
   * Reference year to use for srm2 calculations.
   */
  private int monitorSrm2Year;

  private CalculationMetaData(final CalculationType calculationType, final List<Substance> substances, final List<EmissionResultType> resultTypes,
      final int monitorSrm2Year) {
    this.calculationType = calculationType;
    this.substances = substances;
    this.resultTypes = resultTypes;
    this.monitorSrm2Year = monitorSrm2Year;
  }

  public static Builder builder() {
    return new Builder();
  }

  public CalculationType calculationType() {
    return calculationType;
  }

  public List<Substance> substances() {
    return substances;
  }

  public List<EmissionResultType> resultTypes() {
    return resultTypes;
  }

  public int monitorSrm2Year() {
    return monitorSrm2Year;
  }

  @Override
  public String toString() {
    return "CalculationMetaData [calculationType=" + calculationType + ", substances=" + substances + ", resultTypes=" + resultTypes
        + ", monitorSrm2Year=" + monitorSrm2Year + "]";
  }

  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CalculationMetaData that = (CalculationMetaData) o;
    return (this.calculationType == null ? that.calculationType() == null : this.calculationType.equals(that.calculationType()))
        && (this.substances == null ? that.substances() == null : this.substances.equals(that.substances()))
        && (this.resultTypes == null ? that.resultTypes() == null : this.resultTypes.equals(that.resultTypes()))
        && (this.monitorSrm2Year == that.monitorSrm2Year());
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (calculationType == null) ? 0 : calculationType.hashCode();
    h$ *= 1000003;
    h$ ^= (substances == null) ? 0 : substances.hashCode();
    h$ *= 1000003;
    h$ ^= (resultTypes == null) ? 0 : resultTypes.hashCode();
    h$ *= 1000003;
    h$ ^= monitorSrm2Year;
    return h$;
  }

  static final class Builder {
    private CalculationType calculationType;
    private List<Substance> substances;
    private List<EmissionResultType> resultTypes;
    private int monitorSrm2Year;

    public CalculationMetaData.Builder calculationType(final CalculationType calculationType) {
      this.calculationType = calculationType;
      return this;
    }

    public CalculationMetaData.Builder setSubstances(final List<Substance> substances) {
      this.substances = substances;
      return this;
    }

    public CalculationMetaData.Builder setResultTypes(final List<EmissionResultType> resultTypes) {
      this.resultTypes = resultTypes;
      return this;
    }

    public CalculationMetaData.Builder setMonitorSrm2Year(final int monitorSrm2Year) {
      this.monitorSrm2Year = monitorSrm2Year;
      return this;
    }

    public CalculationMetaData build() {
      return new CalculationMetaData(calculationType, substances, resultTypes, monitorSrm2Year);
    }
  }
}
