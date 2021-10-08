<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/1.0" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.0 http://imaer.aerius.nl/1.0/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2020</imaer:year>
        <imaer:version>DEV</imaer:version>
        <imaer:databaseVersion>1.0-SNAPSHOT_20140121_8e4e7f73b5</imaer:databaseVersion>
        <imaer:situationName>1Q#A(2020)</imaer:situationName>
        <imaer:projectName>1Q#A(2020)</imaer:projectName>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:MaritimeShippingEmissionSource movementType="MARITIME" sectorId="7530" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>zeeroute</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.CURVE">
                            <gml:posList>55390.78 447788.88 57648.7 450880.08</gml:posList>
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
                    <imaer:value>6750.824110913948</imaer:value>
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
            <imaer:maritimeShipping>
                <imaer:MaritimeShipping shipType="GC5000">
                    <imaer:description>containerschip</imaer:description>
                    <imaer:shipsPerYear>1000</imaer:shipsPerYear>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
        </imaer:MaritimeShippingEmissionSource>
        <imaer:MaritimeShippingEmissionSource movementType="MARITIME" sectorId="7530" gml:id="ES.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Binnengaats zeeschepen</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.3.CURVE">
                            <gml:posList>69704.38 442601.04 70712.38 441700.56</gml:posList>
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
                    <imaer:value>2272.089668532233</imaer:value>
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
            <imaer:maritimeShipping>
                <imaer:MaritimeShipping shipType="GC3000">
                    <imaer:description>Container</imaer:description>
                    <imaer:shipsPerYear>200</imaer:shipsPerYear>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
            <imaer:maritimeShipping>
                <imaer:MaritimeShipping shipType="KV10000">
                    <imaer:description>Koelschepen</imaer:description>
                    <imaer:shipsPerYear>303</imaer:shipsPerYear>
                </imaer:MaritimeShipping>
            </imaer:maritimeShipping>
        </imaer:MaritimeShippingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
