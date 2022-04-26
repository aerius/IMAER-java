<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/4.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/4.0 https://imaer.aerius.nl/4.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2013</imaer:year>
                    <imaer:name>Bestaande onderneming</imaer:name>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Bestaande onderneming</imaer:name>
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
            <imaer:label>Steenfabriek</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.22</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>35.0</imaer:emissionHeight>
                    <imaer:diurnalVariation>
                        <imaer:ReferenceDiurnalVariation>
                            <imaer:customDiurnalVariation xlink:href="#Custom_DV.1"/>
                        </imaer:ReferenceDiurnalVariation>
                    </imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
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
    <imaer:definitions>
        <imaer:Definitions>
            <imaer:customDiurnalVariation>
                <imaer:CustomDiurnalVariation gml:id="Custom_DV.1">
                    <imaer:label>Voorbeeld eigen spec</imaer:label>
                    <imaer:customType>DAY</imaer:customType>
                    <imaer:value>7000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>13000</imaer:value>
                    <imaer:value>12000</imaer:value>
                    <imaer:value>12000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>10000</imaer:value>
                    <imaer:value>6000</imaer:value>
                </imaer:CustomDiurnalVariation>
            </imaer:customDiurnalVariation>
        </imaer:Definitions>
    </imaer:definitions>
</imaer:FeatureCollectionCalculator>
