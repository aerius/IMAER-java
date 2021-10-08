<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/0.5" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/0.5 http://imaer.aerius.nl/0.5/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2013</imaer:year>
        <imaer:version>V0.5</imaer:version>
        <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:reference>SomeReference001</imaer:reference>
        <imaer:projectName>Situatie 1</imaer:projectName>
        <imaer:corporation>Big Corp</imaer:corporation>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
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
                        <gml:LineString gml:id="ES.4.CURVE" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>136558 455251 208413 474162</gml:posList>
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
                    <imaer:shipsPerYear>30000</imaer:shipsPerYear>
                    <imaer:averageResidenceTime>100</imaer:averageResidenceTime>
                    <imaer:inlandRoute>
                        <gml:LineString gml:id="NL.IMAER.InlandRoute.4_0_0" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>208413 474162 136558 474162</gml:posList>
                        </gml:LineString>
                    </imaer:inlandRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovements>60000</imaer:shippingMovements>
                            <imaer:route>
<gml:LineString gml:id="NL.IMAER.MaritimeRoute.4_0_0" srsName="urn:ogc:def:crs:EPSG::28992">
    <gml:posList>136558 474162 208413 474162</gml:posList>
</gml:LineString>
                            </imaer:route>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                </imaer:MooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
            <imaer:mooringMaritimeShipping>
                <imaer:MooringMaritimeShipping shipType="OO1600">
                    <imaer:description>Puntertje</imaer:description>
                    <imaer:shipsPerYear>15000</imaer:shipsPerYear>
                    <imaer:averageResidenceTime>200</imaer:averageResidenceTime>
                    <imaer:inlandRoute>
                        <gml:LineString gml:id="NL.IMAER.InlandRoute.4_1_1" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>136558 455251 136558 474162</gml:posList>
                        </gml:LineString>
                    </imaer:inlandRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovements>30000</imaer:shippingMovements>
                            <imaer:route>
<gml:LineString gml:id="NL.IMAER.MaritimeRoute.4_1_0" srsName="urn:ogc:def:crs:EPSG::28992">
    <gml:posList>136558 474162 208413 474162</gml:posList>
</gml:LineString>
                            </imaer:route>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                </imaer:MooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
        </imaer:MooringMaritimeShippingEmissionSource>
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
                        <gml:LineString gml:id="ES.7.CURVE" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>136558 455251 208413 474162</gml:posList>
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
                    <imaer:shipsPerYear>30000</imaer:shipsPerYear>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
            <imaer:maritimeShipping>
                <imaer:MaritimeShipping shipType="OO1600">
                    <imaer:description>Puntertje</imaer:description>
                    <imaer:shipsPerYear>15000</imaer:shipsPerYear>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
        </imaer:MaritimeShippingEmissionSource>
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
                        <gml:LineString gml:id="ES.8.CURVE" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>208413 455251 208513 455151</gml:posList>
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
                    <imaer:numberOfShipsAtoB>50</imaer:numberOfShipsAtoB>
                    <imaer:numberOfShipsBtoA>150</imaer:numberOfShipsBtoA>
                    <imaer:percentageLadenAtoB>20</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>40</imaer:percentageLadenBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
            <imaer:inlandShipping>
                <imaer:InlandShipping shipType="BII-1">
                    <imaer:description>Veerpont</imaer:description>
                    <imaer:numberOfShipsAtoB>300</imaer:numberOfShipsAtoB>
                    <imaer:numberOfShipsBtoA>280</imaer:numberOfShipsBtoA>
                    <imaer:percentageLadenAtoB>80</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>82</imaer:percentageLadenBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
        </imaer:InlandShippingEmissionSource>
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
                        <gml:LineString gml:id="ES.9.CURVE" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>208413 474162 208513 474062</gml:posList>
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
<gml:LineString gml:id="NL.IMAER.InlandMooringRoute.9_0_0" srsName="urn:ogc:def:crs:EPSG::28992">
    <gml:posList>208413 474162 208413 474262</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovements>3500</imaer:shippingMovements>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>DEPART</imaer:direction>
                            <imaer:percentageLaden>64</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString gml:id="NL.IMAER.InlandMooringRoute.9_0_1" srsName="urn:ogc:def:crs:EPSG::28992">
    <gml:posList>208413 474162 208413 474262</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovements>2050</imaer:shippingMovements>
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
<gml:LineString gml:id="NL.IMAER.InlandMooringRoute.9_1_0" srsName="urn:ogc:def:crs:EPSG::28992">
    <gml:posList>208413 474162 208413 474262</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovements>507</imaer:shippingMovements>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                    <imaer:route>
                        <imaer:InlandShippingRoute>
                            <imaer:direction>DEPART</imaer:direction>
                            <imaer:percentageLaden>81</imaer:percentageLaden>
                            <imaer:route>
<gml:LineString gml:id="NL.IMAER.InlandMooringRoute.9_1_1" srsName="urn:ogc:def:crs:EPSG::28992">
    <gml:posList>208413 474162 208413 474262</gml:posList>
</gml:LineString>
                            </imaer:route>
                            <imaer:shippingMovements>120</imaer:shippingMovements>
                        </imaer:InlandShippingRoute>
                    </imaer:route>
                </imaer:MooringInlandShipping>
            </imaer:mooringInlandShipping>
        </imaer:MooringInlandShippingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
