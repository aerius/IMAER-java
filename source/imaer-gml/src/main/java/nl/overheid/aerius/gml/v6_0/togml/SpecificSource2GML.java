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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.overheid.aerius.gml.v6_0.source.Emission;
import nl.overheid.aerius.gml.v6_0.source.EmissionProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Base class for {@link EmissionSource} objects conversion to GML.
 * @param <T> specific {@link EmissionSource}
 */
abstract class SpecificSource2GML<T extends EmissionSource> {

  protected static final String ID_SEPARATOR = "_";

  abstract nl.overheid.aerius.gml.v6_0.source.EmissionSource convert(T emissionSource) throws AeriusException;

  protected static List<EmissionProperty> getEmissions(final Map<Substance, Double> emissionValues, final Substance defaultSubstance) {
    final List<EmissionProperty> emissions = new ArrayList<>(emissionValues.entrySet().size() + 1);

    // Not using entryset to have a predictable order
    for (final Substance substance : Substance.values()) {
      if (emissionValues.containsKey(substance)) {
        final Emission emission = new Emission();
        emission.setSubstance(substance);
        emission.setValue(emissionValues.get(substance));
        emissions.add(new EmissionProperty(emission));
      }
    }
    if (emissions.isEmpty()) {
      final Emission emission = new Emission();
      emission.setSubstance(defaultSubstance);
      emission.setValue(0.0);
      emissions.add(new EmissionProperty(emission));
    }
    return emissions;
  }

}
