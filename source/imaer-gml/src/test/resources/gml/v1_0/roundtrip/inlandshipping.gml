<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/1.0" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.0 http://imaer.aerius.nl/1.0/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2014</imaer:year>
        <imaer:version>DEV</imaer:version>
        <imaer:databaseVersion>BETA8_20140904_7fbba21b46</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:projectName>Situatie 1</imaer:projectName>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:InlandShippingEmissionSource sectorId="7620" gml:id="ES.14">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.14</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Binnenvaart vaarroute</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.14.CURVE">
                            <gml:posList>68709.82 443098.32 69176.86 442742.16</gml:posList>
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
                    <imaer:value>4154.9971698798445</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>160.5267419947643</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:inlandShipping>
                <imaer:InlandShipping shipType="C3B">
                    <imaer:description>Koppelverband</imaer:description>
                    <imaer:numberOfShipsAtoB>20</imaer:numberOfShipsAtoB>
                    <imaer:numberOfShipsBtoA>22</imaer:numberOfShipsBtoA>
                    <imaer:percentageLadenAtoB>45</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>35</imaer:percentageLadenBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
