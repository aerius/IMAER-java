<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<imaer:FeatureCollectionCalculator xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:imaer="http://imaer.aerius.nl/6.0"
  xmlns:gml="http://www.opengis.net/gml/3.2" gml:id="NL.IMAER.Collection"
  xsi:schemaLocation="http://imaer.aerius.nl/6.0 https://imaer.aerius.nl/6.0/IMAER.xsd">
  <imaer:metadata>
    <imaer:AeriusCalculatorMetadata>
      <imaer:project>
        <imaer:ProjectMetadata>
          <imaer:year>2025</imaer:year>
        </imaer:ProjectMetadata>
      </imaer:project>
      <imaer:situation>
        <imaer:SituationMetadata>
          <imaer:name>Scenario 1</imaer:name>
          <imaer:reference>S6KeXjoQicxE</imaer:reference>
          <imaer:situationType>PROPOSED</imaer:situationType>
        </imaer:SituationMetadata>
      </imaer:situation>
      <imaer:version>
        <imaer:VersionMetadata>
          <imaer:aeriusVersion>2024.2.1_20250507_5b5649d2ba</imaer:aeriusVersion>
          <imaer:databaseVersion>2024.2.1_5b5649d2ba_calculator_nl_stable</imaer:databaseVersion>
        </imaer:VersionMetadata>
      </imaer:version>
    </imaer:AeriusCalculatorMetadata>
  </imaer:metadata>
  <imaer:featureMember>
    <imaer:EmissionSource sectorId="9999" gml:id="ES.1">
      <imaer:identifier>
        <imaer:NEN3610ID>
          <imaer:namespace>NL.IMAER</imaer:namespace>
          <imaer:localId>ES.1</imaer:localId>
        </imaer:NEN3610ID>
      </imaer:identifier>
      <imaer:label>Source 1</imaer:label>
      <imaer:emissionSourceCharacteristics>
        <imaer:EmissionSourceCharacteristics>
          <imaer:heatContent>
            <imaer:SpecifiedHeatContent>
              <imaer:value>0.0</imaer:value>
            </imaer:SpecifiedHeatContent>
          </imaer:heatContent>
          <imaer:emissionHeight>80.0</imaer:emissionHeight>
          <imaer:spread>40.0</imaer:spread>
          <imaer:timeVaryingProfile>
            <imaer:StandardTimeVaryingProfile>
              <imaer:standardType>CONTINUOUS</imaer:standardType>
            </imaer:StandardTimeVaryingProfile>
          </imaer:timeVaryingProfile>
        </imaer:EmissionSourceCharacteristics>
      </imaer:emissionSourceCharacteristics>
      <imaer:geometry>
        <imaer:EmissionSourceGeometry>
          <imaer:GM_Point>
            <gml:Point srsName="urn:ogc:def:crs:EPSG::28992" gml:id="ES.1.POINT">
              <gml:pos>139810.6980345408 455015.72519031086</gml:pos>
            </gml:Point>
          </imaer:GM_Point>
        </imaer:EmissionSourceGeometry>
      </imaer:geometry>
      <imaer:emission>
        <imaer:Emission substance="NH3">
          <imaer:value>1.0</imaer:value>
        </imaer:Emission>
      </imaer:emission>
      <imaer:emission>
        <imaer:Emission substance="NOX">
          <imaer:value>1.0</imaer:value>
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