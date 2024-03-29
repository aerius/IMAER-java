/*
 * Crown copyright
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

/**
 * Assessment category for calculation points.
 */
public enum AssessmentCategory {

  ECOLOGY(1.0),
  HUMAN_HEALTH(1.5),
  MONITORING(1.5);

  private final double defaultHeight;

  private AssessmentCategory(final double defaultHeight) {
    this.defaultHeight = defaultHeight;
  }

  public double getDefaultHeight() {
    return defaultHeight;
  }

  public static AssessmentCategory safeValueOf(final String value) {
    AssessmentCategory correct = null;
    if (value != null) {
      for (final AssessmentCategory type : values()) {
        if (type.name().equalsIgnoreCase(value)) {
          correct = type;
        }
      }
    }
    return correct;
  }

}
