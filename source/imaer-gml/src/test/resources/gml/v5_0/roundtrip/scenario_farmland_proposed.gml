<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/5.0" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/5.0 https://imaer.aerius.nl/5.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2022</imaer:year>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Landbouwgronden</imaer:name>
                    <imaer:reference>RXVrPgyULfKj</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>latest_2029ce1361</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>1071, 1075, 1066, 1072, 1077, 319, 1064 - grasland blijvend en zomertarwe</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>133380.15999999002 411796.56000001 133369.23999999 411900.30000000994 133309.9149999899 411904.08000001 133268.22999999 411907.86000001 133197.24999999002 411904.5000000101 133018.95999999007 411887.07000000984 133032.60999999 411754.56000001 133123.32999999 411717.18000001 133269.48999999 411748.26000001 133340.88999999006 411776.1900000101 133359.36999999004 411781.54500001 133380.15999999002 411796.56000001</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>236.3</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>236.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>209 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>133378.47999998007 411865.02000002 133395.594999985 411888.0150000202 133419.84999999017 411876.15000002057 133430.34999999 411790.89000002 133384.98999999007 411797.82000002015 133378.47999998007 411865.02000002</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.3">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.3</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>672, 673 - grasland (blijvend)</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.3.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>133420.18653674 411875.1455575 133485.49653673996 411852.88555750006 133556.26653673997 411835.66555750015 133556.26653673997 411783.1655575 133556.05653674 411777.0755574999 133533.16653673997 411778.1255575 133501.45653673998 411781.90555750014 133466.80653673998 411786.5255574999 133430.68653674002 411791.35555749986 133420.18653674 411875.1455575</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>43.0</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>43.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.4">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.4</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>205, 204, 202, 201, 200 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.4.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>133556.98000000007 411835.41000003996 133618.615000005 411833.52000004007 133667.65000000992 411836.4600000397 133778.94999999998 411851.16000004055 133776.43000000998 411799.5000000401 133711.12000001007 411788.79000004 133663.0300000101 411782.49000004 133613.89000001 411778.60500004 133555.93000001015 411776.82000004 133556.98000000007 411835.41000003996</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>30.7</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>30.7</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.5">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.5</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>199, 699, 698, 197, 195, 194 - grasland, blijvend en maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.5.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>133779.58000000994 411850.95000004995 134072.1100000099 411904.5000000505 134069.5900000098 411852.42000005033 133777.27000001 411799.7100000499 133779.58000000994 411850.95000004995</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>24.6</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>24.6</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.6">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.6</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>191, 192 - zomergerst</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.6.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134072.74000000002 411904.49999999977 134135.53 411914.79 134134.9 411861.66 134071.27 411852.0 134070.85 411852.42 134072.74000000002 411904.49999999977</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>3.3</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>3.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.7">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.7</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>190, 189, 188, 187, 379, 378 - maïs, zomertarwe en zomergerst</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.7.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134136.3700000101 411914.7900000502 134325.68500001 411950.80500005005 134508.28000000998 411990.18000005 134511.64000000997 411933.4800000499 134134.90000000995 411861.2400000499 134136.3700000101 411914.7900000502</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>33.3</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NOX">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>33.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.8">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.8</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>378 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.8.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134596.90000000002 412036.37999999995 134510.16999999995 411993.96000000014 134512.06 411934.32 134600.47 411950.07 134596.90000000002 412036.37999999995</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>15.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>15.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.9">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.9</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>377 - triticale </imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.9.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134599.63000001013 411968.97000004986 134598.58000000997 411989.3400000499 134597.95000001 412016.85000005015 134596.69000000993 412036.80000005 134660.74000001003 412089.72000005015 134664.52000001 411963.09000004997 134632.91500000996 411957.52500005 134600.89000000997 411951.3300000499 134599.63000001013 411968.97000004986</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>15.5</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>15.5</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.10">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.10</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>171, 1156 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.10.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134669.77000000986 411965.8200000498 134666.62000001007 412056.0150000498 134665.3600000102 412147.47000004986 134701.79500001005 412146.63000004983 134737.39000000994 412144.1100000497 134759.09875001005 412145.6587500499 134775.34750001 412149.9375000498 134793.56500000998 412156.81500004994 134817.4000000101 412156.29000005 134830.63000001034 412153.14000005 134848.90000000998 412125.2100000498 134851.39375000994 412114.9462500497 134851.78750001 412103.84250004956 134846.27500000983 412093.81500005006 134838.8725000099 412082.42250005004 134825.59000001 412066.4100000498 134819.84125001004 412056.46125005 134812.83250001012 412030.13250005007 134809.5250000101 412004.77500004997 134800.18000000998 411993.5400000499 134792.35750001002 411988.55250004993 134763.03625001 411983.95875005005 134733.92500000997 411978.3150000498 134716.23250000982 411974.1675000499 134669.77000000986 411965.8200000498</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>69.1</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>69.1</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.11">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.11</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>128 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.11.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135155.91015626 411991.02328131004 135005.13015626004 412160.28328131 135099.8401562601 412162.80328130996 135146.04015626013 412132.9832813095 135155.91015626 411991.02328131004</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>38.1</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>38.1</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.12">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.12</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>1058 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.12.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135171.87015626 411979.26328131 135160.74015625994 412118.7032813098 135163.41765626005 412118.44078130997 135180.79515626025 412088.56828131067 135190.0745312603 412080.1551563103 135203.3668750102 412069.12687505997 135209.9392187603 412067.12859380996 135216.15390626015 412065.2320313097 135230.09265626012 412071.7157813096 135236.5501562601 412074.6032813097 135238.44015626016 412075.44328130956 135239.9101562599 412075.6532813096 135249.99015626 411941.46328131 135171.87015626 411979.26328131</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>14.0</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>14.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.13">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.13</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>1056 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.13.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135239.91015626 412075.6532813099 135287.7901562599 412100.22328130953 135303.75015626 411924.03328131 135250.41015626 411942.09328131 135239.91015626 412075.6532813099</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>12.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>12.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.14">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.14</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>1059 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.14.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135288.42015626002 412100.64328130963 135324.64515626003 412111.0382813099 135357.09015625998 412116.39328131 135383.76015626016 412115.34328130994 135409.38015625993 412110.09328130947 135431.01015626 411864.39328131 135304.17015626 411924.03328131 135288.42015626002 412100.64328130963</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>40.3</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>40.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.15">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.15</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>90 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.15.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135223.11015626 411882.87328131 135248.31015626004 411821.55328131 135260.07015625996 411784.59328131 135267.63015625993 411760.65328131005 135280.23015626 411748.47328131 135398.25015625998 411685.89328131 135407.07015625984 411680.0132813103 135391.53015625975 411832.05328131 135312.15015626 411869.43328131 135284.43015626 411862.71328131 135234.03015626 411884.55328131 135223.11015626 411882.87328131</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>69.2</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>69.2</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.16">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.16</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>91, 24 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.16.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135389.755 411832.47 135511.555 411772.83 135528.355 411634.65 135464.72499999992 411654.8099999999 135406.975 411680.01 135389.755 411832.47</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>26.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>26.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.17">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.17</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>124 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.17.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134759.6499999999 412446.51 134800.81 412404.51 135125.89 412451.97 135123.37 412501.53 134759.6499999999 412446.51</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>26.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>26.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.18">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.18</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>15, 107 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.18.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135145.63 412385.82 135136.60000000006 412503.42 135472.59999999983 412555.08000000013 135484.88499999975 412436.32499999984 135284.64999999994 412404.93000000005 135145.63 412385.82</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>63.6</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>63.6</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.19">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.19</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>74 - grasland, blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.19.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135409.73999999996 412109.77499999997 135432.0 411865.755 135534.48 411814.515 135544.56 411831.315 135510.11999999988 412171.5150000003 135496.10249999992 412168.8375000008 135521.98500000002 411908.27999999997 135507.66562499994 411927.35062499985 135491.2462499999 411982.54124999983 135472.68750000006 412051.76249999984 135465.8100000002 412077.64499999996 135456.41250000015 412095.2324999999 135441.97500000012 412101.6899999999 135425.85750000004 412106.0475 135409.73999999996 412109.77499999997</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>83.1</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>83.1</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.20">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.20</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>75 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.20.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135542.04 411898.515 135560.99250000002 411861.8699999998 135573.82875000002 411843.7574999998 135586.66499999998 411828.5849999996 135598.6874999998 411817.55999999953 135616.58999999973 411804.0149999995 135689.77500000008 411771.04499999946 135737.34000000008 411751.5149999993 135758.34000000023 411734.7150000003 135542.04 411811.155 135547.92000000007 411826.27500000014 135542.04 411898.515</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>17.3</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>17.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.21">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.21</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>25 - asperges</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.21.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135517.2600000001 411763.27500000014 135520.515 411768.2100000004 135527.18250000002 411765.84750000027 135572.51625000022 411740.93625 135575.01 411737.025 135581.20499999996 411677.28 135587.61000000002 411616.9049999999 135533.22 411639.795 135517.2600000001 411763.27500000014</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.22">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.22</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>7444 - asperges</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.22.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135587.40000000014 411620.68500000023 135575.43 411737.4450000001 135593.91 411734.295 135740.49 411646.305 135799.71 411613.965 135853.68 411574.275 135893.16 411546.135 135934.32 411494.265 135959.10000000012 411455.205 135946.50000000023 411458.3549999995 135935.78999999992 411461.29499999987 135921.3 411465.7050000003 135897.25499999977 411473.47500000027 135848.84999999983 411489.015 135846.74999999994 411503.08499999996 135841.71 411516.525 135823.65 411534.795 135587.40000000014 411620.68500000023</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.23">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.23</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>7441, 43 - tuinbonen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.23.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>136133.88015625998 411301.3832813099 136131.99015625994 411303.2732813101 136123.80015625997 411304.74328131013 136107.42015626 411302.8532813102 136095.66015625987 411302.43328131 136088.59890625987 411304.1395313102 136075.51328125998 411309.4026563102 136068.51765626008 411313.6157813101 136056.73140625982 411321.2020313104 136040.3251562599 411336.3482813099 136023.42015625976 411352.6232813097 136012.92015625982 411361.8632813098 136010.8201562598 411361.44328131014 136020.37515626 411310.7282813099 136026.30765625995 411278.7557813098 136028.43390625992 411267.17953130987 136057.86015625997 411272.40328130993 136132.20015626002 411285.8432813101 136137.24015625997 411288.15328131 136139.02515626006 411291.0932813098 136137.24015626006 411295.7132813099 136135.98015626005 411299.59828130994 136133.88015625998 411301.3832813099</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>5.2</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>5.2</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.24">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.24</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>7547, 7495, 7438, 7501, 7500, 2917, 3054 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.24.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>136042.32015626 411247.20328131015 136118.55015626 411262.11328131 136203.8101562601 410773.65328130964 136200.5551562601 410772.8132813097 136197.30015626005 410772.1832813098 136189.32015626007 410770.71328130993 136193.7301562599 410745.5132813093 136180.50015625998 410743.41328131 136183.23015626016 410716.74328130984 136178.24265626023 410715.69328130974 136170.73515626008 410714.4332813096 136155.45765626009 410711.80828130967 136125.48015625996 410706.8732813097 136042.32015626 411247.20328131015</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>41.9</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>41.9</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.25">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.25</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>23 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.25.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135486.18 412504.785 135788.58000000005 412576.1849999999 135792.78000000012 412552.6649999998 135796.14000000028 412528.7249999997 135775.66500000018 412524.41999999975 135633.65250000014 412486.9875 135490.80000000008 412451.8650000002 135486.18 412504.785</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>55.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>55.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.26">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.26</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>33 - aardappelen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.26.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135799.5 412575.55499999993 135811.46999999986 412519.6950000001 136004.03999999986 412566.9449999998 135974.64 412606.005 135967.92 412618.185 135799.5 412575.55499999993</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>15.2</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>15.2</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.27">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.27</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>6576 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.27.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>141069.73000000007 412950.16 141076.02999999977 412953.1000000002 141033.60999999987 413080.3599999997 141030.4600000001 413097.58 141015.97000000003 413147.55999999976 140991.60999999996 413228.2000000001 140991.19 413208.46 141069.73000000007 412950.16</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>3.1</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>3.1</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.28">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.28</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>6118 - slasoorten, eenmalig</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.28.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>141072.25 412937.14 141082.54000000018 412939.65999999986 141082.06750000018 412925.64250000025 141095.45499999996 412879.28499999986 141104.74749999994 412848.88749999984 141114.03999999995 412818.27999999985 141118.4499999999 412802.5299999998 141123.48999999985 412787.41000000003 141131.04999999993 412765.14999999985 141120.97 412775.86 141072.25 412937.14</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1.8</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>1.8</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.29">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.29</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>5102 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.29.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>141234.16000000003 412404.09000000043 141238.99000000002 412403.87999999983 141206.44 412503.42 141206.22999999998 412503.21 141203.92 412502.58 141203.29 412501.32 141211.58500000005 412470.03 141221.98 412437.4799999999 141231.22 412407.6599999999 141234.16000000003 412404.09000000043</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.30">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.30</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>5973, 5975 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.30.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>141242.34999999998 412394.2899999996 141235.83999999997 412392.61 141268.60000000006 412285.5100000003 141275.94999999992 412287.19000000024 141268.33749999994 412312.2849999999 141259.675 412338.84999999974 141250.80249999996 412367.1999999997 141242.34999999998 412394.2899999996</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1.6</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>1.6</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.31">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.31</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>5044, 5027, 5045 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.31.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>143314.42 413877.73 143295.52 413861.77 143525.68 413637.91 143555.5 413650.93 143314.42 413877.73</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.32">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.32</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>5047 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.32.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>143575.0299999999 413626.99000000017 143564.32000000004 413619.42999999993 143550.88 413609.77 143772.64 413384.23 143790.27999999988 413397.2499999998 143575.0299999999 413626.99000000017</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>12.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>12.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.33">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.33</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>6098 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.33.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>143777.25999999992 413382.3400000001 143785.66 413389.8999999999 143792.79999999996 413394.09999999986 143866.29999999978 413317.6600000001 143905.35999999978 413278.18 143891.5 413270.62 143888.97999999995 413269.15 143853.28 413305.06000000006 143833.75 413324.17 143814.43 413344.11999999994 143794.79499999998 413363.23 143777.25999999992 413382.3400000001</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.34">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.34</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>6620, 6614, 6617, 6610, 6624, 6607, 6606, 6623, 6612 - aardappelen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.34.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144014.98000000016 413173.5999999995 143995.86999999994 413165.83000000013 144086.90499999968 413077.31499999965 144102.73374999987 413058.65124999976 144116.1081249999 413047.6393749998 144130.53249999988 413034.3174999997 144149.20937500003 413017.68812499987 144164.53281250005 413002.4434374998 144178.59625000018 412988.8787499999 144337.54 412835.08 144357.70000000013 412849.35999999975 144295.95999999988 412903.9599999996 144345.31 412921.3899999997 144391.1949999998 412929.47499999974 144433.7199999997 412936.30000000016 144493.56999999975 412944.0962500002 144508.26999999981 412930.47250000027 144522.33999999994 412927.7950000003 144530.32000000012 412929.3700000002 144525.5950000002 412900.9150000003 144533.2600000001 412900.1800000002 144537.6699999998 412924.9600000001 144542.39500000005 412951.21000000014 144515.7774999999 412973.2600000002 144500.07999999973 412987.54 144449.2599999997 413029.9599999995 144468.7900000001 412995.7299999996 144437.07999999984 412985.0199999997 144354.34000000005 412969.4799999999 144334.17999999996 412970.7400000001 144269.0799999998 412968.2200000002 144232.53999999995 412955.2 144202.30000000005 412986.2799999998 144174.5800000002 413022.3999999997 144014.98000000016 413173.5999999995</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>33.2</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>33.2</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.35">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.35</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>428 - aardappelen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.35.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144367.56999999983 413146.3 144361.27 413143.36 144470.05 413037.94 144491.89000000007 413049.70000000007 144470.47 413050.5399999999 144426.37 413090.8599999999 144367.56999999983 413146.3</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1.4</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>1.4</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.36">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.36</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>941, 842 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.36.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144791.55999999994 412558.02 144750.82 412572.72 144681.52 412590.36 144623.98 412605.06 144543.34 412620.18 144537.46000000005 412612.82999999984 144628.80999999994 412595.0849999998 144715.1199999997 412574.39999999985 144721.21 412506.57000000007 144791.55999999994 412558.02</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>19.1</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>19.1</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.37">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.37</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>935 - wintergerst</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.37.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144739.69 412501.18 144713.2300000002 412482.28000000014 144764.0500000003 412365.9400000002 144771.61000000028 412352.9200000004 144814.02999999994 412306.30000000034 144739.69 412501.18</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>9.7</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>9.7</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.38">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.38</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>65 - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.38.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144740.95 412502.86 144772.66000000003 412524.49 144775.49499999997 412493.30499999976 144781.3225 412474.5624999998 144791.35000000012 412455.81999999983 144807.30999999985 412433.56000000023 144825.37000000014 412413.18999999994 144853.09000000026 412384.4199999999 144796.39 412362.58 144740.95 412502.86</gml:posList>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.39">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.39</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>939 - grasland, blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.39.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144796.39 412359.85 144855.82000000015 412381.8999999997 144905.37999999995 412327.7200000008 144937.30000000005 412289.0799999999 144862.01500000016 412256.7399999997 144832.0900000002 412311.76000000024 144815.9200000001 412305.88 144796.39 412359.85</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>43.0</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>43.0</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.40">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.40</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>930, 931 (oostelijk) - grasland blijvend</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.40.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144959.98 412272.7 144972.7900000001 412263.67000000004 144993.52749999982 412248.55000000005 145034.00499999998 412229.6499999999 145064.55999999982 412218.72999999986 145086.19 412190.17 145046.92 412181.56 144991.06 412161.61 144959.98 412272.7</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>9.3</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>9.3</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.41">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.41</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>931, 934 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.41.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>144879.34 412237.35 144959.56 412272.21 144991.06 412160.91 144956.62 412139.07 144912.52 412096.65 144877.24 412052.13 144847.42 412015.17 144844.9 412021.05 144861.91000000006 412043.10000000003 144919.65999999997 412193.2500000001 144879.34 412237.35</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>18.6</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>18.6</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.42">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.42</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>170 - maïs</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.42.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>134663.68 412165.00500012 134749.36 412161.22500012 134764.48 412162.90500012 134774.56 412167.52500012 134789.68 412178.86500012 134799.33999999994 412188.7350001201 134784.43000000014 412204.8000001197 134758.39000000004 412183.2750001196 134743.71625000003 412181.8575001195 134728.62250000003 412181.07000011974 134714.60500000004 412180.1250001201 134681.32000000007 412178.6550001197 134663.46999999986 412177.8150001198 134663.68 412165.00500012</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>7.7</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>7.7</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.43">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.43</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>7427 - asperges</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.43.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135547.99 411610.08 135553.66 411591.6 135575.07999999993 411583.8299999999 135577.59999999995 411575.0624999998 135581.8 411572.1487499997 135586.0 411570.91499999975 135597.12999999998 411567.23999999976 135590.41 411594.33 135547.99 411610.08</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>0.8</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>0.8</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
    <imaer:featureMember>
        <imaer:FarmlandEmissionSource sectorId="4150" gml:id="ES.44">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.44</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>7429, 7432, 7439, 7433 - veld en tuinbonen</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>0.5</imaer:emissionHeight>
                    <imaer:spread>0.3</imaer:spread>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.44.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>135597.13 411567.45 135590.41 411594.33 135763.345 411531.85499999975 135804.45249999996 411517.4174999999 135813.56124999994 411513.76875 135820.56999999998 411506.5499999997 135823.64124999996 411498.80624999944 135811.59249999994 411503.2424999997 135754.7349999999 411523.0349999998 135688.05999999985 411547.07999999984 135618.13 411571.65 135597.13 411567.45</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>1.7</imaer:value>
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
            <imaer:activity>
                <imaer:FarmlandActivity activityType="FERTILIZER">
                    <imaer:emission>
                        <imaer:Emission substance="NH3">
                            <imaer:value>1.7</imaer:value>
                        </imaer:Emission>
                    </imaer:emission>
                </imaer:FarmlandActivity>
            </imaer:activity>
        </imaer:FarmlandEmissionSource>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
