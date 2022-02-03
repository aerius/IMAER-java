<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.0 https://imaer.aerius.nl/5.0/IMAER.xsd">
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
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
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
            <imaer:label>SomeSource</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>564.584</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>11.12</imaer:emissionHeight>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>136558.0 455251.0</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>2256.30015</imaer:value>
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
                <imaer:StandardFarmLodging farmLodgingType="A4.2">
                    <imaer:numberOfAnimals>3</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>BWL2011.12</imaer:farmLodgingSystemDefinitionType>
                    <imaer:lodgingSystem>
                        <imaer:AdditionalLodgingSystem>
                            <imaer:lodgingSystemType>E6.1.a</imaer:lodgingSystemType>
                            <imaer:numberOfAnimals>2</imaer:numberOfAnimals>
                        </imaer:AdditionalLodgingSystem>
                    </imaer:lodgingSystem>
                    <imaer:lodgingSystem>
                        <imaer:AdditionalLodgingSystem>
                            <imaer:lodgingSystemType>E6.5.b</imaer:lodgingSystemType>
                            <imaer:numberOfAnimals>1</imaer:numberOfAnimals>
                        </imaer:AdditionalLodgingSystem>
                    </imaer:lodgingSystem>
                    <imaer:lodgingSystem>
                        <imaer:ReductiveLodgingSystem>
                            <imaer:lodgingSystemType>A4.3</imaer:lodgingSystemType>
                        </imaer:ReductiveLodgingSystem>
                    </imaer:lodgingSystem>
                    <imaer:lodgingSystem>
                        <imaer:ReductiveLodgingSystem>
                            <imaer:lodgingSystemType>G2.1.2</imaer:lodgingSystemType>
                        </imaer:ReductiveLodgingSystem>
                    </imaer:lodgingSystem>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
            <imaer:farmLodging>
                <imaer:CustomFarmLodging>
                    <imaer:numberOfAnimals>908</imaer:numberOfAnimals>
                    <imaer:description>Schaap</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>2.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomFarmLodging>
            </imaer:farmLodging>
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A3.100">
                    <imaer:numberOfAnimals>100</imaer:numberOfAnimals>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
        </imaer:FarmLodgingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
