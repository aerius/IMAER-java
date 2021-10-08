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

import nl.overheid.aerius.shared.exception.AeriusException;

public class SchematronValidators {

  private final ImaerSchematronValidator srm1RoadValidator;
  private final ImaerSchematronValidator srm2RoadValidator;
  private final ImaerSchematronValidator nslCalculationPointValidator;
  private final ImaerSchematronValidator dispersionLineValidator;
  private final ImaerSchematronValidator measureAreaValidator;
  private final ImaerSchematronValidator calculationPointCorrectionValidator;

  public SchematronValidators() throws IOException, AeriusException {
    srm1RoadValidator = new ImaerSchematronValidator(ImaerSchematronValidation.SRM1ROAD);
    srm2RoadValidator = new ImaerSchematronValidator(ImaerSchematronValidation.SRM2ROAD);
    nslCalculationPointValidator = new ImaerSchematronValidator(ImaerSchematronValidation.NSLCALCULATIONPOINT);
    dispersionLineValidator = new ImaerSchematronValidator(ImaerSchematronValidation.SRM1DISPERSIONLINE);
    measureAreaValidator = new ImaerSchematronValidator(ImaerSchematronValidation.SRM1MEASUREAREA);
    calculationPointCorrectionValidator = new ImaerSchematronValidator(ImaerSchematronValidation.CALCULATIONPOINTCORRECTION);
  }

  public ImaerSchematronValidator getSrm1RoadValidator() {
    return srm1RoadValidator;
  }

  public ImaerSchematronValidator getSrm2RoadValidator() {
    return srm2RoadValidator;
  }

  public ImaerSchematronValidator getNslCalculationPointValidator() {
    return nslCalculationPointValidator;
  }

  public ImaerSchematronValidator getDispersionLineValidator() {
    return dispersionLineValidator;
  }

  public ImaerSchematronValidator getMeasureAreaValidator() {
    return measureAreaValidator;
  }

  public ImaerSchematronValidator getCalculationPointCorrectionValidator() {
    return calculationPointCorrectionValidator;
  }

}
