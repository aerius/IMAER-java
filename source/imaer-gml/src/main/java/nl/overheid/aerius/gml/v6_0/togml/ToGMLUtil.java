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
package nl.overheid.aerius.gml.v6_0.togml;

import java.util.function.Supplier;

import nl.overheid.aerius.gml.v6_0.base.ReferenceType;
import nl.overheid.aerius.gml.v6_0.source.characteristics.AbstractDiurnalVariation;
import nl.overheid.aerius.gml.v6_0.source.characteristics.ReferenceDiurnalVariation;
import nl.overheid.aerius.gml.v6_0.source.characteristics.StandardDiurnalVariation;
import nl.overheid.aerius.util.gml.GMLIdUtil;

final class ToGMLUtil {

  private ToGMLUtil() {
    // Util class
  }

  /**
   * @param gmlId Id to reference, should not be null and already a valid GML ID.
   */
  static ReferenceType toReferenceType(final String gmlId) {
    return toValidReferenceType(gmlId);
  }

  /**
   * @param id Id to reference, can be null and will be made into a valid GML ID if needed.
   * @param validIdPrefixFallback Prefix to use if the ID isn't valid yet and needs a prefix.
   */
  static ReferenceType toReferenceType(final String id, final String validIdPrefixFallback) {
    final ReferenceType reference;
    if (id == null) {
      reference = null;
    } else {
      final String gmlId = GMLIdUtil.toValidGmlId(id, validIdPrefixFallback);
      reference = toValidReferenceType(gmlId);
    }
    return reference;
  }

  private static ReferenceType toValidReferenceType(final String gmlId) {
    final ReferenceType reference = new ReferenceType(null);
    reference.setHref("#" + gmlId);
    return reference;
  }

  static AbstractDiurnalVariation determineDiurnalVariation(final Supplier<String> customDiurnalVariationIdGetter,
      final Supplier<String> standardDiurnalVariationCodeGetter) {
    AbstractDiurnalVariation gmlVariation = null;
    final String customDiurnalVariationId = customDiurnalVariationIdGetter.get();
    final String standardDiurnalVariationCode = standardDiurnalVariationCodeGetter.get();
    if (customDiurnalVariationId != null) {
      final ReferenceDiurnalVariation referenceVariation = new ReferenceDiurnalVariation();
      referenceVariation.setCustomDiurnalVariation(
          ToGMLUtil.toReferenceType(customDiurnalVariationId, GMLIdUtil.DIURNAL_VARIATION_PREFIX));
      gmlVariation = referenceVariation;
    } else if (standardDiurnalVariationCode != null) {
      final StandardDiurnalVariation standardVariation = new StandardDiurnalVariation();
      standardVariation.setCode(standardDiurnalVariationCode);
      gmlVariation = standardVariation;
    }
    return gmlVariation;
  }

}
