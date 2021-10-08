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
package nl.overheid.aerius.schematron;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;

import nl.overheid.aerius.shared.domain.calculation.Profile;

/**
 * Enum with complete set of validations per profile.
 */
public enum ImaerSchematronProfileValidation {

  NSL(Profile.NSL,
      ImaerSchematronValidation.METADATA, ImaerSchematronValidation.SRM1ROAD, ImaerSchematronValidation.SRM2ROAD,
      ImaerSchematronValidation.NSLCALCULATIONPOINT, ImaerSchematronValidation.SRM1DISPERSIONLINE,
      ImaerSchematronValidation.SRM1MEASUREAREA, ImaerSchematronValidation.CALCULATIONPOINTCORRECTION);

  private final Profile profile;
  private String schema;

  private ImaerSchematronProfileValidation(final Profile profile, final ImaerSchematronValidation... validations) {
    this.profile = profile;

    try {
      schema = ImaerSchematronResourceBuilder.buildSchematron(Arrays.asList(validations));
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static final ImaerSchematronProfileValidation getValidations(final Profile profile) {
    for (final ImaerSchematronProfileValidation pv : values()) {
      if (pv.profile == profile) {
        return pv;
      }
    }
    return null;
  }

  public String getSchema() {
    return schema;
  }
}
