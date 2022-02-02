<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.0 https://imaer.aerius.nl/5.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2014</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>RffepKW3FVSi</imaer:reference>
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
        <imaer:InlandShippingEmissionSource sectorId="7620" gml:id="ES.14">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.14</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Binnenvaart vaarroute</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.14.CURVE">
                            <gml:posList>68709.82 443098.32 69176.86 442742.16</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>20.978</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>13.574</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>12.34</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>8.638</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:inlandShipping>
                <imaer:CustomInlandShipping>
                    <imaer:description>Koppelverband</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>20</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>22</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>45</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>35</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>DAY</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>DAY</imaer:timeUnitShipsBtoA>
                    <imaer:emissionPropertiesAtoB>
                        <imaer:CustomInlandShippingEmissionProperties>
                            <imaer:emissionFactorEmpty>
<imaer:Emission substance="NOX">
    <imaer:value>2.2</imaer:value>
</imaer:Emission>
                            </imaer:emissionFactorEmpty>
                            <imaer:emissionFactorLaden>
<imaer:Emission substance="NOX">
    <imaer:value>3.1</imaer:value>
</imaer:Emission>
                            </imaer:emissionFactorLaden>
                            <imaer:heatContentEmpty>0.11</imaer:heatContentEmpty>
                            <imaer:heatContentLaden>0.05</imaer:heatContentLaden>
                            <imaer:emissionHeightEmpty>5.3</imaer:emissionHeightEmpty>
                            <imaer:emissionHeightLaden>2.7</imaer:emissionHeightLaden>
                        </imaer:CustomInlandShippingEmissionProperties>
                    </imaer:emissionPropertiesAtoB>
                    <imaer:emissionPropertiesBtoA>
                        <imaer:CustomInlandShippingEmissionProperties>
                            <imaer:emissionFactorEmpty>
<imaer:Emission substance="NOX">
    <imaer:value>3.2</imaer:value>
</imaer:Emission>
                            </imaer:emissionFactorEmpty>
                            <imaer:emissionFactorLaden>
<imaer:Emission substance="NOX">
    <imaer:value>4.1</imaer:value>
</imaer:Emission>
                            </imaer:emissionFactorLaden>
                            <imaer:heatContentEmpty>0.12</imaer:heatContentEmpty>
                            <imaer:heatContentLaden>0.06</imaer:heatContentLaden>
                            <imaer:emissionHeightEmpty>5.4</imaer:emissionHeightEmpty>
                            <imaer:emissionHeightLaden>2.8</imaer:emissionHeightLaden>
                        </imaer:CustomInlandShippingEmissionProperties>
                    </imaer:emissionPropertiesBtoA>
                </imaer:CustomInlandShipping>
            </imaer:inlandShipping>
            <imaer:waterway>
                <imaer:InlandWaterway>
                    <imaer:type>CEMT_VIb</imaer:type>
                </imaer:InlandWaterway>
            </imaer:waterway>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
