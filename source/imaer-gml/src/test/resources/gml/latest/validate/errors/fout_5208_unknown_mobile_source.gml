<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/1.1" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.1 http://imaer.aerius.nl/1.1/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2014</imaer:year>
        <imaer:version>1.0-SNAPSHOT_20151118_d4e7301d0f</imaer:version>
        <imaer:databaseVersion>1.0-SNAPSHOT_20151118_d4e7301d0f</imaer:databaseVersion>
        <imaer:situationName>Situatie §</imaer:situationName>
        <imaer:reference>RhRtvaid45xN</imaer:reference>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3130" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Bron 1</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>107087.74 448595.28</gml:pos>
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
                    <imaer:value>553.778</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="Z1980">
                    <imaer:description>KaasMijnGraver</imaer:description>
                    <imaer:literFuelPerYear>10000</imaer:literFuelPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
