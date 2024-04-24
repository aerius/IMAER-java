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
package nl.overheid.aerius.shared.domain.v2.source.farm;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Base class for farm animal additional housing systems.
 * These additional systems can be used to reduce emissions.
 */
@JsonTypeInfo(property = "additionalSystemType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = StandardAdditionalHousingSystem.class, name = "STANDARD"),
    @Type(value = CustomAdditionalHousingSystem.class, name = "CUSTOM"),
})
public abstract class AdditionalHousingSystem implements Serializable {

  private static final long serialVersionUID = 1L;

}
