<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/6.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2023</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
            <imaer:archive>
                <imaer:ArchiveMetadata>
                    <imaer:retrievalDateTime>2023-10-30T09:30:10+02:00</imaer:retrievalDateTime>
                    <imaer:archiveType>IN_COMBINATION_PROCESS_CONTRIBUTION</imaer:archiveType>
                    <imaer:project>
                        <imaer:ArchiveProject>
                            <imaer:id>some-unique-id</imaer:id>
                            <imaer:name>Our Archived Project</imaer:name>
                            <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                        </imaer:ArchiveProject>
                    </imaer:project>
                    <imaer:project>
                        <imaer:ArchiveProject>
                            <imaer:id>another-unique-id</imaer:id>
                            <imaer:name>Some older archived project</imaer:name>
                            <imaer:aeriusVersion>DEV_OLD</imaer:aeriusVersion>
                        </imaer:ArchiveProject>
                    </imaer:project>
                </imaer:ArchiveMetadata>
            </imaer:archive>
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
                    <gml:pos>3604.0 287959.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:representation>
                <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.REPR.1">
                    <gml:exterior>
                        <gml:LinearRing>
                            <gml:posList>3635.0 288013.0 3666.0 287959.0 3635.0 287905.0 3573.0 287905.0 3542.0 287959.0 3573.0 288013.0 3635.0 288013.0</gml:posList>
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
                <imaer:CalculationResult resultType="DEPOSITION" substance="NH3">
                    <imaer:value>8546.77</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NOX">
                    <imaer:value>3.001</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="DEPOSITION" substance="NOX">
                    <imaer:value>968.3</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
        </imaer:ReceptorPoint>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:ReceptorPoint receptorPointId="2" gml:id="CP.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.2.POINT">
                    <gml:pos>3790.12097182042 287959.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:representation>
                <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NL.IMAER.REPR.2">
                    <gml:exterior>
                        <gml:LinearRing>
                            <gml:posList>3821.0 288013.0 3852.0 287959.0 3821.0 287905.0 3759.0 287905.0 3728.0 287959.0 3759.0 288013.0 3821.0 288013.0</gml:posList>
                        </gml:LinearRing>
                    </gml:exterior>
                </gml:Polygon>
            </imaer:representation>
            <imaer:result>
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NH3">
                    <imaer:value>75.8</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="DEPOSITION" substance="NH3">
                    <imaer:value>6546.77</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="CONCENTRATION" substance="NOX">
                    <imaer:value>1.001</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:result>
                <imaer:CalculationResult resultType="DEPOSITION" substance="NOX">
                    <imaer:value>768.3</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
        </imaer:ReceptorPoint>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
