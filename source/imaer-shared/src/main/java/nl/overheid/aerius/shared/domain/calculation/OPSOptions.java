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
package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;

/**
 * Contains OPS specific settings that will override any preset value in the control file of OPS.
 */
public class OPSOptions implements Serializable {

  private boolean rawInput = false;
  private Integer year;
  private Integer compCode;
  private Double molWeight;
  private Integer phase;
  private Integer loss;
  private String diffCoeff;
  private String washout;
  private String convRate;
  private Double roughness;
  private Chemistry chemistry;

  public boolean isRawInput() {
    return rawInput;
  }

  public void setRawInput(final boolean rawInput) {
    this.rawInput = rawInput;
  }

  public Integer getCompCode() {
    return compCode;
  }

  public void setCompCode(Integer compCode) {
    this.compCode = compCode;
  }

  /**
   * OPS .ctr variable: YEAR
   */
  public Integer getYear() {
    return year;
  }

  public void setYear(final Integer year) {
    this.year = year;
  }

  /**
   * This is a suffix for
   * OPS .ctr variable:YEAR
   */
  public Chemistry getChemistry() {
    return chemistry;
  }

  public void setChemistry(Chemistry chemistry) {
    this.chemistry = chemistry;
  }

  /**
   * OPS .ctr variable: MOLWEIGHT
   */
  public Double getMolWeight() {
    return molWeight;
  }

  public void setMolWeight(final Double molWeight) {
    this.molWeight = molWeight;
  }

  /**
   * OPS .ctr variable: PHASE
   */
  public Integer getPhase() {
    return phase;
  }

  public void setPhase(final Integer phase) {
    this.phase = phase;
  }

  /**
   * OPS .ctr variable: LOSS
   */
  public Integer getLoss() {
    return loss;
  }

  public void setLoss(final Integer loss) {
    this.loss = loss;
  }

  /**
   * OPS .ctr variable: DIFFCOEFF
   */
  public String getDiffCoeff() {
    return diffCoeff;
  }

  public void setDiffCoeff(final String diffCoeff) {
    this.diffCoeff = diffCoeff;
  }

  /**
   * OPS .ctr variable: WASHOUT
   */
  public String getWashout() {
    return washout;
  }

  public void setWashout(final String washout) {
    this.washout = washout;
  }

  /**
   * OPS .ctr variable: CONVRATE
   */
  public String getConvRate() {
    return convRate;
  }

  public void setConvRate(final String convRate) {
    this.convRate = convRate;
  }

  /**
   * OPS .ctr variable: ROUGHNESS
   */
  public Double getRoughness() {
    return roughness;
  }

  public void setRoughness(final Double roughness) {
    this.roughness = roughness;
  }

  @Override
  public String toString() {
    return "OPSOptions{" +
        "rawInput=" + rawInput +
        ", year=" + year +
        ", compCode=" + compCode +
        ", molWeight=" + molWeight +
        ", phase=" + phase +
        ", loss=" + loss +
        ", diffCoeff='" + diffCoeff + '\'' +
        ", washout='" + washout + '\'' +
        ", convRate='" + convRate + '\'' +
        ", roughness=" + roughness + '\'' +
        ", chemistry=" + chemistry +
        '}';
  }

  /**
   * Represents the type of background chemistry maps to be used by OPS.
   */
  public enum Chemistry {

    /**
     * Prognostic chemistry background map
     */
    PROGNOSIS("prognosis"),

    /**
     * Actual chemistry background map
     */
    ACTUAL("");

    private final String controlFileLabel;

    Chemistry(final String controlFileLabel) {
      this.controlFileLabel = controlFileLabel;
    }

    public String getControlFileLabel() {
      return controlFileLabel;
    }
  }
}
