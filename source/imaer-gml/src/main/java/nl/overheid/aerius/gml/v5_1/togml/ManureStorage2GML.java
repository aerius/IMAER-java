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
package nl.overheid.aerius.gml.v5_1.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v5_1.source.manure.ManureStorageProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.manure.AbstractManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.CustomManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.StandardManureStorage;

/**
 *
 */
class ManureStorage2GML extends SpecificSource2GML<ManureStorageEmissionSource> {

  private static final Substance FALLBACK_SUBSTANCE = Substance.NH3;

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource convert(final ManureStorageEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v5_1.source.manure.ManureStorageEmissionSource returnSource =
        new nl.overheid.aerius.gml.v5_1.source.manure.ManureStorageEmissionSource();
    final List<AbstractManureStorage> manureStorages = emissionSource.getSubSources();
    final List<ManureStorageProperty> manureStorageProperties = new ArrayList<>(manureStorages.size());

    for (final AbstractManureStorage manureStorage : manureStorages) {
      if (manureStorage instanceof CustomManureStorage) {
        manureStorageProperties.add(new ManureStorageProperty(convert((CustomManureStorage) manureStorage)));
      } else if (manureStorage instanceof StandardManureStorage) {
        manureStorageProperties.add(new ManureStorageProperty(convert((StandardManureStorage) manureStorage)));
      }
    }

    returnSource.setManureStorages(manureStorageProperties);
    return returnSource;
  }

  private nl.overheid.aerius.gml.v5_1.source.manure.CustomManureStorage convert(final CustomManureStorage storage) {
    final nl.overheid.aerius.gml.v5_1.source.manure.CustomManureStorage gmlStorage =
        new nl.overheid.aerius.gml.v5_1.source.manure.CustomManureStorage();
    gmlStorage.setDescription(storage.getDescription());
    gmlStorage.setAnimalCode(storage.getAnimalCode());
    gmlStorage.setEmissionFactorType(storage.getFarmEmissionFactorType().name());
    gmlStorage.setEmissionFactors(getEmissions(storage.getEmissionFactors(), FALLBACK_SUBSTANCE));
    setBasicProperties(storage, gmlStorage);
    return gmlStorage;
  }

  private nl.overheid.aerius.gml.v5_1.source.manure.StandardManureStorage convert(final StandardManureStorage storage) {
    final nl.overheid.aerius.gml.v5_1.source.manure.StandardManureStorage gmlStorage =
        new nl.overheid.aerius.gml.v5_1.source.manure.StandardManureStorage();
    gmlStorage.setManureStorageCode(storage.getManureStorageCode());
    setBasicProperties(storage, gmlStorage);
    return gmlStorage;
  }

  private void setBasicProperties(final AbstractManureStorage storage,
      final nl.overheid.aerius.gml.v5_1.source.manure.AbstractManureStorage gmlStorage) {
    gmlStorage.setTonnes(storage.getTonnes());
    gmlStorage.setMetersSquared(storage.getMetersSquared());
    gmlStorage.setNumberOfDays(storage.getNumberOfDays());
  }

}
