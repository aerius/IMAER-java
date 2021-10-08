<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/1.1" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.1 http://imaer.aerius.nl/1.1/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2013</imaer:year>
        <imaer:version>V1.1</imaer:version>
        <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:reference>SomeReference001</imaer:reference>
        <imaer:projectName>Situatie 1</imaer:projectName>
        <imaer:corporation>Big Corp</imaer:corporation>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
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
                    <imaer:heatContent>564.584</imaer:heatContent>
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
                    <imaer:value>1816440.7917975</imaer:value>
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
                <imaer:StandardFarmLodging farmLodgingType="D3.1">
                    <imaer:numberOfAnimals>3</imaer:numberOfAnimals>
                    <imaer:farmLodgingSystemDefinitionType>BWL2001.21.V1</imaer:farmLodgingSystemDefinitionType>
                    <imaer:fodderMeasure>
                        <imaer:LodgingFodderMeasure>
                            <imaer:fodderMeasureType>PAS2015.01-01</imaer:fodderMeasureType>
                        </imaer:LodgingFodderMeasure>
                    </imaer:fodderMeasure>
                    <imaer:fodderMeasure>
                        <imaer:LodgingFodderMeasure>
                            <imaer:fodderMeasureType>PAS2015.05-01</imaer:fodderMeasureType>
                        </imaer:LodgingFodderMeasure>
                    </imaer:fodderMeasure>
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
                            <imaer:lodgingSystemType>G2.1.2</imaer:lodgingSystemType>
                        </imaer:ReductiveLodgingSystem>
                    </imaer:lodgingSystem>
                    <imaer:lodgingSystem>
                        <imaer:ReductiveLodgingSystem>
                            <imaer:lodgingSystemType>A4.3</imaer:lodgingSystemType>
                        </imaer:ReductiveLodgingSystem>
                    </imaer:lodgingSystem>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
            <imaer:farmLodging>
                <imaer:StandardFarmLodging farmLodgingType="A3.100">
                    <imaer:numberOfAnimals>100</imaer:numberOfAnimals>
                </imaer:StandardFarmLodging>
            </imaer:farmLodging>
            <imaer:farmLodging>
                <imaer:CustomFarmLodging>
                    <imaer:numberOfAnimals>908</imaer:numberOfAnimals>
                    <imaer:description>Schaap</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>2000.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomFarmLodging>
            </imaer:farmLodging>
        </imaer:FarmLodgingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
