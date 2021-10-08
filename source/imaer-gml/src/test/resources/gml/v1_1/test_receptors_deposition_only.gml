<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/1.1" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.1 http://imaer.aerius.nl/1.1/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2013</imaer:year>
        <imaer:version>V1.1</imaer:version>
        <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:ReceptorPoint receptorPointId="1" gml:id="CP.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.1.POINT">
                    <gml:pos>137558.0 456251.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:representation>
                <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.REPR.1">
                    <gml:exterior>
                        <gml:LinearRing>
                            <gml:posList>137589.0 456305.0 137620.0 456251.0 137589.0 456197.0 137527.0 456197.0 137496.0 456251.0 137527.0 456305.0 137589.0 456305.0</gml:posList>
                        </gml:LinearRing>
                    </gml:exterior>
                </gml:Polygon>
            </imaer:representation>
            <imaer:result>
                <imaer:Result resultType="DEPOSITION" substance="NH3">
                    <imaer:value>8546.77</imaer:value>
                </imaer:Result>
            </imaer:result>
            <imaer:result>
                <imaer:Result resultType="DEPOSITION" substance="NOX">
                    <imaer:value>968.3</imaer:value>
                </imaer:Result>
            </imaer:result>
        </imaer:ReceptorPoint>
        <imaer:CalculationPoint gml:id="CP.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.2.POINT">
                    <gml:pos>207413.0 475162.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:result>
                <imaer:Result resultType="DEPOSITION" substance="NH3">
                    <imaer:value>110.66</imaer:value>
                </imaer:Result>
            </imaer:result>
            <imaer:result>
                <imaer:Result resultType="DEPOSITION" substance="NOX">
                    <imaer:value>89.11</imaer:value>
                </imaer:Result>
            </imaer:result>
            <imaer:label>DB-team 1e depositie</imaer:label>
        </imaer:CalculationPoint>
        <imaer:CalculationPoint gml:id="CP.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.3.POINT">
                    <gml:pos>207403.0 475102.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:label>DB-team 2e depositie</imaer:label>
        </imaer:CalculationPoint>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
