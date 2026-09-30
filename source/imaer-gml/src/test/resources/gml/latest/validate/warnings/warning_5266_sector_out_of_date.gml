<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/6.0" xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection" xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
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
					<imaer:reference>S1QZaEKCGqgX</imaer:reference>
					<imaer:situationType>PROPOSED</imaer:situationType>
				</imaer:SituationMetadata>
			</imaer:situation>
			<imaer:version>
				<imaer:VersionMetadata>
					<imaer:aeriusVersion>build-704_2025-06-18-01-02_20250618_18cc08c023</imaer:aeriusVersion>
					<imaer:databaseVersion>build-704_2025-06-18-01-02_18cc08c023_calculator_nl_latest</imaer:databaseVersion>
				</imaer:VersionMetadata>
			</imaer:version>
		</imaer:AeriusCalculatorMetadata>
	</imaer:metadata>
	<imaer:featureMember>
		<imaer:OffRoadMobileSourceEmissionSource sectorId="3219" gml:id="ES.1">
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
							<gml:pos>160734.39125000002 527834.56</gml:pos>
						</gml:Point>
					</imaer:GM_Point>
				</imaer:EmissionSourceGeometry>
			</imaer:geometry>
			<imaer:emission>
				<imaer:Emission substance="NH3">
					<imaer:value>7.5E-6</imaer:value>
				</imaer:Emission>
			</imaer:emission>
			<imaer:emission>
				<imaer:Emission substance="NOX">
					<imaer:value>0.04</imaer:value>
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
				<imaer:StandardOffRoadMobileSource offRoadMobileSourceType="SI56DSN">
					<imaer:description>Standaard werktuig</imaer:description>
					<imaer:literFuelPerYear>1</imaer:literFuelPerYear>
					<imaer:operatingHoursPerYear>2</imaer:operatingHoursPerYear>
				</imaer:StandardOffRoadMobileSource>
			</imaer:offRoadMobileSource>
		</imaer:OffRoadMobileSourceEmissionSource>
	</imaer:featureMember>
</imaer:FeatureCollectionCalculator>
