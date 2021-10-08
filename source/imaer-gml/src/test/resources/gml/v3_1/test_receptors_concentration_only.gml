<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/3.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/3.1 http://imaer.aerius.nl/3.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2013</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:calculation>
                <imaer:CalculationMetadata>
                    <imaer:type>NATURE_AREA</imaer:type>
                    <imaer:substance>NOX</imaer:substance>
                    <imaer:substance>NH3</imaer:substance>
                    <imaer:substance>NO2</imaer:substance>
                    <imaer:resultType>CONCENTRATION</imaer:resultType>
                    <imaer:resultType>DEPOSITION</imaer:resultType>
                    <imaer:maximumRange>3.0</imaer:maximumRange>
                    <imaer:monitorSrm2Year>2030</imaer:monitorSrm2Year>
                </imaer:CalculationMetadata>
            </imaer:calculation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>V1.1</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
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
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NH3">
                    <imaer:value>95.8</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NOX">
                    <imaer:value>3.001</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
        </imaer:ReceptorPoint>
    </imaer:featureMember>
    <imaer:featureMember>
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
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NH3">
                    <imaer:value>80.0</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NOX">
                    <imaer:value>0.001</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:label>DB-team 1e depositie</imaer:label>
        </imaer:CalculationPoint>
    </imaer:featureMember>
    <imaer:featureMember>
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
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
