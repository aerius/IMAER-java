# Shapechange instructions IMAER

Currently using ShapeChange-3.0.0 to extract a XSD for our GML from a Enterprise Architect model.

## Shapechange installation

Download the ShapeChange tool from https://shapechange.github.io/ShapeChange

Integration of ShapeChange-3.0.0 with Enterprise Architect:

To process Enterprise Architect models with ShapeChange, copy the file SSJavaCOM64.dll located in `<EA installation folder>/Java API` to `<Windows folder>/System32` (on a 64-bit machine). If you have a 32 bit system, have a look at the shapechange documentation on how to install.

source: https://shapechange.github.io/ShapeChange/3.0.0/get%20started/Get_Started.html

## Generation of XSD

To generate the XSD, copy the IMAER-ShapeChangeConfiguration.xml file to the `<ShapeChange installation folder>` and adjust to system specifications.
In case of a new IMAER version, be sure to update both the IMAER-ShapeChangeConfiguration.xml and the IMAER.EAP file:
In Enterprise Architect, right-click `<<applicationSchema>> IMAER` -> `Properties` -> `Tagged Values` -> `targetNamespace`.

Run ShapeChange with the following command (do not forget to update the paths!) from inside the ShapeChange directory:

```
"C:\path\to\java.exe" -jar "C:\ShapeChange-3.0.0\ShapeChange-3.0.0.jar" -Dfile.encoding=UTF-8 -c IMAER-ShapeChangeConfiguration.xml
```

After generating the XSD, be sure to do the following 2 things:

* Add `xmlns:gmlsf="http://www.opengis.net/gmlsf/2.0" ` to the `<schema>` element
* replace 
```
  <!--XML Schema document created by ShapeChange - http://shapechange.net/-->
```

with

```
  <import namespace="http://www.opengis.net/gmlsf/2.0" schemaLocation="http://schemas.opengis.net/gmlsfProfile/2.0/gmlsfLevels.xsd"/>
  <!--XML Schema document created by ShapeChange - http://shapechange.net/-->
  <annotation>
    <appinfo source="http://schemas.opengis.net/gmlsfProfile/2.0/gmlsfLevels.xsd">
      <gmlsf:ComplianceLevel>2</gmlsf:ComplianceLevel>
    </appinfo>
  </annotation>
  <element name="FeatureCollectionCalculator" type="IMAER:FeatureCollectionCalculatorType" substitutionGroup="gml:AbstractFeature"/>
  <complexType name="FeatureCollectionCalculatorType">
    <complexContent>
      <extension base="gml:AbstractFeatureType">
        <sequence minOccurs="0" maxOccurs="unbounded">
          <element name="metadata" type="IMAER:AeriusCalculatorMetadataPropertyType" minOccurs="0"/>
          <element name="featureMember" minOccurs="0" maxOccurs="unbounded">
            <complexType>
              <complexContent>
                <extension base="gml:AbstractFeatureMemberType">
                  <sequence>
                    <element ref="IMAER:GeoObject"/>
                  </sequence>
                </extension>
              </complexContent>
            </complexType>
          </element>
          <element name="definitions" type="IMAER:DefinitionsPropertyType" minOccurs="0"/>
        </sequence>
      </extension>
    </complexContent>
  </complexType>
```

## Generated HTML

Along with the XSD a HTML file is generated for documentation purposes. This can be added to the actual release.

It can be found at `<ShapeChange installation folder>\AERIUS\feature_catalogue_AERIUS.html` when done generating XSD.

## XMI file

Currently there is also an XMI file present (version 6.0 and further).
The idea behind this file is that it'll help with reviewing changes done in the EAP file (which is a binary file).
This file is exported in EAP through `<Publish>` -> `Export-XML` -> `Export XML for Current Package...`.
Be sure to select `XMI 2.1` as export type, and export it to the correct location.

When changing something in the EAP file, be sure to also export the changes to XMI and include it in the PR.
This way, the 2 files should be kept in sync (though the EAP file will be leading, at least for now).

Time will tell if this is useful, or if it's only added work that has no actual benefit.
