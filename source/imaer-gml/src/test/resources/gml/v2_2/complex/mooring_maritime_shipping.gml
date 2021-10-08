<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/2.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/2.2 http://imaer.aerius.nl/2.2/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2013</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:corporation>Big Corp</imaer:corporation>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>SomeReference001</imaer:reference>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>V0.5</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:MooringMaritimeShippingEmissionSource sectorId="1800" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeMooringMaritimeShipSource</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.CURVE">
                            <gml:posList>136558.0 455251.0 208413.0 474162.0</gml:posList>
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
                    <imaer:value>1.0449567039263006E7</imaer:value>
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
                <imaer:MooringMaritimeShipping shipType="OO100">
                    <imaer:description>Motorboot</imaer:description>
                    <imaer:shipsPerTimeUnit>30000</imaer:shipsPerTimeUnit>
                    <imaer:averageResidenceTime>100</imaer:averageResidenceTime>
                    <imaer:inlandRoute>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandRoute.4_0_0">
                            <gml:posList>208413.0 474162.0 136558.0 474162.0</gml:posList>
                        </gml:LineString>
                    </imaer:inlandRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovementsPerTimeUnit>60000</imaer:shippingMovementsPerTimeUnit>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.MaritimeRoute.4_0_0">
    <gml:posList>136558.0 474162.0 208413.0 474162.0</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:MooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
            <imaer:mooringMaritimeShipping>
                <imaer:MooringMaritimeShipping shipType="OO1600">
                    <imaer:description>Puntertje</imaer:description>
                    <imaer:shipsPerTimeUnit>15000</imaer:shipsPerTimeUnit>
                    <imaer:averageResidenceTime>200</imaer:averageResidenceTime>
                    <imaer:inlandRoute>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandRoute.4_1_1">
                            <gml:posList>136558.0 455251.0 136558.0 474162.0</gml:posList>
                        </gml:LineString>
                    </imaer:inlandRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovementsPerTimeUnit>30000</imaer:shippingMovementsPerTimeUnit>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.MaritimeRoute.4_1_0">
    <gml:posList>136558.0 474162.0 208413.0 474162.0</gml:posList>
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
    <imaer:featureMember>
        <imaer:MaritimeShippingEmissionSource movementType="INLAND" sectorId="1800" gml:id="ES.7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeMaritimeShipSource</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.7.CURVE">
                            <gml:posList>136558.0 455251.0 208413.0 474162.0</gml:posList>
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
                    <imaer:value>1982661.3199265539</imaer:value>
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
            <imaer:maritimeShipping>
                <imaer:MaritimeShipping shipType="OO100">
                    <imaer:description>Motorboot</imaer:description>
                    <imaer:shipsPerTimeUnit>30000</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
            <imaer:maritimeShipping>
                <imaer:MaritimeShipping shipType="OO1600">
                    <imaer:description>Puntertje</imaer:description>
                    <imaer:shipsPerTimeUnit>15000</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
        </imaer:MaritimeShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:InlandShippingEmissionSource sectorId="1800" gml:id="ES.8">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.8</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeInlandShipSource</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.8.CURVE">
                            <gml:posList>208413.0 455251.0 208513.0 455151.0</gml:posList>
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
                    <imaer:value>16324.082417331447</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>503.23704106652923</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
             <imaer:inlandShipping>
                <imaer:InlandShipping shipType="BI">
                    <imaer:description>Duikboot</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>50</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>150</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>20</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>40</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
            <imaer:inlandShipping>
                <imaer:InlandShipping shipType="BII-1">
                    <imaer:description>Veerpont</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>300</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>280</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>80</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>82</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
            <imaer:waterway>
                <imaer:InlandWaterway>
                    <imaer:type>CEMT_Vb</imaer:type>
                </imaer:InlandWaterway>
             </imaer:waterway>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:MooringInlandShippingEmissionSource sectorId="1800" gml:id="ES.9">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.9</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeMooringInlandShipSource</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.9.CURVE">
                            <gml:posList>208413.0 474162.0 208513.0 474062.0</gml:posList>
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
                    <imaer:value>1720.929304</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>374.561032695</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:mooringInlandShipping>
                <imaer:MooringInlandShipping shipType="BI">
                    <imaer:description>Vliegende Hollander</imaer:description>
                    <imaer:averageResidenceTime>5</imaer:averageResidenceTime>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>ARRIVE</imaer:direction>
                            <imaer:percentageLaden>20</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandMooringRoute.9_0_0">
    <gml:posList>208413.0 474162.0 208413.0 474262.0</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovementsPerTimeUnit>3500</imaer:shippingMovementsPerTimeUnit>
                            <imaer:waterway>
                                <imaer:InlandWaterway>
                                    <imaer:type>CEMT_Vb</imaer:type>
                                </imaer:InlandWaterway>
                            </imaer:waterway>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>DEPART</imaer:direction>
                            <imaer:percentageLaden>64</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandMooringRoute.9_0_1">
    <gml:posList>208413.0 474162.0 208413.0 474262.0</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovementsPerTimeUnit>2050</imaer:shippingMovementsPerTimeUnit>
                            <imaer:waterway>
                                <imaer:InlandWaterway>
                                    <imaer:type>CEMT_Vb</imaer:type>
                                </imaer:InlandWaterway>
                            </imaer:waterway>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                </imaer:MooringInlandShipping>
            </imaer:mooringInlandShipping>
            <imaer:mooringInlandShipping>
                <imaer:MooringInlandShipping shipType="BII-1">
                    <imaer:description>Neeltje Jacoba</imaer:description>
                    <imaer:averageResidenceTime>3</imaer:averageResidenceTime>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>ARRIVE</imaer:direction>
                            <imaer:percentageLaden>20</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandMooringRoute.9_1_0">
    <gml:posList>208413.0 474162.0 208413.0 474262.0</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovementsPerTimeUnit>507</imaer:shippingMovementsPerTimeUnit>
                            <imaer:waterway>
                                <imaer:InlandWaterway>
                                    <imaer:type>CEMT_Vb</imaer:type>
                                </imaer:InlandWaterway>
                            </imaer:waterway>
                            <imaer:timeUnit>YEAR</imaer:timeUnit>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>DEPART</imaer:direction>
                            <imaer:percentageLaden>81</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandMooringRoute.9_1_1">
    <gml:posList>208413.0 474162.0 208413.0 474262.0</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovementsPerTimeUnit>120</imaer:shippingMovementsPerTimeUnit>
                            <imaer:waterway>
                                <imaer:InlandWaterway>
                                    <imaer:type>CEMT_Vb</imaer:type>
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
