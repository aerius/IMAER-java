<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/1.1" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.1 http://imaer.aerius.nl/1.1/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2020</imaer:year>
        <imaer:version>DEV</imaer:version>
        <imaer:databaseVersion>BETA8_20140904_7fbba21b46</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:projectName>Situatie 1</imaer:projectName>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
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
                    <imaer:value>71679.34235104924</imaer:value>
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
                    <imaer:shipsPerYear>300</imaer:shipsPerYear>
                    <imaer:averageResidenceTime>22</imaer:averageResidenceTime>
                    <imaer:inlandRoute>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.InlandRoute.1_0_0">
                            <gml:posList>68621.969431611 443485.04159483 65806.78 444576.72 63105.34 445504.08 60000.0 446773.0</gml:posList>
                        </gml:LineString>
                    </imaer:inlandRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovements>400</imaer:shippingMovements>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.MaritimeRoute.1_0_0">
    <gml:posList>60000.0 446773.0 56976.7 448084.56</gml:posList>
</gml:LineString>
                            </imaer:route>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                    <imaer:maritimeRoute>
                        <imaer:MaritimeShippingRoute>
                            <imaer:shippingMovements>200</imaer:shippingMovements>
                            <imaer:route>
<gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.MaritimeRoute.1_0_1">
    <gml:posList>60000.0 446773.0 61156.54 448528.08</gml:posList>
</gml:LineString>
                            </imaer:route>
                        </imaer:MaritimeShippingRoute>
                    </imaer:maritimeRoute>
                </imaer:MooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
        </imaer:MooringMaritimeShippingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
