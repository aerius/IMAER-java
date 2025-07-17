<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/5.1" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2023</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Referentiesituatie</imaer:name>
                    <imaer:reference>RdgSJAYCQGxj</imaer:reference>
                    <imaer:situationType>REFERENCE</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>latest_813d084939</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="4320" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Ketel 1</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>11.85</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.6</imaer:outflowDiameter>
                            <imaer:outflowVelocity>12.0</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>9.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>SPACE_HEATING_WITHOUT_SEASONAL_CORRECTION</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>188631.9625749053 517351.05015197414</gml:pos>
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
                    <imaer:value>834.3</imaer:value>
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
        <imaer:EmissionSource sectorId="4320" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>WKK 1</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.015</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>SPACE_HEATING_WITHOUT_SEASONAL_CORRECTION</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.POINT">
                            <gml:pos>188649.88924743506 517356.3227027182</gml:pos>
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
                    <imaer:value>735.6</imaer:value>
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
        <imaer:SRM2Road roadAreaType="NL" roadType="NON_URBAN_ROAD_NATIONAL" sectorId="3100" gml:id="ES.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Verkeer 1</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.5</imaer:emissionHeight>
                    <imaer:spread>0.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.3.CURVE">
                            <gml:posList>188654.74175582462 517326.51025299996 188577.57692872413 517289.47113599174 188515.84506704373 517456.1471625288 188383.12156443088 517769.4363605568 188125.71058729728 518408.94578934467 187921.99544375198 518725.32158045686</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.314018784063288</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>6.590495124587866</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.27429425026249954</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.6934124527404503</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="AUTO_BUS">
                    <imaer:vehiclesPerTimeUnit>0.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.02</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>8.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.02</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="NORMAL_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.02</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="4320" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Ketel 2</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.013</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>9.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>SPACE_HEATING_WITHOUT_SEASONAL_CORRECTION</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.POINT">
                            <gml:pos>188776.43046529207 517747.5459679261</gml:pos>
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
                    <imaer:value>1144.3</imaer:value>
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
        <imaer:EmissionSource sectorId="4320" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>WKK 2</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.016</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>SPACE_HEATING_WITHOUT_SEASONAL_CORRECTION</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.POINT">
                            <gml:pos>188786.97556678017 517732.7828258428</gml:pos>
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
                    <imaer:value>1008.9</imaer:value>
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
        <imaer:SRM2Road roadAreaType="NL" roadType="NON_URBAN_ROAD_NATIONAL" sectorId="3100" gml:id="ES.6">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.6</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Verkeer 2</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.5</imaer:emissionHeight>
                    <imaer:spread>0.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.6.CURVE">
                            <gml:posList>188739.92536052677 517728.5590202041 188704.42954006055 517814.9836265567 188412.74649362065 517696.14979282196 188125.69333680684 518412.23938831437 187921.97819326154 518723.98528980033</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.2920159957750813</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>6.128709791034255</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.2550748958050866</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.6448261637400466</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="AUTO_BUS">
                    <imaer:vehiclesPerTimeUnit>0.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.02</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>8.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.02</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="NORMAL_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>2.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.02</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Bemesting</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.7.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>188565.52843553037 517592.53297605127 188852.35519600628 517703.2565416762 189037.9489821965 517223.4544239684 188760.61281305988 517104.29477715306 188565.52843553037 517592.53297605127</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1642.0</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="MANURE">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>1642.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
