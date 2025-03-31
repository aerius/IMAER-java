<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/3.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/3.0 http://imaer.aerius.nl/3.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:name>NSL project</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>NSL situatie</imaer:name>
                    <imaer:reference>KarElTjE</imaer:reference>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>1.0-doeterniettoe</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:SRM2Road sectorId="3111" gml:id="ES.NWR.1_1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.NWR.1_1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SRM2 weg</imaer:label>
            <imaer:description>Test weg voor SRM2</imaer:description>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.28</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>22.0</imaer:emissionHeight>
                    <imaer:spread>11.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.NWR.1_1.CURVE">
                            <gml:posList>136558.0 455251.0 208413.0 474162.0</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1838.3555202255436</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>17404.02093860957</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>1133.0268901674394</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>1589.7459127505733</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>980.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.2</imaer:stagnationFactor>
                    <imaer:maximumSpeed>100</imaer:maximumSpeed>
                    <imaer:strictEnforcement>true</imaer:strictEnforcement>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>200.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.2</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                    <imaer:strictEnforcement>true</imaer:strictEnforcement>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:roadManager>STATE</imaer:roadManager>
            <imaer:inNetwork xlink:href="#NW.1"/>
            <imaer:isFreeway>true</imaer:isFreeway>
            <imaer:tunnelFactor>1.1</imaer:tunnelFactor>
            <imaer:elevation>STEEP_DYKE</imaer:elevation>
            <imaer:elevationHeight>2</imaer:elevationHeight>
            <imaer:barrierLeft>
                <imaer:RoadSideBarrier>
                    <imaer:barrierType>SCREEN</imaer:barrierType>
                    <imaer:height>2.6</imaer:height>
                    <imaer:distance>3.2</imaer:distance>
                </imaer:RoadSideBarrier>
            </imaer:barrierLeft>
            <imaer:barrierRight>
                <imaer:RoadSideBarrier>
                    <imaer:barrierType>WALL</imaer:barrierType>
                    <imaer:height>3.1</imaer:height>
                    <imaer:distance>5.1</imaer:distance>
                </imaer:RoadSideBarrier>
            </imaer:barrierRight>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM1Road sectorId="3113" gml:id="ES.NWR.1_2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.NWR.1_2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SRM1 weg</imaer:label>
            <imaer:description>Een andere test weg</imaer:description>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.5</imaer:emissionHeight>
                    <imaer:spread>1.25</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.NWR.1_2.CURVE">
                            <gml:posList>128120.0 442320.0 128040.0 442180.0</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>26.867070833317502</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>936.6864361405125</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>27.889961722875128</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>263.8670055480373</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>15000.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.1</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:roadManager>PROVINCE</imaer:roadManager>
            <imaer:inNetwork xlink:href="#NW.1"/>
            <imaer:speedProfile>URBAN_TRAFFIC_NORMAL</imaer:speedProfile>
            <imaer:tunnelFactor>1.1</imaer:tunnelFactor>
        </imaer:SRM1Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:RoadNetwork gml:id="NW.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>NW.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>2_wegen_bron1.csv</imaer:label>
            <imaer:element xlink:href="#ES.NWR.1_1"/>
            <imaer:element xlink:href="#ES.NWR.1_2"/>
        </imaer:RoadNetwork>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:NSLCalculationPoint gml:id="CP.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.1.POINT">
                    <gml:pos>128130.0 442310.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:label>Toetspunt</imaer:label>
            <imaer:description>Testpunt voor SRM1 weg, met correctie</imaer:description>
            <imaer:jurisdictionId>19</imaer:jurisdictionId>
            <imaer:correction>
                <imaer:CalculationPointCorrection substance="NOX" resultType="CONCENTRATION">
                    <imaer:label>Correctie NOX</imaer:label>
                    <imaer:description>Correctie op concentratie NOX vanwege redenen</imaer:description>
                    <imaer:jurisdictionId>19</imaer:jurisdictionId>
                    <imaer:value>-3.0</imaer:value>
                </imaer:CalculationPointCorrection>
            </imaer:correction>
            <imaer:correction>
                <imaer:CalculationPointCorrection substance="PM10" resultType="CONCENTRATION">
                    <imaer:label>Correctie PM10</imaer:label>
                    <imaer:description>Correctie op concentratie PM10 vanwege andere redenen</imaer:description>
                    <imaer:jurisdictionId>21</imaer:jurisdictionId>
                    <imaer:value>-2.0</imaer:value>
                </imaer:CalculationPointCorrection>
            </imaer:correction>
            <imaer:rejectionGrounds>EXPOSURE_CRITERION</imaer:rejectionGrounds>
            <imaer:monitorSubstance>ALL</imaer:monitorSubstance>
        </imaer:NSLCalculationPoint>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM1RoadDispersionLine gml:id="DL.CP.1.ES.NWR.1_2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>DL.CP.1.ES.NWR.1_2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Overdrachtslijn</imaer:label>
            <imaer:description>Overdrachtslijn tussen SRM1 weg en rekenpunt</imaer:description>
            <imaer:jurisdictionId>22</imaer:jurisdictionId>
            <imaer:roadProfile>WIDE_STREET_CANYON</imaer:roadProfile>
            <imaer:treeProfile>SEPARATED</imaer:treeProfile>
            <imaer:calculationPoint xlink:href="#CP.1"/>
            <imaer:road xlink:href="#ES.NWR.1_2"/>
        </imaer:SRM1RoadDispersionLine>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM1RoadMeasureArea gml:id="RMA.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>RMA.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Maatregelgebied</imaer:label>
            <imaer:description>Maatregelgebied voor SRM1</imaer:description>
            <imaer:jurisdictionId>55</imaer:jurisdictionId>
            <imaer:measure>
                <imaer:SRM1RoadMeasure>
                    <imaer:vehicleType>LIGHT_TRAFFIC</imaer:vehicleType>
                    <imaer:speedProfile>URBAN_TRAFFIC_NORMAL</imaer:speedProfile>
                    <imaer:reduction>
                        <imaer:EmissionReduction substance="NOX">
                            <imaer:factor>0.8</imaer:factor>
                        </imaer:EmissionReduction>
                    </imaer:reduction>
                    <imaer:reduction>
                        <imaer:EmissionReduction substance="PM10">
                            <imaer:factor>0.7</imaer:factor>
                        </imaer:EmissionReduction>
                    </imaer:reduction>
                </imaer:SRM1RoadMeasure>
            </imaer:measure>
            <imaer:measure>
                <imaer:SRM1RoadMeasure>
                    <imaer:vehicleType>HEAVY_FREIGHT</imaer:vehicleType>
                    <imaer:speedProfile>URBAN_TRAFFIC_NORMAL</imaer:speedProfile>
                    <imaer:reduction>
                        <imaer:EmissionReduction substance="NOX">
                            <imaer:factor>0.9</imaer:factor>
                        </imaer:EmissionReduction>
                    </imaer:reduction>
                    <imaer:reduction>
                        <imaer:EmissionReduction substance="PM10">
                            <imaer:factor>0.85</imaer:factor>
                        </imaer:EmissionReduction>
                    </imaer:reduction>
                </imaer:SRM1RoadMeasure>
            </imaer:measure>
            <imaer:geometry>
                <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="RMA.1.SURFACE">
                    <gml:exterior>
                        <gml:LinearRing>
                            <gml:posList>62188.06 440188.56 62423.26 439415.76 64385.5 440091.12 64876.06 440689.2 64849.18 440820.24 62188.06 440188.56</gml:posList>
                        </gml:LinearRing>
                    </gml:exterior>
                </gml:Polygon>
            </imaer:geometry>
        </imaer:SRM1RoadMeasureArea>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
