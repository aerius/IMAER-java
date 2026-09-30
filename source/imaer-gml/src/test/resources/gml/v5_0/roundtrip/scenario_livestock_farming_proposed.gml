<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/5.0" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.0 https://imaer.aerius.nl/5.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2022</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Gewenst</imaer:name>
                    <imaer:reference>RtgZJRxWoVRe</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>latest_882b4536b7</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:FarmLodgingEmissionSource sectorId="4110" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Ligboxenstal oud</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:building xlink:href="#Building.1"/>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>7.2</imaer:emissionHeight>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>193709.8 547522.92</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>483.7</imaer:value>
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
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A1.100">
                    <imaer:numberOfAnimals>42</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>Overig</imaer:farmLodgingSystemDefinitionType>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A3.100">
                    <imaer:numberOfAnimals>27</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>Overig</imaer:farmLodgingSystemDefinitionType>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A1.28">
                    <imaer:numberOfAnimals>47</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>BWL2015.05</imaer:farmLodgingSystemDefinitionType>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
        </imaer:FarmLodgingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmLodgingEmissionSource sectorId="4110" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Boerderij</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.0</imaer:emissionHeight>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.POINT">
                            <gml:pos>193685.02 547560.3</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>176.0</imaer:value>
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
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A3.100">
                    <imaer:numberOfAnimals>40</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>Overig</imaer:farmLodgingSystemDefinitionType>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
        </imaer:FarmLodgingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3210" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Tractoren e.d.</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>193641.97 547590.33 193824.46 547541.61 193804.09 547470.63 193828.03 547430.1 193675.78 547477.35 193693.0 547534.05 193606.9 547558.2 193641.97 547590.33</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1.725</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>513.75</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SIV75560DSJ">
                    <imaer:description>John Deer 2016</imaer:description>
                    <imaer:literFuelPerYear>7000</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>950</imaer:operatingHoursPerYear>
                    <imaer:literAdBluePerYear>235</imaer:literAdBluePerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SII75560DSN">
                    <imaer:description>John Deere 2004</imaer:description>
                    <imaer:literFuelPerYear>6000</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>800</imaer:operatingHoursPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="8200" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Woning</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>10.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>CONTINUOUS</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.POINT">
                            <gml:pos>193665.7 547566.18</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.5</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>3.6</imaer:value>
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
            <imaer:label>Verkeersbewegingen</imaer:label>
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
                            <gml:posList>193723.66 547567.02 193636.72 547589.7 193724.08 547669.08 193792.96 547719.9 193873.6 547800.54 193899.22 547830.78 193986.58 547937.46 194086.54 548058.84 194141.14 548115.12 194162.98 548133.6 194080.24 548279.76 194015.14 548390.22 193963.9 548480.1 193916.86 548564.1 193872.76 548640.96 193802.62 548770.74 193761.88 548845.08 193739.62 548911.02 193700.98 548987.46 193690.9 549010.14 193672.0 549063.9 193659.82 549130.26 193652.68 549173.52 193632.52 549211.74 193611.94 549236.94 193582.54 549276.0 193507.78 549371.34</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.8220313559523775</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>21.602289170434418</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.8990670870315435</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>2.7743500061209936</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>10.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="NORMAL_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>240.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>MONTH</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>1138.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:stagnationFactor>0.0</imaer:stagnationFactor>
                    <imaer:maximumSpeed>80</imaer:maximumSpeed>
                </imaer:StandardVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmLodgingEmissionSource sectorId="4110" gml:id="ES.7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Uitbreiding ligboxenstal</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:building xlink:href="#Building.2"/>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>9.1</imaer:emissionHeight>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.7.POINT">
                            <gml:pos>193726.9501592265 547517.5589025837</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>254.2</imaer:value>
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
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A1.28">
                    <imaer:numberOfAnimals>62</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>BWL2015.05</imaer:farmLodgingSystemDefinitionType>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
        </imaer:FarmLodgingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:Building gml:id="Building.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>Building.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Gebouw 1</imaer:label>
            <imaer:height>7.0</imaer:height>
            <imaer:geometry>
                <imaer:BuildingGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="Building.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>193709.74 547551.83 193692.24 547495.59 193713.24 547488.83 193715.81 547499.09 193712.54 547500.26 193725.61 547546.69 193709.74 547551.83</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:BuildingGeometry>
            </imaer:geometry>
        </imaer:Building>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:Building gml:id="Building.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>Building.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Gebouw 2</imaer:label>
            <imaer:height>9.0</imaer:height>
            <imaer:geometry>
                <imaer:BuildingGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="Building.2.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>193723.2891880302 547536.9205870924 193741.02252421185 547532.0205849562 193730.75586466593 547494.4539190016 193712.55586039348 547500.5205785475 193723.2891880302 547536.9205870924</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:BuildingGeometry>
            </imaer:geometry>
        </imaer:Building>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
