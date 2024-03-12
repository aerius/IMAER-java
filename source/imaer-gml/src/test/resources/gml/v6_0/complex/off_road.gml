<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/6.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
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
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>V1.1</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="1800" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeOffRoadMobileSource</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.POINT">
                            <gml:pos>136558.0 455251.0</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>102671.334</imaer:value>
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
            <imaer:offRoadMobileSource>
                <imaer:CustomOffRoadMobileSource>
                    <imaer:description>My Big Custom Bike</imaer:description>
                    <imaer:emission>
                        <imaer:Emission substance="NOX">
                            <imaer:value>101010.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emissionSourceCharacteristics>
                        <imaer:EmissionSourceCharacteristics>
                            <imaer:heatContent>
                                <imaer:SpecifiedHeatContent>
                                    <imaer:value>20.0</imaer:value>
                                </imaer:SpecifiedHeatContent>
                            </imaer:heatContent>
                            <imaer:emissionHeight>10.0</imaer:emissionHeight>
                            <imaer:spread>5.0</imaer:spread>
                        </imaer:EmissionSourceCharacteristics>
                    </imaer:emissionSourceCharacteristics>
                </imaer:CustomOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="B4T">
                    <imaer:description>My Little Offroader</imaer:description>
                    <imaer:literFuelPerYear>30000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
