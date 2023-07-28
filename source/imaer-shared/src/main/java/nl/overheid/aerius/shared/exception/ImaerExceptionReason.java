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
package nl.overheid.aerius.shared.exception;

import nl.overheid.aerius.shared.exception.AeriusException.Reason;

/**
 * Enum with a list of errors that can be returned by the server via a {@link AeriusException}.
 * Each reason should state in the JavaDoc the arguments to be passed.
 * It is not enforced at compile time if this matches, therefore be careful and test it.
 */
public enum ImaerExceptionReason implements Reason {

  // Internal errors codes < 1000, these will be interpreted as such. USE WISELY!

  /**
   * Unspecified internal server error.
   */
  INTERNAL_ERROR(666),

  /**
   * A value in the emission source had an invalid value.
   *
   * @param 0 name of the emission source
   * @param 1 failure message
   * @param 2 value that failed
   */
  SOURCE_VALIDATION_FAILED(1002),
  /**
   * Number of sources exceeds maximum.
   *
   * @param 0 maximum number of allowed sources
   * @param 1 name of the source
   */
  LIMIT_SOURCES_EXCEEDED(1003),
  /**
   * Length of a line exceeds maximum length.
   *
   * @param 0 name of the source
   * @param 1 allowed maximum line length
   * @param 2 length of source
   */
  LIMIT_LINE_LENGTH_EXCEEDED(1004),
  /**
   * Surface of a polygon exceeds maximum surface.
   *
   * @param 0 name of the source
   * @param 1 allowed maximum surface
   * @param 2 surface of source
   */
  LIMIT_POLYGON_SURFACE_EXCEEDED(1005),
  /**
   * @param 0 ship type that is not allowed.
   * @param 1 name of the waterway type. For some waterways there is a restriction on which ship types are allowed.
   */
  INLAND_SHIPPING_SHIP_TYPE_NOT_ALLOWED(1006),
  /**
   * Shipping routes are only allowed to be LineStrings.
   */
  SHIPPING_ROUTE_GEOMETRY_NOT_ALLOWED(1007),
  /**
   * Length of a line length 0.
   *
   * @param 0 name of the source
   */
  LIMIT_LINE_LENGTH_ZERO(1012),
  /**
   * Surface of a polygon surface 0.
   *
   * @param 0 name of the source
   */
  LIMIT_POLYGON_SURFACE_ZERO(1013),
  /**
   * Upon determining the water way type of a route, multiple types were found.
   *
   * @param 0 id of the emission source
   * @param 1 name of chosen water way
   * @param 2 list of other water ways found
   */
  INLAND_SHIPPING_WATERWAY_INCONCLUSIVE(1014),
  /**
   * No watery direction or incorrect is given for the waterway type.
   *
   * @param 0 water way
   * @param 1 invalid direction
   */
  INLAND_SHIPPING_WATERWAY_NO_DIRECTION(1015),
  /**
   * Shipping objects can only be calculated with OPS, so when a sector that is calculated with another model (SRM2) this is not valid.
   * This should only happen if a user assigns a road sector id to a shipping emission object. If not throwing the error ops sources
   * would be passed to the SRM2 worker that would then shutdown.
   *
   * @param 0 label of shipping source
   * @param 1 sector name
   * @param 2 sector id
   */
  SHIPPING_INVALID_SECTOR(1016),
  /**
   * Supplied hours exceeds the total hours in a year.
   * @param 0 name of the source
   */
  HOURS_EXCEEDING_HOURS_IN_YEAR(1020),

  /**
   * The supplied custom diurnal variation type is unknown.
   * @param 0 the unrecognized type
   */
  CUSTOM_DIURNAL_VARIATION_TYPE_UNKNOWN(1023),

  /**
   * The supplied custom diurnal variation is invalid due to number of values.
   * @param 0 the expected number of values
   * @param 1 the supplied number of values
   */
  CUSTOM_DIURNAL_VARIATION_INVALID_COUNT(1024),

  /**
   * The supplied custom diurnal variation is invalid due to number of values.
   * @param 0 the expected sum
   * @param 1 the supplied sum
   */
  CUSTOM_DIURNAL_VARIATION_INVALID_SUM(1025),

  /**
   * Missing liter fuel property where they are expected.
   * @param 0 name of the source
   */
  MOBILE_SOURCE_MISSING_LITER_FUEL(1026),

  /**
   * Missing operating hours property where they are expected.
   * @param 0 name of the source
   */
  MOBILE_SOURCE_MISSING_OPERATING_HOURS(1027),

