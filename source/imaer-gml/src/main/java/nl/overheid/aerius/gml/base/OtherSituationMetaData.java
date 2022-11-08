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

import nl.overheid.aerius.shared.domain.scenario.SituationType;

/**
 * MetaData object containing information about another situation that was calculated along side the situation that this metadata object is for.
 */
public class OtherSituationMetaData {

  public static final class Builder {

    private final String name;
    private final SituationType situationType;
    private final String reference;

    private Builder(final String name, final SituationType situationType, final String reference) {
      this.name = name;
      this.situationType = situationType;
      this.reference = reference;
    }

    public static Builder create(final String name, final SituationType situationType, final String reference) {
      return new Builder(name, situationType, reference);
    }

    public OtherSituationMetaData build() {
      return new OtherSituationMetaData(this);
    }

  }

  private final String name;
  private final SituationType situationType;
  private final String reference;

  private OtherSituationMetaData(final Builder builder) {
    this.name = builder.name;
    this.situationType = builder.situationType;
    this.reference = builder.reference;
  }

  public SituationType getSituationType() {
    return situationType;
  }

  public String getName() {
    return name;
  }

  public String getReference() {
    return reference;
  }

}
