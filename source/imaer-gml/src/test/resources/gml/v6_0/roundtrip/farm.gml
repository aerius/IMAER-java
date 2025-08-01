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
                    <imaer:reference>RpXpU4DVH2Nw</imaer:reference>
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
        <imaer:FarmAnimalHousingSource sectorId="4110" gml:id="ES.6">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.6</imaer:localId>
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
                    <imaer:emissionHeight>5.0</imaer:emissionHeight>
                    <imaer:spread>0.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.6.POINT">
                            <gml:pos>69462.46 443548.56</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1999.0</imaer:value>
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
            <imaer:animalHousing>
                <imaer:StandardFarmAnimalHousing animalHousingType="HA1.1" animalType="HA1">
                    <imaer:numberOfAnimals>100</imaer:numberOfAnimals>
                    <imaer:additionalSystem>
                        <imaer:StandardAdditionalHousingSystem additionalSystemType="LW1.1"/>
                    </imaer:additionalSystem>
                </imaer:StandardFarmAnimalHousing>
            </imaer:animalHousing>
            <imaer:animalHousing>
                <imaer:StandardFarmAnimalHousing animalHousingType="HB1.100" animalType="HB1">
                    <imaer:numberOfAnimals>300</imaer:numberOfAnimals>
                </imaer:StandardFarmAnimalHousing>
            </imaer:animalHousing>
            <imaer:animalHousing>
                <imaer:StandardFarmAnimalHousing animalHousingType="HC1.100" animalType="HC1">
                    <imaer:numberOfAnimals>10</imaer:numberOfAnimals>
                </imaer:StandardFarmAnimalHousing>
            </imaer:animalHousing>
            <imaer:animalHousing>
                <imaer:StandardFarmAnimalHousing animalHousingType="HD3.8.2" animalType="HD3">
                    <imaer:numberOfAnimals>200</imaer:numberOfAnimals>
                </imaer:StandardFarmAnimalHousing>
            </imaer:animalHousing>
            <imaer:animalHousing>
                <imaer:CustomFarmAnimalHousing animalType="">
                    <imaer:numberOfAnimals>1000</imaer:numberOfAnimals>
                    <imaer:description>Koeien, extra luchtwasser</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactorType>PER_ANIMAL_PER_YEAR</imaer:emissionFactorType>
                </imaer:CustomFarmAnimalHousing>
            </imaer:animalHousing>
            <imaer:animalHousing>
                <imaer:StandardFarmAnimalHousing animalHousingType="HF1.4" animalType="HF1">
                    <imaer:numberOfAnimals>2000</imaer:numberOfAnimals>
                </imaer:StandardFarmAnimalHousing>
            </imaer:animalHousing>
        </imaer:FarmAnimalHousingSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
