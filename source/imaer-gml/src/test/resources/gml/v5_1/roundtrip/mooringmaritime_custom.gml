<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2020</imaer:year>
                    <imaer:name>Situatie 1</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>Raz1yvtxZgT4</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>BETA8_20140904_7fbba21b46</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:MooringMaritimeShippingEmissionSource sectorId="7510" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Zeescheepvaart haven</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.CURVE">
                            <gml:posList>68320.06 443682.96 68924.86 443286.48</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>20.978</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>13.574</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>12.34</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>8.638</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:mooringMaritimeShipping>
                <imaer:CustomMooringMaritimeShipping>
                    <imaer:description>Olie tankers</imaer:description>
                    <imaer:averageResidenceTime>22</imaer:averageResidenceTime>
                    <imaer:shorePowerFactor>0.2</imaer:shorePowerFactor>
                    <imaer:shipsPerTimeUnit>300</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:emissionProperties>
                        <imaer:CustomMaritimeShippingEmissionProperties>
                            <imaer:emissionFactor>
<imaer:Emission substance="NOX">
    <imaer:value>1.3</imaer:value>
</imaer:Emission>
                            </imaer:emissionFactor>
                            <imaer:heatContent>0.4</imaer:heatContent>
                            <imaer:emissionHeight>2.5</imaer:emissionHeight>
                        </imaer:CustomMaritimeShippingEmissionProperties>
                    </imaer:emissionProperties>
                </imaer:CustomMooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
        </imaer:MooringMaritimeShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
