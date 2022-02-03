<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.0 https://imaer.aerius.nl/5.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>NotImportant</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>1.0-SNAPSHOT_20130819_3cd3c407ef</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3210" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Rondrij terrein</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>69422.14 443595.6 69516.22 443575.44 69522.94 443696.4 69435.58 443689.68 69422.14 443595.6</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.075</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>202.56</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SII75560DSN">
                    <imaer:description>Traktors</imaer:description>
                    <imaer:literFuelPerYear>10000</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>512</imaer:operatingHoursPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="3210" gml:id="ES.1_0">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_0</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Rondrij terrein; Eigen ontwerp traktor</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>100.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>3.0</imaer:emissionHeight>
                    <imaer:spread>2.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_0.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>69422.14 443595.6 69516.22 443575.44 69522.94 443696.4 69435.58 443689.68 69422.14 443595.6</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>500.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>1000.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
