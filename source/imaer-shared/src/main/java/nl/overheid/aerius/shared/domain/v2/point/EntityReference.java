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
package nl.overheid.aerius.shared.domain.v2.point;

import java.util.Map;

import nl.overheid.aerius.shared.domain.result.EmissionResultKey;

/**
 * Entity references of a NCA custom calculation point.
 */
public class EntityReference {
  private String description;
  private String code;
  private EntityType entityType;
  private Map<EmissionResultKey, Double> criticalLevels;

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public void setEntityType(final EntityType entityType) {
    this.entityType = entityType;
  }

  public Map<EmissionResultKey, Double> getCriticalLevels() {
    return criticalLevels;
  }

  public void setCriticalLevels(final Map<EmissionResultKey, Double> criticalLevels) {
    this.criticalLevels = criticalLevels;
  }

}
