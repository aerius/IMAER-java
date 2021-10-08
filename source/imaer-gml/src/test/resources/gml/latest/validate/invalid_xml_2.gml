<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:imaer="http://imaer.aerius.nl/0.5" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/0.5 http://imaer.aerius.nl/0.5/IMAER.xsd">
    <imaer:aeriusCalculatorMetaData>
        <imaer:year>2014</imaer:year>
        <imaer:version>1.0-SNAPSHOT_20141216_32a586bec1</imaer:version>
        <imaer:databaseVersion>1.0-SNAPSHOT_20141216_32a586bec1</imaer:databaseVersion>
        <imaer:situationName>Situation 1</imaer:situationName>
    </imaer:aeriusCalculatorMetaData>
    <imaer:featureMembers>
        <imaer:EmissionSource sectorId="8640" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    </imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>AERIUS Office</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>0.014</imaer:heatContent>
                    <imaer:emissionHeight>11.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>INDUSTRIAL_ACTIVITY</imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Point>
                        <gml:Point gml:id="ES.1.POINT" srsName="urn:ogc:def:crs:EPSG::28992">
                            <gml:pos>135650.84 455604.05</gml:pos>
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
                    <imaer:value>100.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="PM10">
                    <imaer:value>300.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
            <imaer:emission>
                <imaer:Emission substance="NO2">
                    <imaer:value>0.0</imaer:value>
                </imaer:Emission>
            </imaer:emission>
        </imaer:EmissionSource>
    </imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
