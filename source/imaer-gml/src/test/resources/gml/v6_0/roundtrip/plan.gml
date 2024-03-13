<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/6.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
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
                    <imaer:reference>RefSnEa8XrbF</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>BETA8_20140904_7fbba21b46</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_0">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_0</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Huizen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>10.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.0</imaer:emissionHeight>
                    <imaer:spread>1.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_0.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>110.997</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Tussenwoningen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>20.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>4.0</imaer:emissionHeight>
                    <imaer:spread>2.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>15.5043</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Kantoren</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>30.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>6.0</imaer:emissionHeight>
                    <imaer:spread>3.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_2.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>17088.714735</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Kassen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>40.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>8.0</imaer:emissionHeight>
                    <imaer:spread>4.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_3.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>1004.0</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Centrale</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>50.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>10.0</imaer:emissionHeight>
                    <imaer:spread>5.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_4.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>3000.0</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Bouwmaterialen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>60.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:spread>6.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_5.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>67.5</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_6">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_6</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Afvalverwerking</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>70.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>14.0</imaer:emissionHeight>
                    <imaer:spread>7.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_6.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>341647.0</imaer:value>
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
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.1_7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1_7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Plan; Woonwijk; Café</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>80.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>16.0</imaer:emissionHeight>
                    <imaer:spread>8.0</imaer:spread>
                    <imaer:diurnalVariation>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1_7.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>68555.26 443877.84 68924.86 443635.92 69072.7 443810.64 68709.82 444072.72 68555.26 443877.84</gml:posList>
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
                    <imaer:value>109614.0</imaer:value>
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
