<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns:imaer="http://imaer.aerius.nl/6.0"
    xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2025</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>Rk2YW94YXNCA</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>1.0-SNAPSHOT_20130819_3cd3c407ef</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:OffRoadMobileSourceEmissionSource sectorId="3210" gml:id="ES.1">
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
                            <gml:pos>112207.04000000004 490310.08</gml:pos>
                        </gml:Point>
                    </imaer:GM_Point>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>2.072</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NOX">
                    <imaer:value>8.05</imaer:value>
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
                <imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SV560DSJ">
                    <imaer:description>Standaard werktuig</imaer:description>
                    <imaer:literFuelPerYear>300</imaer:literFuelPerYear>
                    <imaer:operatingHoursPerYear>2</imaer:operatingHoursPerYear>
                    <imaer:literAdBluePerYear>1</imaer:literAdBluePerYear>
                </imaer:StandardOffRoadMobileSource>
            </imaer:offRoadMobileSource>
            <imaer:offRoadMobileSource>
                <imaer:CustomOffRoadMobileSource>
                    <imaer:description>Custom werktuig</imaer:description>
                    <imaer:emission>
                        <imaer:Emission substance="NOX">
                            <imaer:value>1.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>2.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emissionSourceCharacteristics>
                        <imaer:EmissionSourceCharacteristics>
                            <imaer:heatContent>
<imaer:SpecifiedHeatContent>
    <imaer:value>4.0</imaer:value>
</imaer:SpecifiedHeatContent>
                            </imaer:heatContent>
                            <imaer:emissionHeight>3.0</imaer:emissionHeight>
                            <imaer:spread>5.0</imaer:spread>
                        </imaer:EmissionSourceCharacteristics>
                    </imaer:emissionSourceCharacteristics>
                </imaer:CustomOffRoadMobileSource>
            </imaer:offRoadMobileSource>
        </imaer:OffRoadMobileSourceEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
