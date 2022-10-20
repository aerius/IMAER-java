<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/5.1" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.1 https://imaer.aerius.nl/5.1/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2030</imaer:year>
                    <imaer:description>RIVM als weiland</imaer:description>
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
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:ManureStorageEmissionSource sectorId="4120" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>Bron 1</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:ADMSSourceCharacteristics>
                    <imaer:height>1.0</imaer:height>
                    <imaer:specificHeatCapacity>342.12</imaer:specificHeatCapacity>
                    <imaer:sourceType>AREA</imaer:sourceType>
                    <imaer:buoyancyType>DENSITY</imaer:buoyancyType>
                    <imaer:density>58.23</imaer:density>
                    <imaer:effluxType>VELOCITY</imaer:effluxType>
                    <imaer:verticalVelocity>49.2</imaer:verticalVelocity>
                </imaer:ADMSSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>141381.58 459298.56 141255.58 458964.24 141762.94 458745.84 141899.02 459088.56 141381.58 459298.56</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>134.5484</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>18.72</imaer:value>
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
            <imaer:manureStorage>
                <imaer:StandardManureStorage manureStorageType="Z1.123">
                    <imaer:tonnes>40.5</imaer:tonnes>
                </imaer:StandardManureStorage>
            </imaer:manureStorage>
            <imaer:manureStorage>
                <imaer:StandardManureStorage manureStorageType="Y2.123">
                    <imaer:metersSquared>2.3</imaer:metersSquared>
                </imaer:StandardManureStorage>
            </imaer:manureStorage>
            <imaer:manureStorage>
                <imaer:StandardManureStorage manureStorageType="X3.123">
                    <imaer:metersSquared>3.1</imaer:metersSquared>
                    <imaer:numberOfDays>200</imaer:numberOfDays>
                </imaer:StandardManureStorage>
            </imaer:manureStorage>
            <imaer:manureStorage>
                <imaer:CustomManureStorage>
                    <imaer:metersSquared>5.2</imaer:metersSquared>
                    <imaer:numberOfDays>300</imaer:numberOfDays>
                    <imaer:description>Eigen mest</imaer:description>
                    <imaer:animalType>MANBEARPIG</imaer:animalType>
                    <imaer:emissionFactorType>PER_METERS_SQUARED_PER_DAY</imaer:emissionFactorType>
                    <imaer:emissionFactor>
                        <imaer:Emission substance="NOX">
                            <imaer:value>0.012</imaer:value>
                        </imaer:Emission>
                    </imaer:emissionFactor>
                </imaer:CustomManureStorage>
            </imaer:manureStorage>
        </imaer:ManureStorageEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
