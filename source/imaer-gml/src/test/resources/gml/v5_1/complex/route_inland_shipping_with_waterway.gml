<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
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
                    <imaer:situationType>PROPOSED</imaer:situationType>
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
                    <imaer:value>17820.511234633548</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>549.369410227202</imaer:value>
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
                    <imaer:numberOfShipsAtoBperTimeUnit>50</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>150</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>20</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>40</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
            <imaer:inlandShipping>
                <imaer:InlandShipping shipType="BII-1">
                    <imaer:description>Veerpont</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>300</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>280</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>80</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>82</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:InlandShipping>
            </imaer:inlandShipping>
            <imaer:waterway>
                <imaer:InlandWaterway>
                    <imaer:type>Lek</imaer:type>
                    <imaer:direction>UPSTREAM</imaer:direction>
                </imaer:InlandWaterway>
            </imaer:waterway>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
