<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/2.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/2.1 http://imaer.aerius.nl/2.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2020</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>Raz1yvtxZgT4</imaer:reference>
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
        <imaer:MooringMaritimeShippingEmissionSource sectorId="7510" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Zeescheepvaart haven</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.CURVE">
                            <gml:posList>68320.06 443682.96 68924.86 443286.48</gml:posList>
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
                    <imaer:value>77226.01690728687</imaer:value>
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
            <imaer:mooringMaritimeShipping>
                <imaer:MooringMaritimeShipping shipType="OO10000">
                    <imaer:description>Olie tankers</imaer:description>
                    <imaer:shipsPerTimeUnit>300</imaer:shipsPerTimeUnit>
                    <imaer:averageResidenceTime>22</imaer:averageResidenceTime>
                    <imaer:inlandRoute>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandRoute.1_0_0">
                            <gml:posList>68621.969431611 443485.04159483 65806.78 444576.72 63105.34 445504.08 60000.0 446773.0</gml:posList>
                        </gml:LineString>
                    </imaer:inlandRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovementsPerTimeUnit>400</imaer:shippingMovementsPerTimeUnit>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.MaritimeRoute.1_0_0">
    <gml:posList>60000.0 446773.0 56976.7 448084.56</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovementsPerTimeUnit>200</imaer:shippingMovementsPerTimeUnit>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.MaritimeRoute.1_0_1">
    <gml:posList>60000.0 446773.0 61156.54 448528.08</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:MooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
        </imaer:MooringMaritimeShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
