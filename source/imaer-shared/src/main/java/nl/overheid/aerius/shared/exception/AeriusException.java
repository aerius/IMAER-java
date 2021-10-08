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
package nl.overheid.aerius.shared.exception;

import java.io.Serializable;
import java.util.Date;

import nl.overheid.aerius.shared.domain.HasReference;

/**
 *
 */
public class AeriusException extends Exception implements HasReference, Serializable {

  public interface Reason {
    int getErrorCode();

    default String getErrorCodeKey() {
      return "e" + getErrorCode();
    }

    String name();
  }

  private static final long serialVersionUID = 1L;

  private Reason reason;
  private long reference;
  private String[] args;

  // Needed for GWT.
  public AeriusException() {
  }

  public AeriusException(final Reason errorCode, final String... args) {
    this(errorCode, new Date().getTime(), args);
  }

  public AeriusException(final Reason errorCode, final long reference, final String... args) {
    super();
    this.reason = errorCode;
    this.reference = new Date().getTime();
    this.args = args;
  }

  public boolean isInternalError() {
    return reason.getErrorCode() < 1000;
  }

  public Reason getReason() {
    return reason;
  }

  @Override
  public long getReference() {
    return reference;
  }

  public String[] getArgs() {
    return args;
  }

  @Override
  public String getMessage() {
    return toString();
  }

  @Override
  public String toString() {
    final StringBuilder str = new StringBuilder(64);

    str.append("[errorCode=").append(reason).append(",reference=").append(reference).append(",args=[");
    if (args != null) {
      for (int i = 0; i < args.length; ++i) {
        if (i != 0) {
          str.append(',');
        }
        str.append(args[i]);
      }
    }
    str.append("]];");
    return str.toString();
  }
}
