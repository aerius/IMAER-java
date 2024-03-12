<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/6.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>RZFV1Faab6mW</imaer:reference>
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
        <imaer:MooringInlandShippingEmissionSource sectorId="7610" gml:id="ES.13">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.13</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>binnenvaart aanlegplaats</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.13.CURVE">
                            <gml:posList>68958.46 443259.6 69072.7 443185.68</gml:posList>
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
            <imaer:mooringInlandShipping>
                <imaer:StandardMooringInlandShipping shipType="BII-6L">
                    <imaer:description>Duwstel</imaer:description>
                    <imaer:averageResidenceTime>20</imaer:averageResidenceTime>
                    <imaer:shorePowerFactor>0.0</imaer:shorePowerFactor>
                    <imaer:shipsPerTimeUnit>150</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:percentageLaden>63</imaer:percentageLaden>
                </imaer:StandardMooringInlandShipping>
            </imaer:mooringInlandShipping>
        </imaer:MooringInlandShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:InlandShippingEmissionSource sectorId="7620" gml:id="ES.InlandMooringRoute.0">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.InlandMooringRoute.0</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>binnenvaart aanlegplaats; Route 1</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.InlandMooringRoute.0.CURVE">
                            <gml:posList>69015.550770686 443222.65891309 69139.9 442933.68 69526.3 442617.84</gml:posList>
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
                <imaer:StandardInlandShipping shipType="BII-6L">
                    <imaer:description>Duwstel</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>0</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>100</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>0</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>10</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:StandardInlandShipping>
            </imaer:inlandShipping>
            <imaer:waterway>
                <imaer:InlandWaterway>
                    <imaer:type>CEMT_VIb</imaer:type>
                </imaer:InlandWaterway>
            </imaer:waterway>
            <imaer:mooringA xlink:href="#ES.13"/>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:InlandShippingEmissionSource sectorId="7620" gml:id="ES.InlandMooringRoute.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.InlandMooringRoute.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>binnenvaart aanlegplaats; Route 2</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.InlandMooringRoute.1.CURVE">
                            <gml:posList>69015.550770686 443222.65891309 68787.1 443195.76 68407.42 443370.48</gml:posList>
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
                <imaer:StandardInlandShipping shipType="BII-6L">
                    <imaer:description>Duwstel</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>200</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>0</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>90</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>0</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:StandardInlandShipping>
            </imaer:inlandShipping>
            <imaer:waterway>
                <imaer:InlandWaterway>
                    <imaer:type>CEMT_VIb</imaer:type>
                </imaer:InlandWaterway>
            </imaer:waterway>
            <imaer:mooringA xlink:href="#ES.13"/>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
