<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/4.0" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/4.0 https://imaer.aerius.nl/4.0/IMAER.xsd">
    <imaer:metadata>
        <imaer:AeriusCalculatorMetadata>
            <imaer:project>
                <imaer:ProjectMetadata>
                    <imaer:year>2016</imaer:year>
                    <imaer:name>Haven Heijen</imaer:name>
                    <imaer:corporation>Teunesen Zand en Grint</imaer:corporation>
                    <imaer:facilityLocation>
                        <imaer:Address>
                            <imaer:streetAddress>Hoogveld</imaer:streetAddress>
                            <imaer:postcode>6598 BL</imaer:postcode>
                            <imaer:city>Heijen</imaer:city>
                        </imaer:Address>
                    </imaer:facilityLocation>
                    <imaer:description>Stikstofdepositieberekeningen</imaer:description>
                </imaer:ProjectMetadata>
            </imaer:project>
            <imaer:situation>
                <imaer:SituationMetadata>
                    <imaer:name>feitelijk</imaer:name>
                    <imaer:reference>S2zVcVJxky74</imaer:reference>
                </imaer:SituationMetadata>
            </imaer:situation>
            <imaer:version>
                <imaer:VersionMetadata>
                    <imaer:aeriusVersion>1.0-SNAPSHOT_20190319_83010e2357</imaer:aeriusVersion>
                    <imaer:databaseVersion>1.0-SNAPSHOT_20190319_83010e2357</imaer:databaseVersion>
                </imaer:VersionMetadata>
            </imaer:version>
        </imaer:AeriusCalculatorMetadata>
    </imaer:metadata>
    <imaer:featureMember>
        <imaer:EmissionSource sectorId="1800" gml:id="ES.1">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.1</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>bemesting</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>1.0</imaer:emissionHeight>
                    <imaer:spread>0.0</imaer:spread>
                    <imaer:diurnalVariation>INDUSTRIAL_ACTIVITY</imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>195027.228 409229.835 195033.554 409377.963 195035.512 409476.971 195035.245 409518.752 195034.867 409578.105 195034.85 409580.729 195034.054 409601.163 195030.992 409679.864 195029.455 409720.233 195028.307 409750.374 195027.11 409781.809 195023.276 409828.286 195018.77 409882.918 195018.569 409885.345 195014.923 409920.557 195008.938 409977.722 195007.212 409994.192 195006.569 410000.0 195000.0 410059.317 194998.918 410069.088 194996.083 410094.698 194991.571 410134.04 194983.812 410201.707 194979.505 410230.647 194978.874 410234.886 194971.781 410282.56 194962.318 410344.793 194959.621 410362.528 194962.306 410367.743 194992.593 410438.546 195000.0 410455.863 195005.019 410467.596 195010.095 410500.0 195012.284 410513.973 195012.444 410518.238 195047.842 410519.658 195106.484 410527.98 195131.108 410531.057 195174.23 410537.427 195175.0569694737 410537.5212489699 195175.88837810603 410537.56024982774 195176.72053900358 410537.5438296496 195177.5497619286 410537.4720612317 195178.37236969915 410537.34526282543 195179.18471444395 410537.1639967262 195179.98319379982 410536.92906676227 195180.7642669156 410536.6415147334 195181.52447010574 410536.3026157915 195182.26043223587 410535.91387279326 195182.968889676 410535.4770096242 195183.64670076963 410534.99396355444 195184.29085973647 410534.46687667066 195184.89851006863 410533.8980863243 195185.4669571146 410533.29011483485 195185.99368009713 410532.64565826947 195186.4763432592 410531.96757444734 195186.91280622032 410531.2588703781 195187.30113347617 410530.522688806 195187.63960298893 410529.7622943222 195187.9267138168 410528.9810589179 195188.1611927474 410528.1824469899 195188.342 410527.37 195188.973 410524.012 195189.07061654935 410523.4561668934 195189.1557549982 410522.898286201 195189.22837259618 410522.3386380076 195189.28843288866 410521.7775032669 195189.33590572316 410521.21516373 195189.3707672567 410520.6519016923 195189.393 410520.088 195190.693 410473.306 195190.74293445188 410472.2738275478 195190.83551359916 410471.24460329855 195190.97057903555 410470.22008842224 195191.1478996351 410469.2020359486 195191.36717198783 410468.1921878978 195191.62802088397 410467.1922722558 195191.93 410466.204 195202.873 410432.862 195197.196 410430.466 195223.858 410289.981 195226.966 410277.574 195212.935 410274.059 195190.545 410265.083 195191.092 410255.934 195196.858 410157.874 195200.713 410092.992 195201.471 410080.226 195206.554 410052.155 195215.302 410020.402 195220.54 410002.983 195220.552 410002.68 195221.522 410000.0 195235.307 409961.923 195235.634 409961.02 195241.962 409943.542 195245.05 409935.31 195247.498 409927.604 195251.178 409913.097 195265.376 409857.131 195265.387 409857.074 195267.126 409848.553 195272.203 409811.608 195274.2 409812.0 195279.638 409782.402 195280.667 409776.798 195290.879 409754.047 195293.138 409750.944 195302.79 409737.694 195309.918 409720.344 195310.5049791208 409711.7943041181 195311.474 409711.905 195310.227 409691.33 195310.2 409685.754 195310.795 409680.21 195312.658 409668.854 195312.6728237268 409668.7614713349 195312.7774493194 409667.8952536236 195312.8213957563 409667.02384766267 195312.80444893482 409666.15149885445 195312.72669141824 409665.2824571945 195312.5885020333 409664.42095656664 195312.39055402484 409663.57119411515 195312.13381177565 409662.7373097973 195311.81952610813 409661.9233662138 195311.4492281905 409661.1333288158 195310.976 409660.291 195310.43914510056 409659.4877566191 195309.90231018496 409658.79994275526 195309.31880350248 409658.151252082 195308.69146783903 409657.54484495526 195308.02335951093 409656.98367572954 195307.31773347477 409656.47047836485 195306.57802746966 409656.0077531073 195305.80784526895 409655.5977543077 195305.01093912285 409655.24247943895 195304.102 409654.915 195307.236 409644.949 195312.135 409646.49 195312.7846145641 409646.66797302495 195313.40080029386 409646.7897174886 195314.02397749067 409646.8681824452 195314.65111009782 409646.9029856213 195315.2791427883 409646.8939574594 195315.90501584997 409646.84114194376 195316.5256800922 409646.7447963863 195317.13811170115 409646.6053901728 195317.73932697164 409646.4236024764 195318.32639684327 409646.2003189485 195318.937 409645.916 195319.52456450026 409645.5868433592 195320.0489162576 409645.2410662597 195320.54787052926 409644.8595545261 195321.01899646016 409644.44416684547 195321.45999877213 409643.9969269447 195321.86872894625 409643.5200137316 195322.24319569016 409643.01575067954 195322.58157463957 409642.4865945073 195322.88221724622 409641.93512321054 195323.14365880957 409641.3640235021 195323.379 409640.733 195336.59 409600.822 195336.59 409596.593 195333.565 409593.328 195330.493 409590.832 195326.317 409588.624 195325.069 409588.672 195329.677 409577.055 195334.789 409579.082 195335.47687296805 409579.32623971114 195336.1493620959 409579.5121784199 195336.865 409579.656 195337.58884179243 409579.74718096625 195338.28554964796 409579.7847707854 195339.015 409579.772 195351.674 409579.089 195353.19974270905 409578.9277015288 195354.2282922831 409578.7284363583 195355.24043632642 409578.4579085964 195356.23124377502 409578.1174362271 195357.634 409577.496 195358.96610714958 409576.7345390584 195359.82762286937 409576.13837507175 195360.6454536827 409575.48356701195 195361.41561520004 409574.773305038 195362.45 409573.64 195385.795 409545.257 195369.771 409526.273 195351.471 409506.235 195346.996 409501.335 195309.472 409459.708 195269.301 409415.796 195231.082 409371.186 195227.005 409366.517 195219.304 409357.732 195209.956 409348.779 195206.965 409345.95 195183.227 409324.766 195149.422 409294.463 195128.141 409275.52 195104.805 409254.401 195094.83 409245.063 195084.186 409235.146 195065.979 409218.349 195057.478 409210.759 195047.563 409201.935 195042.191 409196.129 195033.65 409186.683 195031.22 409183.254 195028.79 409179.463 195026.423 409175.58 195024.819 409173.42 195027.228 409229.835</gml:posList>