  /**
   * Missing AdBlue property where they are expected.
   * @param 0 name of the source
   */
  MOBILE_SOURCE_MISSING_LITER_ADBLUE(1028),

  /**
   * Higher AdBlue value than expected.
   * @param 0 name of the source
   * @param 1 the maximum AdBlue amount based on supplied fuel amount
   * @param 2 the supplied AdBlue amount
   */
  MOBILE_SOURCE_HIGH_ADBLUE_FUEL_RATIO(1029),

  /**
   * Number of animals was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_NUMBER_OF_ANIMALS(1031),

  /**
   * Number of days was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_NUMBER_OF_DAYS(1032),

  /**
   * Number of applications was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_NUMBER_OF_APPLICATIONS(1033),

  /**
   * Meters cubed was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_METERS_CUBED(1034),

  /**
   * Tonnes was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_TONNES(1035),

  /**
   * Meters squared was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_METERS_SQUARED(1036),

  /**
   * Heat content was missing on an object.
   *
   * @param 0 the id of the object containing the error.
   */
  MISSING_HEAT_CONTENT(1037),

  /**
   * Heat content was out of range on an object.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 the value causing the problem
   * @param 2 the minimum value allowed
   * @param 3 the maximum value allowed
   */
  HEAT_CONTENT_OUT_OF_RANGE(1038),

  /**
   * Heat capacity was out of range on an object.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 the value causing the problem
   * @param 2 the minimum value allowed
   * @param 3 the maximum value allowed
   */
  HEAT_CAPACITY_OUT_OF_RANGE(1039),

  // Import GML file errors/warnings.
  /**
   * Uploaded file should contain no calculation points.
   */
  IMPORT_CALCULATION_POINTS_PRESENT(5013),

  /**
   * GML validation failed.
   *
   * @param 0 raw errors from validator.
   */
  GML_VALIDATION_FAILED(5201),
  /**
   * Geometry in gml is invalid.
   *
   * @param 0 the id of the object containing an invalid geometry.
   */
  GML_GEOMETRY_INVALID(5202),
  /**
   * Encoding in GML is not supported.
   */
  GML_ENCODING_INCORRECT(5203),
  /**
   * Geometry in gml intersects with self.
   *
   * @param 0 name of the source containing the intersecting geometry
   * @param 1 type of the geometry, line or polygon
   */
  GML_GEOMETRY_INTERSECTS(5204),
  /**
   * Geometry in gml is not permitted for that particular source type.
   *
   * @param 0 the id of the object containing the error.
   */
  GML_GEOMETRY_NOT_PERMITTED(5205),
  /**
   * Geometry in gml is not supported.
   *
   * @param 0 the id of the object containing the error.
   */
  GML_GEOMETRY_UNKNOWN(5206),
  /**
   * GML contains a unknown RAV code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_RAV_CODE(5207),
  /**
   * GML contains a unknown mobile source code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_MOBILE_SOURCE_CODE(5208),
  /**
   * GML contains a unknown ship code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_SHIP_CODE(5209),
  /**
   * GML contains a unknown plan code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_PLAN_CODE(5210),
  /**
   * GML parsing failed, but AERIUS could not parse the error message.
   *
   * @param 0 user-unfriendly internal error message
   */
  GML_GENERIC_PARSE_ERROR(5211),
  /**
   * GML file contains a parse error.
   *
   * @param 0 row in error
   * @param 1 column in error
   * @param 2 value in error
   * @param 3 expected value
   */
  GML_PARSE_ERROR(5212),
  /**
   * IMAER version in GML not recognized (or not supported anymore).
   *
   * @param 0 namespace uri used to determine version
   */
  GML_VERSION_NOT_SUPPORTED(5213),
  /**
   * Building the GML file failed.
   *
   * @param 0 raw error message from jaxb parser.
   */
  GML_CREATION_FAILED(5214),
  /**
   * Geometry is invalid.
   *
   * @param 0 the invalid geometry.
   */
  GEOMETRY_INVALID(5215),
  /**
   * GML contains an unknown PAS-measure code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_PAS_MEASURE_CODE(5216),
  /**
   * GML contains a PAS-measure that can not be applied to the lodging system.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is invalid.
   * @param 2 The code of the farm lodging system
   */
  GML_INVALID_PAS_MEASURE_CATEGORY(5217),
  /**
   * While importing the old category could not correctly matched with a new category.
   *
   * @param 0 the offending category code.
   * @param 1 the category code used.
   * @param 2 the label of the source.
   */
  GML_INVALID_CATEGORY_MATCH(5218),
  /**
   * While importing a road source the old category could not correctly matched with a new category.
   *
   * @param 0 the offending category code.
   * @param 1 the category code used.
   * @param 2 the label of the source.
   */
  GML_INVALID_ROAD_CATEGORY_MATCH(5219),

