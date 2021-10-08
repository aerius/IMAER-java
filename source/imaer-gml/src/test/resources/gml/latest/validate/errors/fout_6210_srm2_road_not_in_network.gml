<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/2.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/2.0 http://imaer.aerius.nl/2.0/IMAER.xsd">
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
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>V1.1</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:SRM2Road sectorId="3112" gml:id="NWR.1_1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>NWR.1_1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeOnRoadMobileSource</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>0.28</imaer:heatContent>
                    <imaer:emissionHeight>22.0</imaer:emissionHeight>
                    <imaer:spread>11.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NWR.1_1.CURVE">
                            <gml:posList>136558.0 455251.0 208413.0 474162.0</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>20340.13518180811</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>148306.84125652479</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>33441.68000749287</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:SpecificVehicle vehicleType="BA-B-E3">
                    <imaer:vehiclesPerDay>30000.0</imaer:vehiclesPerDay>
                </imaer:SpecificVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:SpecificVehicle vehicleType="BA-D-E6-ZW">
                    <imaer:vehiclesPerDay>15000.0</imaer:vehiclesPerDay>
                </imaer:SpecificVehicle>
            </imaer:vehicles>
            <imaer:inNetwork xlink:href="#NW.1"/>
            <imaer:isFreeway>false</imaer:isFreeway>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road sectorId="3113" gml:id="NWR.1_2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>NWR.1_2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeOnRoadMobileSource</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>0.28</imaer:heatContent>
                    <imaer:emissionHeight>22.0</imaer:emissionHeight>
                    <imaer:spread>11.0</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="NWR.1_2.CURVE">
                            <gml:posList>136558.0 455251.0 208413.0 474162.0</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>10576.870294540218</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>235045.72052853095</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>43533.665887461</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:vehicles>
                <imaer:SpecificVehicle vehicleType="BA-B-E3">
                    <imaer:vehiclesPerDay>30000.0</imaer:vehiclesPerDay>
                </imaer:SpecificVehicle>
            </imaer:vehicles>
            <imaer:vehicles>
                <imaer:SpecificVehicle vehicleType="BA-D-E6-ZW">
                    <imaer:vehiclesPerDay>15000.0</imaer:vehiclesPerDay>
                </imaer:SpecificVehicle>
            </imaer:vehicles>
            <imaer:inNetwork xlink:href="#NW.1"/>
            <imaer:isFreeway>false</imaer:isFreeway>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:RoadNetwork gml:id="NW.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>NW.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeNetwork</imaer:label>
            <imaer:element xlink:href="#NWR.2_1"/>
            <imaer:element xlink:href="#NWR.2_2"/>
        </imaer:RoadNetwork>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
