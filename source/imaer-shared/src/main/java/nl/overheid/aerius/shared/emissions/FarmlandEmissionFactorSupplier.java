package nl.overheid.aerius.shared.emissions;

import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;

public interface FarmlandEmissionFactorSupplier {

  Map<Substance, Double> getGrazingEmissionFactors(String grazingCategoryCode);
}
