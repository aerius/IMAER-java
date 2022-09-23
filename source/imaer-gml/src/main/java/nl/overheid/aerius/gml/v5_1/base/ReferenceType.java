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
package nl.overheid.aerius.gml.v5_1.base;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import nl.overheid.aerius.gml.base.IsGmlReferenceType;
import nl.overheid.aerius.gml.base.XLinkSchema;

/**
 *
 */
public class ReferenceType implements IsGmlReferenceType {

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
    return featureMember == null ? href : ("#" + featureMember.getId());
  }

  public void setHref(final String href) {
    this.href = href;
  }

  @Override
  @XmlTransient
  public String getReferredId() {
    return featureMember == null ? href.replace("#", "") : featureMember.getId();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    final int result = 1;
    return prime * result + ((getHref() == null) ? 0 : getHref().hashCode());
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
