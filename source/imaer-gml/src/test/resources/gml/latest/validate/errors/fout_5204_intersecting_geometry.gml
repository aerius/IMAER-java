<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/1.1" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.1 http://imaer.aerius.nl/1.1/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2014</imaer:year>
        <imaer:version>1.0-SNAPSHOT_20151117_997900d846</imaer:version>
        <imaer:databaseVersion>1.0-SNAPSHOT_20151117_760713d2ae</imaer:databaseVersion>
        <imaer:situationName>Situatie §</imaer:situationName>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:PlanEmissionSource sectorId="9000" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>HexaPlan</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
	<!-- test with intersecting geometry -->
	<!--  1 2 -->			
	<!-- 3   6 -->
	<!--  5 4 -->
    <gml:posList>101053.18000000002 449287.4400000002 
	105031.41999999997 449179.92 
	98902.78000000001 445094.16 
	105031.89999999998 441115.91999999987 
	101160.69999999998 441223.44000000006 
	107181.81999999998 445094.1599999998 
	101053.18000000002 449287.4400000002</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>2.0250000000000004</imaer:value>
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
            <imaer:plan>
                <imaer:Plan planType="PIA">
                    <imaer:description>Hexagonaal Megaplan</imaer:description>
                    <imaer:amount>3</imaer:amount>
                </imaer:Plan>
            </imaer:plan>
        </imaer:PlanEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
