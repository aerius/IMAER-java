<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/1.0" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.0 http://imaer.aerius.nl/1.0/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2030</imaer:year>
        <imaer:version>DEV</imaer:version>
        <imaer:databaseVersion>BETA8_20140904_7fbba21b46</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:projectName>Situatie 1</imaer:projectName>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
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
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>718.6907635949517</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>117.61357642140347</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
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
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandMooringRoute.13_0_0">
    <gml:posList>69015.550770686 443222.65891309 69139.9 442933.68 69526.3 442617.84</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovements>100</imaer:shippingMovements>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>DEPART</imaer:direction>
                            <imaer:percentageLaden>90</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandMooringRoute.13_0_1">
    <gml:posList>69015.550770686 443222.65891309 68787.1 443195.76 68407.42 443370.48</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovements>200</imaer:shippingMovements>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                </imaer:MooringInlandShipping>
            </imaer:mooringInlandShipping>
        </imaer:MooringInlandShippingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
