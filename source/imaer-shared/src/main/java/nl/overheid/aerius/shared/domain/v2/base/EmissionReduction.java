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
package nl.overheid.aerius.shared.domain.v2.base;

import java.io.Serializable;
import java.util.OptionalDouble;

import nl.overheid.aerius.shared.domain.Substance;

/**
 *
 */
public class EmissionReduction implements Serializable {

  private static final long serialVersionUID = 3L;

  private Substance substance;
  private double factor;

  public OptionalDouble determineFactor(final Substance substance) {
    return appliesTo(substance) ? OptionalDouble.of(factor) : OptionalDouble.empty();
  }

  public boolean appliesTo(final Substance substance) {
    return substance == this.substance;
  }

  public Substance getSubstance() {
    return substance;
  }

  public void setSubstance(final Substance substance) {
    this.substance = substance;
  }

  public double getFactor() {
    return factor;
  }

  public void setFactor(final double factor) {
    this.factor = factor;
  }

}
