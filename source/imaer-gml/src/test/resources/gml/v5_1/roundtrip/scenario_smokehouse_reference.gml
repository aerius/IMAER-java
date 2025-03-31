<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/5.1" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2020</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Vergunde situatie</imaer:name>
                    <imaer:reference>RgZYDWKerP5d</imaer:reference>
                    <imaer:situationType>REFERENCE</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>latest_2029ce1361</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R03 2</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.CURVE">
                            <gml:posList>150679.54 398637.33 150779.92 398657.28</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.11535315741683495</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>5.3189460421853285</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.19693712626345655</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.24086695564240665</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>40.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R03 1c</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.CURVE">
                            <gml:posList>150674.31 398608.67 150701.28999999995 398617.46999999986 150731.3300000003 398627.88999999984 150743.08000000025 398634.77 150796.52000000046 398648.49999999977 150806.2300000004 398660.61499999976 150804.9825000004 398671.72874999995 150784.61875000043 398709.62562499987 150760.89500000034 398744.16249999986 150694.1275000003 398852.2962500003 150688.88375000065 398883.68312500045 150669.46187500088 398904.6265625006 150665.16000000044 398918.43000000063</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.2743291887491502</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>12.649347321118523</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.4683495735380569</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.5728220884243913</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>22.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R04a</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.3.CURVE">
                            <gml:posList>150671.98 398607.72 150675.13 398615.49 150616.75 398719.65 150558.79 398767.53 150548.71 398883.45 150568.03 398935.53</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.14788458626893694</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>6.818973597688275</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.2524765345886773</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.30879527599161444</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>14.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R04b</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.CURVE">
                            <gml:posList>150672.4 398609.19 150674.92 398618.43 150616.33 398721.75 150559.0 398767.74 150546.82 398919.78 150536.74 398926.5 150520.36 399026.04</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.21071338979792628</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>9.716016239166816</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.35974125356692593</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.4399870263656181</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>16.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R05</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.CURVE">
                            <gml:posList>150666.3299999997 398603.63000000024 150725.4400000004 398621.8799999999 150743.72000000026 398630.40999999957 150797.0300000003 398645.45999999944 150808.14500000037 398657.1849999998 150807.61250000022 398671.8674999999 150802.8800000002 398684.4500000001 150784.32000000012 398716.31999999995 150693.36 398858.97 150686.43499999994 398908.285 150706.80999999994 398923.99999999994 150703.27 398943.8 150685.61 398982.07 150621.99 399071.9 150568.08 399121.58 150522.44 399127.2 150497.63 399119.92 150493.04 399094.02 150456.0 399088.07 150438.1 399096.11</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.19482153488266865</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>8.983241162199944</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.3326098225069395</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.40680351584308533</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>8.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.6">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.6</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R08</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.6.CURVE">
                            <gml:posList>150672.4 398607.09 150674.08 398617.17 150619.05999999994 398718.80999999994 150558.15999999997 398766.69 150547.66 398886.39 150546.4 398920.41 150537.16 398929.23 150529.18000000002 398986.3500000001 150574.12 399003.15 150569.5 399038.01</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.05760229188750953</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>2.656047648082483</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.09834173666805383</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.12027836078065461</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>4.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R16</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.7.CURVE">
                            <gml:posList>150672.82 398607.51 150675.76 398618.85 150618.22 398720.91 150559.0 398767.53 150549.76 398870.85 150553.12 398930.49</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.4973482381060974</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>22.932778797749677</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.849099712207042</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>1.0385043521075505</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>48.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.8">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.8</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R102</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.8.CURVE">
                            <gml:posList>150626.57 399045.37 150639.6 399047.03 150685.61 398982.07 150703.27 398943.8 150707.64999999994 398925.26000000024 150686.85499999992 398908.07500000024 150693.36 398858.97 150806.5800000001 398671.38 150806.8300000001 398662.5699999997 150796.99999999997 398649.58999999985 150742.24999999988 398635.0299999999 150729.54000000012 398626.9849999999 150701.92 398616.84000000014 150674.31 398608.67</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.03407091209806775</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>1.57101328750637</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.05816769707934364</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.07114288899233835</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.9">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.9</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R01a</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.9.CURVE">
                            <gml:posList>150502.78 399065.99 150517.0800000001 399061.02000000025 150515.34000000003 399077.99999999994 150505.24000000005 399082.6500000001 150493.04 399094.02 150456.0 399088.07 150413.61 399046.46 150341.56000000017 398963.67000000004 150328.07000000007 398945.69999999995</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.8717694492711131</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>40.19737964469406</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>1.4883317799732214</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>1.8203268811205107</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>107.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.10">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.10</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R06</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.10.CURVE">
                            <gml:posList>150326.80999999997 398944.43999999994 150339.45999999996 398962.20000000007 150416.68 399049.48 150451.62 399083.77 150492.83 399090.83 150493.04 399094.02 150456.0 399088.07 150438.1 399096.11</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.016311736465829594</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>0.7521358587747876</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.027848275468864515</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.03406025800896024</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.11">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.11</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R01b</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.11.CURVE">
                            <gml:posList>150453.66 399055.21 150422.55999999976 399050.32000000007 150419.69999999987 399042.88999999966 150437.04000000004 398951.4600000001 150451.57000000007 398890.42 150467.56999999995 398868.6500000001</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.6701425355444023</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>30.900341758774726</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>1.1441034479890184</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>1.3993131700745811</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>107.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.12">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.12</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R104</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.12.CURVE">
                            <gml:posList>150327.44 398944.86 150339.24999999997 398961.77999999997 150371.17999999996 398996.97999999963 150407.09999999995 399039.1099999999 150419.865 399042.2200000006 150432.8399999999 398973.0900000004</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.011665577166086565</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>0.5379009719963517</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.01991610194935504</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.024358692217268836</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.13">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.13</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R09</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.13.CURVE">
                            <gml:posList>150582.50999999998 398991.05 150572.18000000002 399005.44000000024 150528.16999999998 398988.0399999999 150538.29 398925.83 150546.56 398920.21 150551.7799999997 398867.65000000026 150556.93999999986 398774.96000000014 150599.48999999993 398741.43999999994 150628.3199999995 398693.1199999998 150638.06999999966 398677.5699999998 150648.79999999978 398660.2999999999 150654.36999999962 398647.86999999976 150662.59999999983 398633.2099999999 150668.95999999982 398623.26999999996 150673.3599999998 398614.32000000007 150671.78999999998 398606.99000000005</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.05551523470551948</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>2.559813225145566</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.09477860018377549</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.11592041236437486</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>4.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.14">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.14</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R103</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.14.CURVE">
                            <gml:posList>150671.35 398606.88 150675.97 398619.48 150618.85 398722.38 150558.79 398768.16 150549.13000000012 398911.80000000045 150536.74000000008 398929.02000000014 150532.12 398966.81999999995 150518.89 398964.71999999986</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.02385464016104284</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>1.0999399298091217</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.04072592711431925</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.04981046624300655</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.15">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.15</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R15</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.15.CURVE">
                            <gml:posList>150454.7599999998 398864.6600000001 150436.08 398887.82999999984 150405.63500000013 398884.4649999999 150347.5725 398939.9025 150346.26124999984 398959.22124999994 150401.0584375003 399002.6909375 150419.7356250003 398989.8806250001 150431.04999999993 398911.33999999985</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>7.907945093608833</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>77.18371067392184</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>15.59923415725578</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>12.295229700337021</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>4470.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.16">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.16</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R101</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.16.CURVE">
                            <gml:posList>150471.01 398856.36 150544.51 398785.17 150541.57 398864.13 150477.1 398854.47 150470.59 398860.98</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.43610022395455095</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>4.25645766530983</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.8602524965678814</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.6780462386142676</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>320.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.17">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.17</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R03 1d</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.17.CURVE">
                            <gml:posList>150674.31 398608.67 150698.35000000006 398617.47 150731.33000000022 398627.25999999966 150742.5737500002 398635.27 150796.02750000008 398648.31999999995 150806.22124999992 398660.84500000003 150805.28499999986 398671.0599999999 150781.04749999975 398715.95499999955 150722.9387499996 398805.18249999953 150694.51437499956 398852.1062499998 150678.2699999997 398944.80999999924</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>2.445925814530249</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>112.78189277180475</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>4.1758163517498295</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>5.107295871791141</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>188.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="URBAN_ROAD_FREE_FLOW" sectorId="3100" gml:id="ES.18">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.18</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Wegverkeer R03 1a</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.18.CURVE">
                            <gml:posList>150752.46999999988 398706.5700000003 150765.669375 398686.4384374996 150751.68906249997 398677.31765624933 150752.6187499999 398676.3868749995 150786.18812499978 398697.2053125002 150799.17749999982 398673.29375 150800.73499999984 398661.22749999963 150789.14999999985 398648.01499999966 150740.98999999958 398634.8199999993 150723.86999999982 398627.4049999997 150708.22 398621.6700000001 150674.31 398610.76999999996</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.16081258895374986</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>7.415085141174396</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.2745479174106766</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.33579001734902175</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>22.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1800" gml:id="ES.19">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.19</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Schroeioven</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.492</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>22.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.19.POINT">
                            <gml:pos>150585.0 399029.0</gml:pos>
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
                    <imaer:value>1920.0</imaer:value>
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
        <imaer:EmissionSource sectorId="1800" gml:id="ES.20">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.20</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Stoomketel 1 </imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.067</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>10.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.20.POINT">
                            <gml:pos>150536.0 399047.0</gml:pos>
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
                    <imaer:value>768.0</imaer:value>
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
        <imaer:EmissionSource sectorId="1800" gml:id="ES.21">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.21</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Stoomketel 2</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.067</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>10.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.21.POINT">
                            <gml:pos>150536.0 399047.0</gml:pos>
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
                    <imaer:value>768.0</imaer:value>
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
        <imaer:EmissionSource sectorId="1800" gml:id="ES.22">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.22</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Weegbrug</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>1.5</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.22.POINT">
                            <gml:pos>150753.0 398736.0</gml:pos>
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
                    <imaer:value>441.5</imaer:value>
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
        <imaer:EmissionSource sectorId="1800" gml:id="ES.23">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.23</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Rokerij</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.117</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>10.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.23.POINT">
                            <gml:pos>150492.0 398981.0</gml:pos>
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
                    <imaer:value>381.9</imaer:value>
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
        <imaer:EmissionSource sectorId="9999" gml:id="ES.24">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.24</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Ammoniak vanuit stallen </imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>8.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>ANIMAL_HOUSING</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.24.POINT">
                            <gml:pos>150568.0 399071.0</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>84.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1800" gml:id="ES.25">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.25</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Uitbreding rokerij</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.117</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>10.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.25.POINT">
                            <gml:pos>150640.0 398858.0</gml:pos>
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
                    <imaer:value>55.1</imaer:value>
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
