<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:gml="http://www.opengis.net/gml/3.2" xmlns:imaer="http://imaer.aerius.nl/4.0" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/4.0 https://imaer.aerius.nl/4.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2017</imaer:year>
                    <imaer:name>Pipowagens</imaer:name>
                    <imaer:corporation>Strandpark Vlugtenburg</imaer:corporation>
                    <imaer:facilityLocation>
                        <imaer:Address>
                            <imaer:streetAddress>'t Louwtje 10</imaer:streetAddress>
                            <imaer:postcode>2691 KR</imaer:postcode>
                            <imaer:city>'s-Gravenzande</imaer:city>
                        </imaer:Address>
                    </imaer:facilityLocation>
                    <imaer:description>Pipowagens nabij strandpark Vlugtenburg, 9 stuks</imaer:description>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>Situatie 1</imaer:name>
                    <imaer:reference>RUs4ZDYMeuwa</imaer:reference>
                    <imaer:situationType>PROPOSED</imaer:situationType>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:calculation>
                <imaer:CalculationMetadata>
                    <imaer:type>CUSTOM_POINTS</imaer:type>
                    <imaer:substance>NOX</imaer:substance>
                    <imaer:substance>NO2</imaer:substance>
                    <imaer:substance>NH3</imaer:substance>
                    <imaer:resultType>DEPOSITION</imaer:resultType>
                </imaer:CalculationMetadata>
            </imaer:calculation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>DEV</imaer:aeriusVersion>
                    <imaer:databaseVersion>1.0-SNAPSHOT_20171026_e371b7d046</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:CalculationPoint gml:id="CP.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>CP.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:GM_Point>
                <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="CP.1.POINT">
                    <gml:pos>71902.123456 450141.123456</gml:pos>
                </gml:Point>
            </imaer:GM_Point>
            <imaer:result>
                <imaer:CalculationResult resultType="DEPOSITION" substance="NOX">
                    <imaer:value>7.224E-5</imaer:value>
                </imaer:CalculationResult>
            </imaer:result>
            <imaer:label>Solleveld &amp; Kapittelduinen H2130B (4 km)</imaer:label>
        </imaer:CalculationPoint>
    </imaer:featureMember>
 </imaer:FeatureCollectionCalculator>
