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
package nl.overheid.aerius.gml.base.conversion;

/**
 * Class to be used to convert from properties used for farm lodging approach for the older IMAER versions (< 6.0)
 * to the new farm animal housing approach.
 */
public class FarmLodgingConversion {

  private final String animalTypeCode;
  private final String animalHousingCode;
  private final String additionalSystemCode;

  /**
   * @param animalTypeCode The code of the animal type to convert to.
   * @param animalHousingCode The code of the animal housing to convert to.
   * @param additionalSystemCode The optional code of the additional system that has to be added to the source.
   */
  public FarmLodgingConversion(final String animalTypeCode, final String animalHousingCode, final String additionalSystemCode) {
    this.animalTypeCode = animalTypeCode;
    this.animalHousingCode = animalHousingCode;
    this.additionalSystemCode = additionalSystemCode;
  }

  public String getAnimalTypeCode() {
    return animalTypeCode;
  }

  public String getAnimalHousingCode() {
    return animalHousingCode;
  }

  public String getAdditionalSystemCode() {
    return additionalSystemCode;
  }

}
