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
@XmlSchema(namespace = CalculatorSchema.NAMESPACE, xmlns = {
    @XmlNs(prefix = CalculatorSchema.PREFIX, namespaceURI = CalculatorSchema.NAMESPACE),
    @XmlNs(prefix = GMLSchema.PREFIX, namespaceURI = GMLSchema.NAMESPACE),
    @XmlNs(prefix = XLinkSchema.PREFIX, namespaceURI = XLinkSchema.NAMESPACE),
    @XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance")
}, elementFormDefault = jakarta.xml.bind.annotation.XmlNsForm.QUALIFIED, location = CalculatorSchema.PUBLIC_SCHEMA_LOCATION)
package nl.overheid.aerius.gml.v5_0.base;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlSchema;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.XLinkSchema;