</gml:LinearRing>
                            </gml:exterior>
                        </gml:Polygon>
                    </imaer:GM_Surface>
                </imaer:EmissionSourceGeometry>
            </imaer:geometry>
            <imaer:emission>
                <imaer:Emission substance="NH3">
                    <imaer:value>210.0</imaer:value>
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
        <imaer:EmissionSource sectorId="1800" gml:id="ES.2">
            <imaer:identifier>
                <imaer:NEN3610ID>
                    <imaer:namespace>NL.IMAER</imaer:namespace>
                    <imaer:localId>ES.2</imaer:localId>
                </imaer:NEN3610ID>
            </imaer:identifier>
            <imaer:label>opslag_AVG</imaer:label>
            <imaer:emissionSourceCharacteristics>
                <imaer:EmissionSourceCharacteristics>
                    <imaer:heatContent>
                        <imaer:SpecifiedHeatContent>
                            <imaer:value>0.0</imaer:value>
                        </imaer:SpecifiedHeatContent>
                    </imaer:heatContent>
                    <imaer:emissionHeight>1.0</imaer:emissionHeight>
                    <imaer:spread>0.0</imaer:spread>
                    <imaer:diurnalVariation>INDUSTRIAL_ACTIVITY</imaer:diurnalVariation>
                </imaer:EmissionSourceCharacteristics>
            </imaer:emissionSourceCharacteristics>
            <imaer:geometry>
                <imaer:EmissionSourceGeometry>
                    <imaer:GM_Surface>
                        <gml:Polygon srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.2.SURFACE">
                            <gml:exterior>
<gml:LinearRing>
    <gml:posList>195301.20699999854 409954.7049999982 195267.15300000086 409947.3819999993 195254.8951999992 409944.74599999934 195253.817400001 409947.853599999 195236.86719999835 409996.7232000008 195231.59 410014.90019999817 195229.7650000006 410025.4230000004 195223.336 410048.7529999986 195223.01839999855 410051.27710000053 195221.83100000024 410060.71499999985 195220.83900000155 410064.4050000012 195217.03599999845 410070.864 195213.796 410119.3579999991 195204.9459999986 410258.73000000045 195218.1640000008 410264.02899999917 195221.9149999991 410262.30799999833 195234.73000000045 410224.1279999986 195246.28700000048 410185.7890000008 195261.3990000002 410122.3509999998 195276.6629000008 410058.0702 195299.9 409960.2120000012 195300.74599999934 409956.6490000002 195301.20699999854 409954.7049999982</gml:posList>
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
                    <imaer:value>233.0</imaer:value>
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
</imaer:FeatureCollectionCalculator>
