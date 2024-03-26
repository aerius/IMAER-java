<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/6.0" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2022</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Aangevraagde Situatie</imaer:name>
                    <imaer:reference>Rpjk92sc2GS7</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>latest_882b4536b7</imaer:databaseVersion>
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
            <imaer:label>Zeeschepen</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>84681.77489783871 434366.94328212604</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>62.934</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>40.722</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>37.019999999999996</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>25.914</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:mooringMaritimeShipping>
                <imaer:StandardMooringMaritimeShipping shipType="S10000">
                    <imaer:description>Bulkschip GT 10.000 - 29.999</imaer:description>
                    <imaer:averageResidenceTime>14</imaer:averageResidenceTime>
                    <imaer:shorePowerFactor>0.9</imaer:shorePowerFactor>
                    <imaer:shipsPerTimeUnit>165</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:StandardMooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
            <imaer:mooringMaritimeShipping>
                <imaer:StandardMooringMaritimeShipping shipType="S30000">
                    <imaer:description>Bulkschip GT 30.000 - 59.999</imaer:description>
                    <imaer:averageResidenceTime>14</imaer:averageResidenceTime>
                    <imaer:shorePowerFactor>0.7</imaer:shorePowerFactor>
                    <imaer:shipsPerTimeUnit>825</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:StandardMooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
            <imaer:mooringMaritimeShipping>
                <imaer:StandardMooringMaritimeShipping shipType="S60000">
                    <imaer:description>Bulkschip GT 60.000 - 99.999</imaer:description>
                    <imaer:averageResidenceTime>14</imaer:averageResidenceTime>
                    <imaer:shorePowerFactor>0.0</imaer:shorePowerFactor>
                    <imaer:shipsPerTimeUnit>110</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:StandardMooringMaritimeShipping>
            </imaer:mooringMaritimeShipping>
        </imaer:MooringMaritimeShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:MooringInlandShippingEmissionSource sectorId="7610" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Binnenvaartschepen</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.POINT">
                            <gml:pos>84729.04289536495 434538.1300839778</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
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
            <imaer:mooringInlandShipping>
                <imaer:StandardMooringInlandShipping shipType="M10">
                    <imaer:description>Binnenvaart</imaer:description>
                    <imaer:averageResidenceTime>6</imaer:averageResidenceTime>
                    <imaer:shorePowerFactor>0.0</imaer:shorePowerFactor>
                    <imaer:shipsPerTimeUnit>700</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:percentageLaden>65</imaer:percentageLaden>
                </imaer:StandardMooringInlandShipping>
            </imaer:mooringInlandShipping>
        </imaer:MooringInlandShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="3720" gml:id="ES.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Railverkeer</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>80.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.1</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.1</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>1.0</imaer:emissionHeight>
                    <imaer:spread>2.5</imaer:spread>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.3.CURVE">
                            <gml:posList>83634.21387158167 434154.8760499812 83585.66836060878 434452.53668305185</gml:posList>
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
                    <imaer:value>170.4</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="NON_URBAN_ROAD_NATIONAL" sectorId="3100" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Vrachtwagens (binnen terrein)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.5</imaer:emissionHeight>
                    <imaer:spread>2.5</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.CURVE">
                            <gml:posList>83613.18610584456 434481.45576470427 83648.00441692374 434273.5407071172 83758.42820348914 434294.43169376464 83775.33995458474 434183.0130983113 84578.15072718175 434307.3642093085 84669.67314487559 434600.83283126156 84653.75620266797 434663.5057912041 83621.1445769484 434482.4505735923</gml:posList>
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
                    <imaer:value>78.72687844070799</imaer:value>
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
                <imaer:CustomVehicle>
                    <imaer:vehiclesPerTimeUnit>5000.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:description>Tankwagen</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NO2">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="PM10">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NOX">
                            <imaer:value>5.9</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>A_TO_B</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="9999" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Personenauto's (binnen terrein)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.0</imaer:emissionHeight>
                    <imaer:spread>0.0</imaer:spread>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>CONTINUOUS</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>83764.46054504713 434292.70447645494 83868.49203809634 434311.01632898755 83876.18616282327 434273.3597581828 83771.17197791708 434255.199410417 83764.46054504713 434292.70447645494</gml:posList>
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
                    <imaer:value>0.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="NON_URBAN_ROAD_NATIONAL" sectorId="3100" gml:id="ES.6">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.6</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Vrachtwagen (buiten terrein)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.5</imaer:emissionHeight>
                    <imaer:spread>2.5</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.6.CURVE">
                            <gml:posList>83612.51428163823 434486.4925227259 83612.51428163823 434486.4925227259 83612.24 434486.63 83571.2 434476.17 83554.3 434468.12 83547.86 434455.24 83543.84 434430.3 83648.62 433855.79 83663.4 433801.62 83674.48 433762.22 83723.72 433704.35 83760.66 433674.8 83795.13 433660.03 83823.45 433640.33 83855.46 433610.78 83880.09 433575.08 83892.4 433541.84 83908.4 433481.51 84077.42 432521.7 83941.14 432515.75 83709.67 432511.97 83571.07 432505.03 83441.27 432462.84 83326.62 432417.96 83246.58 432392.0 83116.25 432340.62</gml:posList>
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
                    <imaer:value>1926.4147603777578</imaer:value>
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
                <imaer:CustomVehicle>
                    <imaer:vehiclesPerTimeUnit>140000.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:description>Tankwagens</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NO2">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="PM10">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NOX">
                            <imaer:value>4.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:SRM2Road roadAreaType="NL" roadType="NON_URBAN_ROAD_NATIONAL" sectorId="3100" gml:id="ES.7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Personenauto's (buiten parkeerplaats)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>2.5</imaer:emissionHeight>
                    <imaer:spread>2.5</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.7.CURVE">
                            <gml:posList>83758.70859338525 434290.2672008998 83650.06629493913 434274.1720455744 83612.24267992454 434486.62809586903 83571.2000338449 434476.16624490754 83554.30012075328 434468.1186672449 83547.86205862314 434455.2425429846 83543.83826979181 434430.2950522303 83648.62228683928 433855.78915764636 83663.39658027586 433801.6167483789 83674.4773003533 433762.218632548 83723.72494514192 433704.3526499214 83760.66067873339 433674.8040630482 83795.13403008542 433660.0297696116 83823.45142583888 433640.3307116962 83855.46239495148 433610.78212482296 83880.0862173458 433575.0775823512 83892.39812854295 433541.8354221189 83908.40361309923 433481.5070572529 84077.42358591879 432521.70297129045 83941.13953113573 432515.75406413723 83709.67296190101 432511.9683959488 83571.06757756445 432505.02772073867 83441.27323967582 432462.84456092486 83326.62157454086 432417.95735240506 83246.58173284287 432391.99848482735 83116.24658521304 432340.6215594131</gml:posList>
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
                    <imaer:value>98.29604647625145</imaer:value>
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
                <imaer:CustomVehicle>
                    <imaer:vehiclesPerTimeUnit>94900.0</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                    <imaer:description>Personenauto's</imaer:description>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NO2">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="PM10">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NOX">
                            <imaer:value>0.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomVehicle>
            </imaer:vehicles>
            <imaer:trafficDirection>BOTH</imaer:trafficDirection>
            <imaer:elevation>NORMAL</imaer:elevation>
        </imaer:SRM2Road>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.8">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.8</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Boiler 1</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.5</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.2</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.8.POINT">
                            <gml:pos>83783.92086300373 434476.90929434955</gml:pos>
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
                    <imaer:value>1411.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.9">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.9</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Boiler 2</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.5</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.2</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.9.POINT">
                            <gml:pos>83814.05410954414 434344.0998003381</gml:pos>
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
                    <imaer:value>1411.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.10">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.10</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Boiler 3</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.5</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.2</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.10.POINT">
                            <gml:pos>83786.15295534006 434400.64613952505</gml:pos>
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
                    <imaer:value>1411.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.11">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.11</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Stoomketel</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.5</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.2</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>12.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.11.POINT">
                            <gml:pos>84156.65051082829 434492.5262502024</gml:pos>
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
                    <imaer:value>1411.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3220" gml:id="ES.12">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.12</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Mobiele werktuigen</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.12.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>84659.20176173779 434668.5218948812 84678.87507679478 434600.5595337752 84585.87395107084 434301.88284154644 83654.07421064441 434158.8041865865 83605.78516459544 434471.7887443113 84659.20176173779 434668.5218948812</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>9.1416</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>340.68</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SIIIB5675DSN">
                    <imaer:description>Reachstacker</imaer:description>
                    <imaer:literFuelPerYear>2000</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>4500</imaer:operatingHoursPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SIIIA75560DSN">
                    <imaer:description>Mobiele kraan</imaer:description>
                    <imaer:literFuelPerYear>25090</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>1000</imaer:operatingHoursPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SIIIA5675DSN">
                    <imaer:description>Heftruck</imaer:description>
                    <imaer:literFuelPerYear>7000</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>2000</imaer:operatingHoursPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SII5675DSN">
                    <imaer:description>Terminal trekker</imaer:description>
                    <imaer:literFuelPerYear>4000</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>800</imaer:operatingHoursPerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.13">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.13</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Brandwaterpomp 1</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.4</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.0</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>3.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.13.POINT">
                            <gml:pos>83925.99323477052 434370.04994529305</gml:pos>
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
                    <imaer:value>7.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.14">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.14</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Brandwaterpomp 2</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.4</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.0</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>3.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.14.POINT">
                            <gml:pos>83928.93323477052 434355.34994529304</gml:pos>
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
                    <imaer:value>7.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.15">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.15</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Brandwaterpomp 3</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.4</imaer:outflowDiameter>
                            <imaer:outflowVelocity>0.0</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>3.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.15.POINT">
                            <gml:pos>83930.19323477052 434340.43994529307</gml:pos>
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
                    <imaer:value>7.0</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.16">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.16</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>DVI (20MW)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>1.5</imaer:outflowDiameter>
                            <imaer:outflowVelocity>5.4</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>20.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.16.POINT">
                            <gml:pos>84162.22592430786 434429.3462281225</gml:pos>
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
                    <imaer:value>1254.7</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1300" gml:id="ES.17">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.17</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>DVI (2MW)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:CalculatedHeatContent>
                            <imaer:emissionTemperature>100.0</imaer:emissionTemperature>
                            <imaer:outflowDiameter>0.5</imaer:outflowDiameter>
                            <imaer:outflowVelocity>4.9</imaer:outflowVelocity>
                            <imaer:outflowDirection>VERTICAL</imaer:outflowDirection>
                            <imaer:outflowVelocityType>ACTUAL_FLOW</imaer:outflowVelocityType>
                        </imaer:CalculatedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>20.0</imaer:emissionHeight>
                    <imaer:timeVaryingProfile>
                        <imaer:StandardTimeVaryingProfile>
                            <imaer:standardType>INDUSTRIAL_ACTIVITY</imaer:standardType>
                        </imaer:StandardTimeVaryingProfile>
                    </imaer:timeVaryingProfile>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.17.POINT">
                            <gml:pos>84162.22592430786 434438.2984373088</gml:pos>
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
                    <imaer:value>627.4</imaer:value>
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
        </imaer:EmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:InlandShippingEmissionSource sectorId="7620" gml:id="ES.InlandMooringRoute.0">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.InlandMooringRoute.0</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Binnenvaartschepen; Route 1</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.InlandMooringRoute.0.CURVE">
                            <gml:posList>84730.32822889074 434531.41306207963 84867.95118487923 435006.4950471358</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>41.956</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>27.148</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>24.68</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>17.276</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:inlandShipping>
                <imaer:StandardInlandShipping shipType="M10">
                    <imaer:description>Binnenvaart</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>0</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>700</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>0</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>65</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:StandardInlandShipping>
            </imaer:inlandShipping>
            <imaer:inlandShipping>
                <imaer:StandardInlandShipping shipType="M10">
                    <imaer:description>Binnenvaart</imaer:description>
                    <imaer:numberOfShipsAtoBperTimeUnit>700</imaer:numberOfShipsAtoBperTimeUnit>
                    <imaer:numberOfShipsBtoAperTimeUnit>0</imaer:numberOfShipsBtoAperTimeUnit>
                    <imaer:percentageLadenAtoB>65</imaer:percentageLadenAtoB>
                    <imaer:percentageLadenBtoA>0</imaer:percentageLadenBtoA>
                    <imaer:timeUnitShipsAtoB>YEAR</imaer:timeUnitShipsAtoB>
                    <imaer:timeUnitShipsBtoA>YEAR</imaer:timeUnitShipsBtoA>
                </imaer:StandardInlandShipping>
            </imaer:inlandShipping>
            <imaer:waterway>
                <imaer:InlandWaterway>
                    <imaer:type>CEMT_Va</imaer:type>
                </imaer:InlandWaterway>
            </imaer:waterway>
            <imaer:mooringA xlink:href="#ES.2"/>
        </imaer:InlandShippingEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:MaritimeShippingEmissionSource movementType="INLAND" sectorId="7520" gml:id="ES.MaritimeInlandMooringRoute.0">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.MaritimeInlandMooringRoute.0</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Zeeschepen; Route 1</imaer:label>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.MaritimeInlandMooringRoute.0.CURVE">
                            <gml:posList>84683.19707957961 434365.5114165045 84965.98397544638 434987.64258741133</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>62.934</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>40.722</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>37.019999999999996</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>25.914</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:maritimeShipping>
                <imaer:StandardMaritimeShipping shipType="S10000">
                    <imaer:description>Bulkschip GT 10.000 - 29.999</imaer:description>
                    <imaer:shipsPerTimeUnit>330</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:StandardMaritimeShipping>
            </imaer:maritimeShipping>
            <imaer:maritimeShipping>
                <imaer:StandardMaritimeShipping shipType="S30000">
                    <imaer:description>Bulkschip GT 30.000 - 59.999</imaer:description>
                    <imaer:shipsPerTimeUnit>1650</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:StandardMaritimeShipping>
            </imaer:maritimeShipping>
            <imaer:maritimeShipping>
                <imaer:StandardMaritimeShipping shipType="S60000">
                    <imaer:description>Bulkschip GT 60.000 - 99.999</imaer:description>
                    <imaer:shipsPerTimeUnit>220</imaer:shipsPerTimeUnit>
                    <imaer:timeUnit>YEAR</imaer:timeUnit>
                </imaer:StandardMaritimeShipping>
            </imaer:maritimeShipping>
            <imaer:mooringA xlink:href="#ES.1"/>
        </imaer:MaritimeShippingEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
