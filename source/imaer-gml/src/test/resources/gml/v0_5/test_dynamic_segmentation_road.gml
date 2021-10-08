<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator
	xmlns:imaer="http://imaer.aerius.nl/0.5" xmlns:gml="http://www.opengis.net/gml/3.2"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	gml:id="NL.IMAER.Collection"
	xsi:schemaLocation="http://imaer.aerius.nl/0.5 http://imaer.aerius.nl/0.5/IMAER.xsd">
	<imaer:aeriusCalculatorMetaData>
		<imaer:year>2013</imaer:year>
		<imaer:version>V0.5</imaer:version>
		<imaer:databaseVersion>SomeDBVersion</imaer:databaseVersion>
		<imaer:situationName>Situatie 1</imaer:situationName>
		<imaer:reference>SomeReference001</imaer:reference>
		<imaer:projectName>Situatie 1</imaer:projectName>
		<imaer:corporation>Acme B.V.</imaer:corporation>
	</imaer:aeriusCalculatorMetaData>

	<imaer:featureMembers>
		<imaer:SRM2Road sectorId="3111" gml:id="ES.2">
			<imaer:identifier>
				<imaer:NEN3610ID>
					<imaer:namespace>NL.IMAER</imaer:namespace>
					<imaer:localId>ES.2</imaer:localId>
				</imaer:NEN3610ID>
			</imaer:identifier>

			<imaer:label>SomeRoadSource</imaer:label>

			<imaer:emissionSourceCharacteristics>
				<imaer:EmissionSourceCharacteristics>
					<imaer:heatContent>0.28</imaer:heatContent>
					<imaer:emissionHeight>22.0</imaer:emissionHeight>
					<imaer:spread>11.0</imaer:spread>
				</imaer:EmissionSourceCharacteristics>
			</imaer:emissionSourceCharacteristics>

			<imaer:geometry>
				<imaer:EmissionSourceGeometry>
					<imaer:GM_Curve>
						<gml:LineString gml:id="ES.2.CURVE"
							srsName="urn:ogc:def:crs:EPSG::28992">
							<gml:posList>136558 455251 208413 474162</gml:posList>
						</gml:LineString>
					</imaer:GM_Curve>
				</imaer:EmissionSourceGeometry>
			</imaer:geometry>

			<imaer:emission>
				<imaer:Emission substance="NH3">
					<imaer:value>787.0184602634738</imaer:value>
				</imaer:Emission>
			</imaer:emission>

			<imaer:emission>
				<imaer:Emission substance="NOX">
					<imaer:value>32230.22144968474</imaer:value>
				</imaer:Emission>
			</imaer:emission>

			<imaer:emission>
				<imaer:Emission substance="PM10">
					<imaer:value>1448.617656123354</imaer:value>
				</imaer:Emission>
			</imaer:emission>

			<imaer:emission>
				<imaer:Emission substance="NO2">
					<imaer:value>4827.820159317392</imaer:value>
				</imaer:Emission>
			</imaer:emission>

			<imaer:vehicles>
				<imaer:StandardVehicle vehicleType="LIGHT_TRAFFIC">
					<imaer:vehiclesPerDay>980.0</imaer:vehiclesPerDay>
					<imaer:stagnationFactor>0.2</imaer:stagnationFactor>
				</imaer:StandardVehicle>
			</imaer:vehicles>

			<imaer:vehicles>
				<imaer:StandardVehicle vehicleType="HEAVY_FREIGHT">
					<imaer:vehiclesPerDay>200.0</imaer:vehiclesPerDay>
					<imaer:stagnationFactor>0.0</imaer:stagnationFactor>
				</imaer:StandardVehicle>
			</imaer:vehicles>

			<imaer:isFreeway>true</imaer:isFreeway>
			<imaer:maximumSpeed>100</imaer:maximumSpeed>
			<imaer:strictEnforcement>false</imaer:strictEnforcement>
			<imaer:tunnelFactor>1.0</imaer:tunnelFactor>
			<imaer:elevation>STEEP_DYKE</imaer:elevation>
			<imaer:elevationHeight>2</imaer:elevationHeight>

			<imaer:partialChange>
				<imaer:SRM2LinearReference>
					<imaer:fromPosition uom="%">50</imaer:fromPosition>
					<imaer:toPosition uom="%">60</imaer:toPosition>
					<imaer:barrierRight>
						<imaer:RoadSideBarrier>
							<imaer:barrierType>WALL</imaer:barrierType>
							<imaer:height>3.1</imaer:height>
							<imaer:distance>5.1</imaer:distance>
						</imaer:RoadSideBarrier>
					</imaer:barrierRight>
				</imaer:SRM2LinearReference>
			</imaer:partialChange>

			<imaer:partialChange>
				<imaer:SRM2LinearReference>
					<imaer:fromPosition uom="%">10</imaer:fromPosition>
					<imaer:toPosition uom="%">30</imaer:toPosition>

					<imaer:barrierLeft>
						<imaer:RoadSideBarrier>
							<imaer:barrierType>SCREEN</imaer:barrierType>
							<imaer:height>2.6</imaer:height>
							<imaer:distance>3.2</imaer:distance>
						</imaer:RoadSideBarrier>
					</imaer:barrierLeft>
				</imaer:SRM2LinearReference>
			</imaer:partialChange>
		</imaer:SRM2Road>
	</imaer:featureMembers>
</imaer:FeatureCollectionCalculator>
