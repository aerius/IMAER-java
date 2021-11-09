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
package nl.overheid.aerius.gml.base;

import java.util.Map;

import nl.overheid.aerius.gml.base.conversion.MobileSourceOffRoadConversion;
import nl.overheid.aerius.gml.base.conversion.PlanConversion;

/**
 * Util class to convert old codes in previous versions of AERIUS gml files to the codes of the latest version.
 */
public class GMLLegacyCodeConverter {

  /**
   * The type of a legacy code.
   */
  public enum GMLLegacyCodeType {

    ON_ROAD_MOBILE_SOURCE,
    OFF_ROAD_MOBILE_SOURCE,
    SECTOR;

  }

  private final Map<GMLLegacyCodeType, Map<String, Conversion>> codeMaps;
  private final Map<String, MobileSourceOffRoadConversion> mobileSourceOffRoadConversions;
  private final Map<String, PlanConversion> planConversions;

  /**
   * @param codeMaps The &lt;OldCode, NewCode&gt; maps to use for each legacy code type.
   * @param mobileSourceOffRoadConversions The &lt;OldCode, ConversionValues&gt; to use for mobile sources.
   * @param planConversions The &lt;OldCode, ConversionValues&gt; to use for plan activities.
   */
  public GMLLegacyCodeConverter(final Map<GMLLegacyCodeType, Map<String, Conversion>> codeMaps,
      final Map<String, MobileSourceOffRoadConversion> mobileSourceOffRoadConversions,
      final Map<String, PlanConversion> planConversions) {
    this.codeMaps = codeMaps == null ? Map.of() : codeMaps;
    this.mobileSourceOffRoadConversions = mobileSourceOffRoadConversions == null ? Map.of() : mobileSourceOffRoadConversions;
    this.planConversions = planConversions == null ? Map.of() : planConversions;
  }

  /**
   * Convert an old code to the right code.
   * In case the old code wasn't changed or can't be found, the same code is returned.
   * @param codeType The code type to find a code for.
   * @param oldCode The old code.
   * @return The proper code for the old code (or the supplied code if not found)
   */
  protected Conversion getConversion(final GMLLegacyCodeType codeType, final String oldCode) {
    return codeMaps.get(codeType) != null ? codeMaps.get(codeType).get(oldCode) : null;
  }

  protected MobileSourceOffRoadConversion getMobileSourceOffRoadConversion(final String oldCode) {
    return mobileSourceOffRoadConversions.get(oldCode);
  }

  protected PlanConversion getPlanConversion(final String oldCode) {
    return planConversions.get(oldCode);
  }

  /**
   * The conversion of a an old code.
   */
  public static class Conversion {

    private final String newValue;
    private final boolean issueWarning;

    /**
     * @param newValue The new value to use in the conversion.
     * @param issueWarning Boolean to indicate if the user should receive a warning about the conversion or not.
     */
    public Conversion(final String newValue, final boolean issueWarning) {
      this.newValue = newValue;
      this.issueWarning = issueWarning;
    }

    public String getNewValue() {
      return newValue;
    }

    public boolean isIssueWarning() {
      return issueWarning;
    }

  }
}
