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
package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculation options related to the NCA theme.
 */
public class NCACalculationOptions implements Serializable {

  private static final long serialVersionUID = 2L;

  private String projectCategory;

  /**
   * Regional area for which a permit calculation would apply to.
   */
  private String permitArea;

  /**
   * ADMS version to use.
   */
  private String admsVersion;

  /**
   * Additional ADMS options.
   */
  private ADMSOptions admsOptions = new ADMSOptions();

  // Additional Fraction NO2 (fNO2) options

  /**
   * Option on what fNO2 to use when calculating receptors.
   */
  private RoadLocalFractionNO2Option roadLocalFractionNO2ReceptorsOption = RoadLocalFractionNO2Option.LOCATION_BASED;
  /**
   * Option on what fNO2 to use when calculating custom calculation points.
   */
  private RoadLocalFractionNO2Option roadLocalFractionNO2PointsOption = RoadLocalFractionNO2Option.LOCATION_BASED;
  /**
   * Value for fNO2 when either receptors or points is configured as {@link RoadLocalFractionNO2Option#ONE_CUSTOM_VALUE}.
   */
  private Double roadLocalFractionNO2;

  private List<String> developmentPressureSourceIds = new ArrayList<>();

  public String getProjectCategory() {
    return projectCategory;
  }

  public void setProjectCategory(final String projectCategory) {
    this.projectCategory = projectCategory;
  }

  public String getPermitArea() {
    return permitArea;
  }

  public void setPermitArea(final String permitArea) {
    this.permitArea = permitArea;
  }

  public ADMSOptions getAdmsOptions() {
    return admsOptions;
  }

  public void setAdmsOptions(final ADMSOptions admsOptions) {
    this.admsOptions = admsOptions;
  }

  public String getAdmsVersion() {
    return admsVersion;
  }

  public void setAdmsVersion(final String admsVersion) {
    this.admsVersion = admsVersion;
  }

  public RoadLocalFractionNO2Option getRoadLocalFractionNO2ReceptorsOption() {
    return roadLocalFractionNO2ReceptorsOption;
  }

  public void setRoadLocalFractionNO2ReceptorsOption(final RoadLocalFractionNO2Option roadLocalFractionNO2ReceptorsOption) {
    this.roadLocalFractionNO2ReceptorsOption = roadLocalFractionNO2ReceptorsOption;
  }

  public RoadLocalFractionNO2Option getRoadLocalFractionNO2PointsOption() {
    return roadLocalFractionNO2PointsOption;
  }

  public void setRoadLocalFractionNO2PointsOption(final RoadLocalFractionNO2Option roadLocalFractionNO2PointsOption) {
    this.roadLocalFractionNO2PointsOption = roadLocalFractionNO2PointsOption;
  }

  public Double getRoadLocalFractionNO2() {
    return roadLocalFractionNO2;
  }

  public void setRoadLocalFractionNO2(final Double roadLocalFractionNO2) {
    this.roadLocalFractionNO2 = roadLocalFractionNO2;
  }

  public List<String> getDevelopmentPressureSourceIds() {
    return developmentPressureSourceIds;
  }

  public void setDevelopmentPressureSourceIds(final List<String> developmentPressureSourceIds) {
    this.developmentPressureSourceIds = developmentPressureSourceIds;
  }

}
