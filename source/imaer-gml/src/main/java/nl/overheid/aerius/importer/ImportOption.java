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
package nl.overheid.aerius.importer;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enum of options for importing files.
 */
public enum ImportOption {
  /**
   * If set, calculation points will be imported from GML files. If not set, calculation points will be ignored.
   */
  INCLUDE_CALCULATION_POINTS,
  /**
   * If set, results for points will be imported from GML files. If not set, results will be ignored.
   */
  INCLUDE_RESULTS,
  /**
   * If set, sources will be imported from GML files. If not set, sources will be ignored.
   */
  INCLUDE_SOURCES,
  /**
   * If set, nsl measures will be imported from GML files. If not set, nsl measures will be ignored.
   */
  INCLUDE_NSL_MEASURES,
  /**
   * If set, nsl dispersion lines will be imported from GML files. If not set, nsl dispersion lines will be ignored.
   */
  INCLUDE_NSL_DISPERSION_LINES,
  /**
   * If set, nsl corrections will be imported from GML files. If not set, nsl corrections will be ignored.
   */
  INCLUDE_NSL_CORRECTIONS,
  /**
  * If set, input will be persisted in the database.
  */
  PERSIST_INPUT,
  /**
   * If set, the importer will use the landuse columns supplied in the .RCP file when constructing AeriusPoints. If not set, the importer
   * will not use the landuse, and points will be only x,y,label.
   * <p>This is specific for RCP files. <b>When this value is set it also will use the name column as id if it contains an integer</b>.
   */
  USE_IMPORTED_LANDUSES,
  /**
   * If set, receptor height will be imported from (rcp) files. If not set, height will be not imported (and will not be expected).
   */
  USE_IMPORTED_RECEPTOR_HEIGHT,
  /**
   * If set, the importer will use valid AERIUS sectors when importing .BRN files. If not found, the SECTOR_DEFAULT is used. If not set,
   * the importer will use a 'fake' sector with the ID supplied in the .BRN file.
   */
  USE_VALID_SECTORS,
  /**
   * If set, the GML file(s) will be validated against the XSD schema before importing.
   */
  VALIDATE_AGAINST_SCHEMA,
  /**
   * If set, the import will fail if a ZIP contains more than 2 input files.
   */
  VALIDATE_MAX_USABLE_FILES,
  /**
   * If set, metadata in GML files will be checked for required fields (for Register). Any missing field will result in an AeriusException
   * added to the errors list.
   */
  VALIDATE_METADATA,
  /**
   * If set, validates the cohesion of input files related to RBL required data restrictions.
   */
  VALIDATE_RBL_COHESION,
  /**
   * If set, source will be validated. Any missing field will result in an AeriusException added to the errors list.
   */
  VALIDATE_SOURCES,
  /**
   * If set, the input will be validated with Schematron.
   */
  VALIDATE_WITH_SCHEMATRON,
  /**
   * If set, the GML file(s) warnings will be forced as exceptions. If not set, this check will do nothing and returns warnings and
   * exceptions. Default is false.
   */
  VALIDATE_STRICT,
  /**
   * If set, a validation warning is thrown to alert that calculation points are found but will be ignored.
   */
  WARNING_ON_CALCULATION_POINTS;

  private static final EnumSet<ImportOption> DEFAULT_IMPORT_OPTIONS = EnumSet.of(
      INCLUDE_CALCULATION_POINTS, INCLUDE_SOURCES,
      INCLUDE_NSL_MEASURES, INCLUDE_NSL_DISPERSION_LINES, INCLUDE_NSL_CORRECTIONS,
      USE_VALID_SECTORS, VALIDATE_AGAINST_SCHEMA, VALIDATE_SOURCES);

  /**
   * @return Returns the default options.
   */
  public static EnumSet<ImportOption> getDefaultOptions() {
    return EnumSet.copyOf(DEFAULT_IMPORT_OPTIONS);
  }

  /**
   * Returns true if this option is present in the given set.
   *
   * @param options options to check
   * @return true if this options is in the set
   */
  public boolean in(final Set<ImportOption> options) {
    return options.contains(this);
  }
}
