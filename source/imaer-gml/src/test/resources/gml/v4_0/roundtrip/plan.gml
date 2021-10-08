<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/4.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/4.0 https://imaer.aerius.nl/4.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2013</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>RefSnEa8XrbF</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>BETA8_20140904_7fbba21b46</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:PlanEmissionSource sectorId="9000" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Woonwijk</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>472547.716035</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:plan>
                <imaer:Plan planType="PHA">
                    <imaer:description>Huizen</imaer:description>
                    <imaer:amount>100</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="PHB">
                    <imaer:description>Tussenwoningen</imaer:description>
                    <imaer:amount>10</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="POA">
                    <imaer:description>Kantoren</imaer:description>
                    <imaer:amount>105783</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="PGA">
                    <imaer:description>Kassen</imaer:description>
                    <imaer:amount>1</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="PEA">
                    <imaer:description>Centrale</imaer:description>
                    <imaer:amount>10000</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="PIA">
                    <imaer:description>Bouwmaterialen</imaer:description>
                    <imaer:amount>100</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="PWA">
                    <imaer:description>Afvalverwerking</imaer:description>
                    <imaer:amount>1</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
            <imaer:plan>
                <imaer:Plan planType="PFA">
                    <imaer:description>Café</imaer:description>
                    <imaer:amount>1</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
        </imaer:PlanEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
