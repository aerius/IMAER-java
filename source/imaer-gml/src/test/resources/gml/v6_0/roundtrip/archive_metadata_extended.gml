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
                    <imaer:archiveType>DEVELOPMENT_PRESSURE_SEARCH</imaer:archiveType>
                    <imaer:project>
                        <imaer:ArchiveProject>
                            <imaer:id>some-unique-id</imaer:id>
                            <imaer:name>Our Archived Project</imaer:name>
                            <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                            <imaer:projectType>AGRICULTURE</imaer:projectType>
                            <imaer:permitReference>permitRef1</imaer:permitReference>
                            <imaer:planningReference>planningRef1</imaer:planningReference>
                            <imaer:netEmission>
<imaer:Emission substance="NOX">
    <imaer:value>1000.0</imaer:value>
</imaer:Emission>
                            </imaer:netEmission>
                            <imaer:netEmission>
<imaer:Emission substance="NH3">
    <imaer:value>500.0</imaer:value>
</imaer:Emission>
                            </imaer:netEmission>
                            <imaer:centroid>
<gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="APM.1.POINT">
    <gml:pos>184080.38 430602.3</gml:pos>
</gml:Point>
                            </imaer:centroid>
                        </imaer:ArchiveProject>
                    </imaer:project>
                    <imaer:project>
                        <imaer:ArchiveProject>
                            <imaer:id>another-unique-id</imaer:id>
                            <imaer:name>Some older archived project</imaer:name>
                            <imaer:aeriusVersion>DEV_OLD</imaer:aeriusVersion>
                            <imaer:projectType>TRAFFIC</imaer:projectType>
                            <imaer:permitReference>permitRef2</imaer:permitReference>
                            <imaer:planningReference>planningRef2</imaer:planningReference>
                            <imaer:netEmission>
<imaer:Emission substance="NOX">
    <imaer:value>100.0</imaer:value>
</imaer:Emission>
                            </imaer:netEmission>
                            <imaer:netEmission>
<imaer:Emission substance="NH3">
    <imaer:value>-300.0</imaer:value>
</imaer:Emission>
                            </imaer:netEmission>
                            <imaer:centroid>
<gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="APM.2.POINT">
    <gml:pos>194080.38 420602.3</gml:pos>
</gml:Point>
                            </imaer:centroid>
                        </imaer:ArchiveProject>
                    </imaer:project>
                </imaer:ArchiveMetadata>
            </imaer:archive>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
</imaer:FeatureCollectionCalculator>