  /**
   * The source id string contains a number that already is used on another id, while the string id itself might be unique.
   *
   * @param 0 violating id.
   * @param 1 id that is violated.
   */
  GML_ID_NOT_UNIQUE(5220),
  /**
   * GML contains an unknown combination of road characteristics that don't match a known vehicle category.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 road area category
   * @param 2 road type category
   * @param 3 road speed
   * @param 4 strict enforcement
   * @param 5 road vehicle category
   */
  GML_UNKNOWN_ROAD_CATEGORY(5221),
  /**
   * GML meta data field may not be empty.
   *
   * @param 0 the meta data field found to be empty
   */
  GML_METADATA_EMPTY(5222),
  /**
   * Warning GML version is not the latest version.
   */
  GML_VERSION_NOT_LATEST(5223),
  /**
   * Warning GML all emission values are zero.
   */
  GML_SOURCE_NO_EMISSION(5224),
  /**
   * The srsName used in GML is unsupported.
   *
   * @param 0 the unsupported SRS name.
   */
  GML_SRS_NAME_UNSUPPORTED(5225),
  /**
   * Warning GML source has no vehicles.
   *
   * @param 0 label of the source
   */
  SRM2_SOURCE_NO_VEHICLES(5226),
  /**
   * GML contains a unknown waterway code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_WATERWAY_CODE(5227),
  /**
   * GML contains an invalid ship/waterway combination.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code of the ship.
   * @param 2 The code of the waterway.
   */
  GML_INVALID_SHIP_FOR_WATERWAY(5228),
  /**
   * Warning that the waterway is not set for an inland shipping source.
   *
   * @param 0 The label of the source containing the error.
   */
  GML_INLAND_WATERWAY_NOT_SET(5229),
  /**
   * Error that the road vehicles are negative.
   *
   * @param 0 the id of the object containing the error.
   */
  SRM2_SOURCE_NEGATIVE_VEHICLES(5230),
  /**
   * Error, road segment position was not a fraction (0-1)
   */
  GML_ROAD_SEGMENT_POSITION_NOT_FRACTION(5231),
  /**
   * GML contains a unknown farm field activity code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_FARMLAND_ACTIVITY_CODE(5232),
  /**
   * Warning that the idle properties for a mobile emission source is not set, though the used category allows for it.
   *
   * @param 0 the id of the object containing the error.
   */
  GML_MOBILE_SOURCE_IDLE_NOT_SET(5233),
  /**
   * While importing an old GML a off road mobile source was converted.
   *
   * @param 0 the old category code.
   * @param 1 the new category code used.
   * @param 2 the label of the source.
   */
  GML_OFF_ROAD_CATEGORY_CONVERTED(5234),
  /**
   * GML with netting situation contained no netting factor.
   */
  GML_MISSING_NETTING_FACTOR(5235),
  /**
   * GML contains unknown farm emission factor type code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE(5236),
  /**
   * GML contains a unknown manure storage code.
   *
   * @param 0 the id of the object containing the error.
   * @param 1 The code that is unknown.
   */
  GML_UNKNOWN_MANURE_STORAGE_CODE(5237),

  /**
   * Error that strict enforcement is used on a SRM1 source.
   *
   * @param 0 the id of the object containing the error.
   */
  SRM1_SOURCE_WITH_STRICT_ENFORCEMENT(5238),

  /**
   * GML contains a deprecated situation type.
   */
  GML_DEPRECATED_NETTING_UPDATED_TO_OFF_SITE_REDUCTION(5239),

  // Cohesion (between files) errors.

