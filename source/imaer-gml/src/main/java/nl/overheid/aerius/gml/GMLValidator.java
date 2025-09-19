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
package nl.overheid.aerius.gml;

import java.util.List;
import java.util.Optional;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.StringUtils;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public final class GMLValidator {

  private GMLValidator() {
    // Util class
  }

  public static void validateGMLVersion(final AeriusGMLVersion version, final List<AeriusException> warnings) {
    if (GMLWriter.LATEST_WRITER_VERSION != version) {
      warnings.add(new AeriusException(ImaerExceptionReason.GML_VERSION_NOT_LATEST, version.toString(), GMLWriter.LATEST_WRITER_VERSION.toString()));
    }
  }

  /**
   * Validates all meta data elements. Adds any violation to the list of exceptions.
   *
   * @param metaData
   * @param exceptions
   */
  public static void validateMetaData(final ScenarioMetaData metaData, final List<AeriusException> exceptions, final boolean useValidMetaData) {
    if (!useValidMetaData) {
      return;
    }
    final Optional<ScenarioMetaData> smd = Optional.ofNullable(metaData);
    validateMetaDataElement(smd.map(m -> m.getProjectName()), "projectName", exceptions);
    validateMetaDataElement(smd.map(m -> m.getCorporation()), "corporation", exceptions);
    validateMetaDataElement(smd.map(m -> m.getDescription()), "description", exceptions);
    validateMetaDataElement(smd.map(m -> m.getStreetAddress()), "streetAddress", exceptions);
    validateMetaDataElement(smd.map(m -> m.getPostcode()), "postcode", exceptions);
    validateMetaDataElement(smd.map(m -> m.getCity()), "city", exceptions);
  }

  private static void validateMetaDataElement(final Optional<String> metaDataElement, final String elementName,
      final List<AeriusException> exceptions) {
    if (metaDataElement.isEmpty() || StringUtils.isEmpty(metaDataElement.get())) {
      exceptions.add(new AeriusException(ImaerExceptionReason.GML_METADATA_EMPTY, elementName));
    }
  }

}
