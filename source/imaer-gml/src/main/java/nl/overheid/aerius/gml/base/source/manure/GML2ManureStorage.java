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
package nl.overheid.aerius.gml.base.source.manure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.manure.AbstractManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.CustomManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.StandardManureStorage;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2ManureStorage<T extends IsGmlManureStorageEmissionSource> extends AbstractGML2Specific<T, ManureStorageEmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2ManureStorage.class);

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2ManureStorage(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public ManureStorageEmissionSource convert(final T source) throws AeriusException {
    final ManureStorageEmissionSource emissionSource = new ManureStorageEmissionSource();
    for (final IsGmlProperty<IsGmlManureStorage> manureStorageProperty : source.getManureStorages()) {
      final IsGmlManureStorage gmlManureStorage = manureStorageProperty.getProperty();
      final AbstractManureStorage manureStorage;
      if (gmlManureStorage instanceof IsGmlCustomManureStorage) {
        manureStorage = convertCustom((IsGmlCustomManureStorage) gmlManureStorage, source.getId());
      } else if (gmlManureStorage instanceof IsGmlStandardManureStorage) {
        manureStorage = convertStandard((IsGmlStandardManureStorage) gmlManureStorage);
      } else {
        LOG.error("Don't know how to treat manure storage type: {}", gmlManureStorage.getClass());
        throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
      }
      manureStorage.setTonnes(gmlManureStorage.getTonnes());
      manureStorage.setMetersSquared(gmlManureStorage.getMetersSquared());
      manureStorage.setNumberOfDays(gmlManureStorage.getNumberOfDays());

      emissionSource.getSubSources().add(manureStorage);
    }
    return emissionSource;
  }

  private CustomManureStorage convertCustom(final IsGmlCustomManureStorage gmlStorage, final String sourceId) throws AeriusException {
    final CustomManureStorage storage = new CustomManureStorage();
    storage.setDescription(gmlStorage.getDescription());
    storage.setAnimalCode(gmlStorage.getAnimalCode());
    storage.setFarmEmissionFactorType(determineEmissionFactorType(gmlStorage.getEmissionFactorType(), sourceId));
    for (final IsGmlProperty<IsGmlEmission> emissionFactorProperty : gmlStorage.getEmissionFactors()) {
      final IsGmlEmission emissionFactor = emissionFactorProperty.getProperty();
      storage.getEmissionFactors().put(emissionFactor.getSubstance(), emissionFactor.getValue());
    }
    return storage;
  }

  private FarmEmissionFactorType determineEmissionFactorType(final String gmlEmissionFactorType, final String sourceId) throws AeriusException {
    final FarmEmissionFactorType type = FarmEmissionFactorType.safeValueOf(gmlEmissionFactorType);
    if (type == null) {
      throw new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE, sourceId, gmlEmissionFactorType);
    }
    return type;
  }

  private StandardManureStorage convertStandard(final IsGmlStandardManureStorage gmlStorage) {
    final StandardManureStorage storage = new StandardManureStorage();
    storage.setManureStorageCode(gmlStorage.getManureStorageCode());
    return storage;
  }
}
