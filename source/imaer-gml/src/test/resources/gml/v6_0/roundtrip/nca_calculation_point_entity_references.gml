<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/6.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2035</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
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
        <imaer:NcaCustomCalculationPoint gml:id="CP.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.1.POINT">
                    <gml:pos>207413.0 475162.0</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:label>Generic critical level entity</imaer:label>
            <imaer:height>3.1</imaer:height>
            <imaer:assessmentCategory>ECOLOGY</imaer:assessmentCategory>
            <imaer:roadLocalFractionNO2>0.432</imaer:roadLocalFractionNO2>
            <imaer:entityReference>
                <imaer:EntityReference>
                    <imaer:entityType>CRITICAL_LEVEL_ENTITY</imaer:entityType>
                    <imaer:description>Some unknown species</imaer:description>
                    <imaer:criticalLevel>
                        <imaer:CriticalLevel resultType="CONCENTRATION" substance="NH3">
                            <imaer:value>30.0</imaer:value>
                        </imaer:CriticalLevel>
                    </imaer:criticalLevel>
                    <imaer:criticalLevel>
                        <imaer:CriticalLevel resultType="DEPOSITION" substance="NOXNH3">
                            <imaer:value>900.0</imaer:value>
                        </imaer:CriticalLevel>
                    </imaer:criticalLevel>
                </imaer:EntityReference>
            </imaer:entityReference>
            <imaer:entityReference>
                <imaer:EntityReference>
                    <imaer:entityType>HABITAT_TYPE</imaer:entityType>
                    <imaer:code>H10100101</imaer:code>
                    <imaer:description>Some known habitat type</imaer:description>
                </imaer:EntityReference>
            </imaer:entityReference>
        </imaer:NcaCustomCalculationPoint>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
