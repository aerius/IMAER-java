<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:description>RIVM als weiland</imaer:description>
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
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Bron 1</imaer:label>
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
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>141381.58 459298.56 141255.58 458964.24 141762.94 458745.84 141899.02 459088.56 141381.58 459298.56</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>18792.8832</imaer:value>
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
                <imaer:StandardFarmlandActivity standardActivityType="A1.123" activityType="PASTURE">
                    <imaer:numberOfAnimals>500</imaer:numberOfAnimals>
                </imaer:StandardFarmlandActivity>
            </imaer:activity>
            <imaer:activity>
                <imaer:StandardFarmlandActivity standardActivityType="B2.123" activityType="PASTURE">
                    <imaer:numberOfAnimals>900</imaer:numberOfAnimals>
                    <imaer:numberOfDays>200</imaer:numberOfDays>
                </imaer:StandardFarmlandActivity>
            </imaer:activity>
            <imaer:activity>
                <imaer:StandardFarmlandActivity standardActivityType="C3.123" activityType="MANURE">
                    <imaer:tonnes>50.6</imaer:tonnes>
                    <imaer:numberOfApplications>2</imaer:numberOfApplications>
                </imaer:StandardFarmlandActivity>
            </imaer:activity>
            <imaer:activity>
                <imaer:StandardFarmlandActivity standardActivityType="D4.123" activityType="MANURE">
                    <imaer:metersCubed>33.2</imaer:metersCubed>
                    <imaer:numberOfApplications>3</imaer:numberOfApplications>
                </imaer:StandardFarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
