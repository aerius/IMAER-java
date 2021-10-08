<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/3.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/3.1 http://imaer.aerius.nl/3.1/IMAER.xsd">
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
                <imaer:MooringInlandShipping shipType="BII-6L">
                    <imaer:description>Duwstel</imaer:description>
                    <imaer:averageResidenceTime>20</imaer:averageResidenceTime>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>ARRIVE</imaer:direction>
                            <imaer:percentageLaden>10</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.13.InlandMooringRoute.0_0">
    <gml:posList>69015.550770686 443222.65891309 69139.9 442933.68 69526.3 442617.84</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovementsPerTimeUnit>100</imaer:shippingMovementsPerTimeUnit>
                            <imaer:waterway>
<imaer:InlandWaterway>
    <imaer:type>CEMT_VIb</imaer:type>
</imaer:InlandWaterway>
                            </imaer:waterway>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>DEPART</imaer:direction>
                            <imaer:percentageLaden>90</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.13.InlandMooringRoute.0_1">
    <gml:posList>69015.550770686 443222.65891309 68787.1 443195.76 68407.42 443370.48</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovementsPerTimeUnit>200</imaer:shippingMovementsPerTimeUnit>
                            <imaer:waterway>
<imaer:InlandWaterway>
    <imaer:type>CEMT_VIb</imaer:type>
</imaer:InlandWaterway>
                            </imaer:waterway>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                </imaer:MooringInlandShipping>
            </imaer:mooringInlandShipping>
        </imaer:MooringInlandShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
