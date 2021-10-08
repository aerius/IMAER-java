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

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLCharacteristicsSupplier;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLVersionReader;
import nl.overheid.aerius.gml.base.GMLVersionReaderFactory;
import nl.overheid.aerius.gml.base.source.ship.v31.GMLInlandShippingSupplier;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Class to read data from a list of feature members that was created from multiple IMAER features.
 */
public final class GMLSourceReader {

  private final List<FeatureMember> featureMembers;
  private final GMLVersionReader versionReader;

  /**
   * Constructor.
   *
   * @param inlandShippingSupplier The inland shipping supplier
   * @param rgs the receptor grid settings
   * @param characteristicsSupplier
   * @param factory specific version factory
   * @param featureMembers the feature members with parsed IMAER GML data
   * @param errors list to add errors on
   * @param warnings list to add warnings on
   */
  GMLSourceReader(final GMLInlandShippingSupplier inlandShippingSupplier, final ReceptorGridSettings rgs,
      final GMLCharacteristicsSupplier characteristicsSupplier, final GMLVersionReaderFactory factory, final List<FeatureMember> featureMembers,
      final List<AeriusException> errors, final List<AeriusException> warnings) {
    this.featureMembers = featureMembers;
    final GMLConversionData conversionData = new GMLConversionData(inlandShippingSupplier, factory.getLegacyCodeConverter(), characteristicsSupplier,
        rgs,
        errors, warnings);
    versionReader = factory.createReader(conversionData);
  }

  public List<EmissionSourceFeature> readEmissionSources() throws AeriusException {
    return versionReader.sourcesFromGML(featureMembers);
  }

}
