<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/3.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/3.1 http://imaer.aerius.nl/3.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2013</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>RpN7o3P6EDZv</imaer:reference>
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
            <imaer:label>schovels</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>62188.06 440188.56 62423.26 439415.76 64385.5 440091.12 64876.06 440689.2 64849.18 440820.24 62188.06 440188.56</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.8371</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>2606.6116</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="S1A">
                    <imaer:description>Voertuigen</imaer:description>
                    <imaer:literFuelPerYear>100000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3220" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wiellader</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>184106.0 430567.86 184095.08 430560.3 184064.0 430434.3 184059.8 430389.78 184065.68 430356.18 184160.6 430177.26 184368.92 430283.94 184336.16 430307.46 184295.0 430392.3 184216.04 430382.22 184232.84 430513.26 184106.0 430567.86</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>2.5113</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>7819.8348</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="S1A">
                    <imaer:description></imaer:description>
                    <imaer:literFuelPerYear>300000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3220" gml:id="ES.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Hydraulische kraan</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.3.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>183915.74 430499.82 183967.82 430545.18 184073.66 430506.54 184051.82 430434.3 183932.54 430491.42 183915.74 430499.82</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>8.371</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>26066.116</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="S1A">
                    <imaer:description></imaer:description>
                    <imaer:literFuelPerYear>1000000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3220" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Hydraulische kraan</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.POINT">
                            <gml:pos>184060.22 430530.06</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>4.1855</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>13033.058</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="S1A">
                    <imaer:description></imaer:description>
                    <imaer:literFuelPerYear>500000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3220" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Droog Grondverzet</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>184209.74 430603.98 184229.9 430724.94 184278.62 430867.74 184387.82 430844.22 184418.06 430832.46 184428.14 430819.02 184466.78 430805.58 184463.42 430777.02 184471.82 430746.78 184465.1 430719.9 184431.5 430704.78 184394.54 430575.42 184371.02 430598.94 184345.82 430598.94 184307.18 430580.46 184281.98 430572.06 184260.14 430538.46 184208.06 430592.22 184209.74 430603.98</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>7.5339</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>23459.5044</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="S1A">
                    <imaer:description></imaer:description>
                    <imaer:literFuelPerYear>900000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3210" gml:id="ES.8">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.8</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Rondrij terrein</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.8.SURFACE">
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
                    <imaer:value>0.08371</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>1173.33332</imaer:value>
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
                    <imaer:description>Eigen ontwerp traktor</imaer:description>
                    <imaer:emission>
                        <imaer:Emission substance="NOX">
                            <imaer:value>1000.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
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
                </imaer:CustomOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="S2F">
                    <imaer:description>Traktors</imaer:description>
                    <imaer:literFuelPerYear>10000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3210" gml:id="ES.9">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.9</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>schovels</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.9.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>62188.06 440188.56 62423.26 439415.76 64385.5 440091.12 64876.06 440689.2 64849.18 440820.24 62188.06 440188.56</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>0.0</imaer:value>
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
                    <imaer:description>tt</imaer:description>
                    <imaer:emission>
                        <imaer:Emission substance="NOX">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emissionSourceCharacteristics>
                        <imaer:EmissionSourceCharacteristics>
                            <imaer:heatContent>
<imaer:SpecifiedHeatContent>
    <imaer:value>0.0</imaer:value>
</imaer:SpecifiedHeatContent>
                            </imaer:heatContent>
                            <imaer:emissionHeight>4.0</imaer:emissionHeight>
                            <imaer:spread>4.0</imaer:spread>
                        </imaer:EmissionSourceCharacteristics>
                    </imaer:emissionSourceCharacteristics>
                </imaer:CustomOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