  /**
   * Duplicate source IDs found.
   *
   * @param 0 the id that was duplicate.
   */
  COHESION_DUPLICATE_SOURCE_IDS(5501),
  /**
   * Duplicate calculation point IDs found.
   *
   * @param 0 the id that was duplicate.
   */
  COHESION_DUPLICATE_POINT_IDS(5502),
  /**
   * Duplicate measure IDs found.
   *
   * @param 0 the id that was duplicate.
   */
  COHESION_DUPLICATE_MEASURE_IDS(5503),
  /**
   * Duplicate dispersion lines found.
   *
   * @param 0 the calculation point id of the duplicate dispersion line.
   * @param 1 the road segment (source) id of the duplicate dispersion line.
   */
  COHESION_DUPLICATE_DISPERSION_LINES(5504),
  /**
   * Missing a road referenced by dispersion line.
   *
   * @param 0 the calculation point id of the dispersion line.
   * @param 1 the road segment (source) id of the dispersion line.
   */
  COHESION_REFERENCE_DISPERSION_LINE_MISSING_ROAD(5511),
  /**
   * Missing a calculation point referenced by dispersion line.
   *
   * @param 0 the calculation point id of the dispersion line.
   * @param 1 the road segment (source) id of the dispersion line.
   */
  COHESION_REFERENCE_DISPERSION_LINE_MISSING_POINT(5512),
  /**
   * Missing a calculation point referenced by correction.
   *
   * @param 0 the calculation point id of the correction.
   */
  COHESION_REFERENCE_CORRECTION_MISSING_POINT(5513),
  /**
   * The line describing the dispersionline is not a perpendicular line that intersects with the road segment.
   *
   * @param 0 the calculation point id of the dispersion line.
   * @param 1 the road segment (source) id of the dispersion line.
   */
  COHESION_DISPERSION_LINE_NOT_PERPENDICULAR(5514),
  /**
   * The (SRM1) road is missing at least one dispersion line.
   *
   * @param 0 the road segment (source) id missing a dispersion line.
   */
  COHESION_ROAD_MISSING_DISPERSION_LINE(5515),

  /**
   * Missing a building referenced by source (characteristics).
   *
   * @param 0 ID of the building that was referred but not found.
   */
  COHESION_REFERENCE_MISSING_BUILDING(5521),

  /**
   * Buildingheight is 0.
   *
   * @param 0 Label of the building that has height = 0.
   */
  BUILDING_HEIGHT_ZERO(5241),

  /**
   * Buildingheight is < 0.
   *
   * @param 0 Label of the building that has height < 0.
   */
  BUILDING_HEIGHT_TOO_LOW(5242),

  /**
   * Circular building with a incorrect diameter (negative or 0).
   *
   * @param 0 Label of the circular building that has an incorrect diameter.
   */
  CIRCULAR_BUILDING_INCORRECT_DIAMETER(5243),

  /**
   * Value is <= 0.
   *
   * @param 0 Label of the object that has an unexpected negative value.
   */
  UNEXPECTED_NEGATIVE_VALUE(5251),
  /**
   * Fraction value is unexpected: it is either negative or higher than 1.
   *
   * @param 0 Label of the object that has an unexpected fraction value.
   */
  UNEXPECTED_FRACTION_VALUE(5252),

  // SRM related errors.

  /**
   * Source tunnel factor < 0, which is not supported.
   *
   * @param 0 actual tunnel factor
   * @param 1 label of the source
   */
  SRM2_SOURCE_TUNNEL_FACTOR_ZERO(6209),
  /**
   * Error when an invalid road type number is given in a NSL legacy file.
   *
   * @param 0 line number
   * @param 1 unrecognized road type number
   */
  SRM_LEGACY_INVALID_ROAD_TYPE(6211),
  /**
   * Error when an invalid road speed type is given in a NSL legacy file.
   *
   * @param 0 line number
   * @param 1 unrecognized road speed type
   */
  SRM_LEGACY_INVALID_SPEED_TYPE(6212),
  /**
   * Error when an invalid tree factor is given in a NSL legacy file.
   *
   * @param 0 line number
   * @param 1 unrecognized tree factor
   */
  SRM_LEGACY_INVALID_TREE_FACTOR(6213),
  /**
   * There is no monitoring SRM2 data for the given year.
   *
   * @param 0 year to be calculated
   */
  SRM_NO_MONITOR_SRM2_DATA_FOR_YEAR(6214),
  /**
   * Records have matching IDs, but do not match on specific fields.
   *
   * @param 0 ID of the offending records
   */
  SRM_MEASURE_RECORDS_DID_NOT_MATCH(6215);

  private final int errorCode;

  ImaerExceptionReason(final int errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @param errorCode The error code to resolve.
   * @return The reason object for the given error code, or null if the error code was unknown.
   */
  public static Reason fromErrorCode(final int errorCode) {
    for (final Reason reason : ImaerExceptionReason.values()) {
      if (reason.getErrorCode() == errorCode) {
        return reason;
      }
    }
    return null;
  }

  @Override
  public int getErrorCode() {
    return errorCode;
  }
}
