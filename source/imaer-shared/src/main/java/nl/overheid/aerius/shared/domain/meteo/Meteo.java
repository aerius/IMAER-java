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
package nl.overheid.aerius.shared.domain.meteo;

import java.io.Serializable;

/**
 * Abstract meteo class, is implemented for single years (e.g. 2014) and multiple years (e.g. 1995-2004)
 * @see SingleYearMeteo
 * @see MultiYearMeteo
 */
public abstract class Meteo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String opsFile;

  public Meteo(String opsFile) {
    this.opsFile = opsFile;
  }

  public Meteo() {
  }

  public String getOpsFile() {
    return opsFile;
  }

  public void setOpsFile(String opsFile) {
    this.opsFile = opsFile;
  }
}
