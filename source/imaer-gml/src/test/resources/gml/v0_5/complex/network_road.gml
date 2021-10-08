<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/0.5" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/0.5 http://imaer.aerius.nl/0.5/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2013</imaer:year>
        <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:reference>SomeReference001</imaer:reference>
        <imaer:projectName>Situatie 1</imaer:projectName>
        <imaer:corporation>Big Corp</imaer:corporation>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
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
                        <gml:LineString gml:id="NWR.1_1.CURVE" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>136558 455251 208413 474162</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>29289.848399999995</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>719228.4996</imaer:value>
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
                        <gml:LineString gml:id="NWR.1_2.CURVE" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:posList>136558 455251 208413 474162</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>12204.103500000001</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>432025.26389999996</imaer:value>
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
        <imaer:RoadNetwork gml:id="NW.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>NW.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeNetwork</imaer:label>
            <imaer:element xlink:href="#NWR.1_1"/>
            <imaer:element xlink:href="#NWR.1_2"/>
        </imaer:RoadNetwork>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
