<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/3.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/3.1 http://imaer.aerius.nl/3.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:name>Road sources</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Road sources</imaer:name>
                    <imaer:reference>Rt3YbMBG7qMW</imaer:reference>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>BETA2_20130626_d131b24b59</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:SRM2Road sectorId="3111" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeRoadSource</imaer:label>
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
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.CURVE">
                            <gml:posList>136558.0 455251.0 208413.0 474162.0</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>585035.2680296783</imaer:value>
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
                    <imaer:emission>
                        <imaer:Emission substance="NO2">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emission>
                        <imaer:Emission substance="NOX">
                            <imaer:value>22.12</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:CustomVehicle>
            </imaer:vehicles>
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
</imaer:FeatureCollectionCalculator>
