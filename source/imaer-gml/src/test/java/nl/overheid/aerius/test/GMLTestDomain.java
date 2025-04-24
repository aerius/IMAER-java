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
package nl.overheid.aerius.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.overheid.aerius.geo.shared.BBox;
import nl.overheid.aerius.gml.ReferenceGenerator;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.ops.DiurnalVariation;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.geo.EPSG;

/**
 * Convenience class to avoid having to write the same test code over and over again.
 *
 */
public class GMLTestDomain {

  public static final int XCOORD_1 = 136558;
  public static final int YCOORD_1 = 455251;
  public static final int XCOORD_2 = 208413;
  public static final int YCOORD_2 = 474162;
  public static final int XCOORD_3 = XCOORD_1;
  public static final int YCOORD_3 = YCOORD_2;

  public static final int YEAR = 2020;

  public static final int BINNENVELD_ID = 65;
  public static final int DWINGELDERVELD_ID = 30;
  public static final int VELUWE_ID = 57;
  public static final int DUINEN_TEXEL_ID = 2;
  public static final int DUINEN_VLIELAND_ID = 3;
  public static final int SOLLEVELD_ID = 99;
  public static final int WOOLDSE_VEEN_ID = 64;
  public static final int OLDENZAAL_ID = 50;
  public static final int SCHOORLSE_DUINEN_ID = 86;
  public static final int NOORDHOLLANDS_DUINRESERVAAT_ID = 87;

  public static final EmissionResultKey DEFAULT_ERK_NH3 = EmissionResultKey.NH3_DEPOSITION;
  public static final EmissionResultKey DEFAULT_ERK_NOX = EmissionResultKey.NOX_DEPOSITION;

  public static final String USERROLE_REGISTER_SUPERUSER = "register_superuser";
  public static final String USERROLE_REGISTER_EDITOR = "register_editor";
  public static final String USERROLE_REGISTER_VIEWER = "register_viewer";

  public static final ReferenceGenerator TEST_REFERENCE_GENERATOR = r -> Optional.empty();

  /**
   * Sector default is the sector in case no specific sector is specified, because it's unknown. Therefore the sector industry generic can be used.
   */
  public static final int DEFAULT_SECTOR_ID = 1800;

  public static GenericEmissionSource getGenericEmissionSource() {
    return getGenericEmissionSource(new GenericEmissionSource());
  }

  public static GenericEmissionSource getGenericEmissionSource(final GenericEmissionSource source) {
    source.getEmissions().put(Substance.NH3, 657.0);
    return source;
  }

  public static <E extends EmissionSource> EmissionSourceFeature getSource(final int id, final Geometry geometry, final String label,
      final E source) {
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    feature.setId(String.valueOf(id));
    feature.setGeometry(geometry);
    source.setLabel(label);
    source.setSectorId(DEFAULT_SECTOR_ID);
    //default characteristics for this sector.
    source.setCharacteristics(getDefaultCharacteristics());
    feature.setProperties(source);
    return feature;
  }

  public static OPSSourceCharacteristics getDefaultCharacteristics() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(HeatContentType.NOT_FORCED);
    characteristics.setHeatContent(0.28);
    characteristics.setEmissionHeight(22.0);
    characteristics.setSpread(11.0);
    characteristics.setDiameter(0);
    characteristics.setDiurnalVariation(DiurnalVariation.INDUSTRIAL_ACTIVITY);
    return characteristics;
  }

  public static OPSSourceCharacteristics getNonDefaultCharacteristics() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(HeatContentType.NOT_FORCED);
    characteristics.setHeatContent(564.584);
    characteristics.setEmissionHeight(11.12);
    characteristics.setSpread(649.10);
    characteristics.setDiameter(5);
    characteristics.setDiurnalVariation(DiurnalVariation.INDUSTRIAL_ACTIVITY);
    return characteristics;
  }

  public static List<EmissionSourceFeature> getExampleSourceList() {
    final List<EmissionSourceFeature> emissionSourceList = new ArrayList<>();
    final Point point1 = new Point(XCOORD_1, YCOORD_1);
    emissionSourceList.add(getSource(1, point1, "ExampleSource1", getGenericEmissionSource()));
    final Point point2 = new Point(XCOORD_2, YCOORD_2);
    final GenericEmissionSource source2 = new GenericEmissionSource();
    source2.getEmissions().put(Substance.NH3, 267.0);
    source2.getEmissions().put(Substance.NOX, 901.0);
    emissionSourceList.add(getSource(2, point2, "ExampleSource2", source2));
    return emissionSourceList;
  }

  public static ReceptorGridSettings getExampleGridSettings() {
    final BBox bbox = new BBox(3604.0, 287959.0, 296800.0, 629300.0);
    final ArrayList<HexagonZoomLevel> zoomLevels = new ArrayList<HexagonZoomLevel>();
    for (int i = 1; i <= 5; i++) {
      zoomLevels.add(new HexagonZoomLevel(i, 10000));
    }
    final int hexHor = 1529;
    return new ReceptorGridSettings(bbox, EPSG.RDNEW, hexHor, zoomLevels);
  }

}
