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

public enum NSLTreeProfile {

  /**
   * Hier en daar bomen of in het geheel niet.
   */
  NONE_OR_FEW(1.0, 100),

  /**
   * Een of meer rijen bomen met een onderlinge afstand < 15 meter met openingen tussen de kronen.
   */
  SEPARATED(1.25, 125),

  /**
   * De kronen raken elkaar en overspannen minstens een derde gedeelte van de straatbreedte.
   */
  PACKED(1.5, 150);

  private final double factor;
  private final int fakeId;

  NSLTreeProfile(final double factor, final int fakeId) {
    this.factor = factor;
    this.fakeId = fakeId;
  }

  public double getFactor() {
    return factor;
  }

  public static NSLTreeProfile legacySafeValueOf(final double value) {
    NSLTreeProfile result = null;
    final int treeFactorInt = (int) (value * 100);
    for (final NSLTreeProfile treeProfile : values()) {
      if (treeFactorInt == treeProfile.fakeId) {
        result = treeProfile;
        break;
      }
    }
    return result;
  }

}
