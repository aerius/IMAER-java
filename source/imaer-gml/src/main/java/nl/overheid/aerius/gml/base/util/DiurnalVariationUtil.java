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
package nl.overheid.aerius.gml.base.util;

import java.util.function.Consumer;

import nl.overheid.aerius.gml.base.characteristics.IsGmlDiurnalVariation;
import nl.overheid.aerius.gml.base.characteristics.IsGmlReferenceDiurnalVariation;
import nl.overheid.aerius.gml.base.characteristics.IsGmlStandardDiurnalVariation;

/**
 *
 */
public final class DiurnalVariationUtil {

  private DiurnalVariationUtil() {
    // Util class
  }

  public static void setDiurnalVariation(final IsGmlDiurnalVariation gmlDiurnalVariation,
      final Consumer<String> standardDiurnalVariationCodeSetter,
      final Consumer<String> customDiurnalVariationIdSetter) {
    if (gmlDiurnalVariation == null) {
      standardDiurnalVariationCodeSetter.accept(null);
      customDiurnalVariationIdSetter.accept(null);
    } else if (gmlDiurnalVariation instanceof IsGmlStandardDiurnalVariation) {
      final String diurnalVariationCode = ((IsGmlStandardDiurnalVariation) gmlDiurnalVariation).getCode();
      standardDiurnalVariationCodeSetter.accept(diurnalVariationCode);
      customDiurnalVariationIdSetter.accept(null);
    } else if (gmlDiurnalVariation instanceof IsGmlReferenceDiurnalVariation) {
      standardDiurnalVariationCodeSetter.accept(null);
      final IsGmlReferenceDiurnalVariation gmlReferenceDiurnalVariation = (IsGmlReferenceDiurnalVariation) gmlDiurnalVariation;
      customDiurnalVariationIdSetter.accept(gmlReferenceDiurnalVariation.getCustomDiurnalVariation().getReferredId());
    }
  }

}
