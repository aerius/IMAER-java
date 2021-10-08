<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/1.1" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/1.1 http://imaer.aerius.nl/1.1/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2013</imaer:year>
        <imaer:version>V1.1</imaer:version>
        <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
        <imaer:situationName>Situatie 1</imaer:situationName>
        <imaer:reference>SomeReference001</imaer:reference>
        <imaer:projectName>Situatie 1</imaer:projectName>
        <imaer:corporation>Big Corp</imaer:corporation>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:InlandShippingEmissionSource sectorId="1800" gml:id="ES.8">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.8</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>SomeInlandShipSource</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.8.CURVE">
                            <gml:posList>208413.0 455251.0 208513.0 455151.0</gml:posList>
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
                    <imaer:value>16324.082417331447</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>503.23704106652923</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:inlandShipping>
                <imaer:InlandShipping shipType="BI">
                    <imaer:description>Duikboot</imaer:description>
                    <imaer:numberOfShipsAtoB>50</imaer:numberOfShipsAtoB>
                    <imaer:numberOfShipsBtoA>150</imaer:numberOfShipsBtoA>
                    <imaer:percentageLadenAtoB>20</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>40</imaer:percentageLadenBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
            <imaer:inlandShipping>
                <imaer:InlandShipping shipType="BII-1">
                    <imaer:description>Veerpont</imaer:description>
                    <imaer:numberOfShipsAtoB>300</imaer:numberOfShipsAtoB>
                    <imaer:numberOfShipsBtoA>280</imaer:numberOfShipsBtoA>
                    <imaer:percentageLadenAtoB>80</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>82</imaer:percentageLadenBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
