<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2023</imaer:year>
                    <imaer:name>Existing enterprise</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Existing enterprise</imaer:name>
                    <imaer:reference>S4R2JDFikdcM</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>BETA2_20130626_d131b24b59</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1400" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Brick factory</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:ADMSSourceCharacteristics>
                    <imaer:building xlink:href="#Building.1"/>
                    <imaer:height>35.0</imaer:height>
                    <imaer:specificHeatCapacity>342.12</imaer:specificHeatCapacity>
                    <imaer:sourceType>POINT</imaer:sourceType>
                    <imaer:diameter>4.3</imaer:diameter>
                    <imaer:buoyancyType>DENSITY</imaer:buoyancyType>
                    <imaer:density>58.23</imaer:density>
                    <imaer:effluxType>VELOCITY</imaer:effluxType>
                    <imaer:verticalVelocity>49.2</imaer:verticalVelocity>
                    <imaer:diurnalVariation>
                        <imaer:StandardDiurnalVariation>
                            <imaer:standardType>SOME_TYPE</imaer:standardType>
                        </imaer:StandardDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:ADMSSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
                            <gml:pos>184080.38 430602.3</gml:pos>
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
                    <imaer:value>20000.0</imaer:value>
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
        <imaer:Building gml:id="Building.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>Building.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Building 1</imaer:label>
            <imaer:height>9.2</imaer:height>
            <imaer:geometry>
                <imaer:BuildingGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="Building.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>184099.3539485522 430576.7455135771 184110.88283492287 430593.2104696673 184061.40605144782 430627.8544864229 184049.87716507714 430611.3895303327 184099.3539485522 430576.7455135771</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:BuildingGeometry>
            </imaer:geometry>
        </imaer:Building>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
