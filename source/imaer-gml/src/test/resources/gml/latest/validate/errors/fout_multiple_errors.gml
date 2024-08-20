<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xsi:schemaLocation="http://imaer.aerius.nl/5.1 http://imaer.aerius.nl/5.1/IMAER.xsd" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/5.1" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2023</imaer:year>
                    <imaer:description></imaer:description>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:gmlCreator>QgisImaerPlugin-3.4.2</imaer:gmlCreator>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:ADMSRoad sectorId="3100" roadType="" roadAreaType="" gml:id="ES.0">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.0</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Curve>
                        <gml:LineString srsName="urn:ogc:def:crs:EPSG::27700" gml:id="ES.0.CURVE">
                            <gml:posList srsDimension="2">201425.333 637649.561 201227.783 637602.222 201149.036 637563.987 201120.587 637536.448 201096.69 637496.961 201075.297 637444.615 201082.58 637393.975 201081.442 637329.339 201066.421 637128.603 201054.586 637104.479 200679.515 636943.116 200642.189 636923.088 200577.781 636867.1 200557.525 636842.065</gml:posList>
                        </gml:LineString>
                    </imaer:GM_Curve>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:vehicles>
                <imaer:CustomVehicle>
                    <imaer:vehiclesPerTimeUnit>None</imaer:vehiclesPerTimeUnit>
                    <imaer:timeUnit>None</imaer:timeUnit>
                    <imaer:description>None</imaer:description>
                </imaer:CustomVehicle>
            </imaer:vehicles>
            <imaer:diurnalVariation>
                <imaer:StandardDiurnalVariation>
                    <imaer:standardType>800.0</imaer:standardType>
                </imaer:StandardDiurnalVariation>
            </imaer:diurnalVariation>
        </imaer:ADMSRoad>
    </imaer:featureMember>
</imaer:FeatureCollectionCalculator>
