<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/6.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:name>Cold start sources</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Cold start sources</imaer:name>
                    <imaer:reference>ABCD1234</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>DEV_DB</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:ColdStartEmissionSource sectorId="3150" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeColdStartSource</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>20.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>3.7</imaer:emissionHeight>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>69462.46 443548.56</gml:pos>
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
                    <imaer:value>7455.0764</imaer:value>
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
            <imaer:vehicles>
                <imaer:ColdStartStandardVehicle vehicleType="HEAVY_FREIGHT">
                    <imaer:vehiclesPerTimeUnit>10.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                </imaer:ColdStartStandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:ColdStartStandardVehicle vehicleType="LIGHT_TRAFFIC">
                    <imaer:vehiclesPerTimeUnit>100.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                </imaer:ColdStartStandardVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:SpecificVehicle vehicleType="BA-B-E3">
                    <imaer:vehiclesPerTimeUnit>300.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>DAY</imaer:timeUnit>
                </imaer:SpecificVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:SpecificVehicle vehicleType="BA-D-E6-ZW">
                    <imaer:vehiclesPerTimeUnit>15000.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>MONTH</imaer:timeUnit>
                </imaer:SpecificVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:CustomVehicle>
                    <imaer:vehiclesPerTimeUnit>22.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>HOUR</imaer:timeUnit>
                    <imaer:description>Mijn omschrijving</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NO2">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NOX">
                            <imaer:value>22.12</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomVehicle>
            </imaer:vehicles>
        </imaer:ColdStartEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
