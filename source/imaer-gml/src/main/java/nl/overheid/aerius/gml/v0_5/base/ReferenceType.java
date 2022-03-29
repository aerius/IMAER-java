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
package nl.overheid.aerius.gml.v0_5.base;

import javax.xml.bind.annotation.XmlAttribute;

import nl.overheid.aerius.gml.base.XLinkSchema;

/**
 *
 */
public class ReferenceType {

  private FeatureMemberImpl featureMember;
  private String href;

  ReferenceType() {
    //no-op, needed for JAX-B
  }

  /**
   * @param featureMember The featuremember that this is referencing.
   */
  public ReferenceType(final FeatureMemberImpl featureMember) {
    this.featureMember = featureMember;
  }

  @XmlAttribute(namespace = XLinkSchema.NAMESPACE)
  public String getHref() {
    return featureMember == null ? href : "#" + featureMember.getId();
  }

  public void setHref(final String href) {
    this.href = href;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getHref() == null) ? 0 : getHref().hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    boolean equal = false;
    if (obj != null && this.getClass() == obj.getClass()) {
      final ReferenceType other = (ReferenceType) obj;
      equal = this.getHref().equals(other.getHref());
    }
    return equal;
  }

}
