# Expose

This is a debugging project for figuring out how a JAXB2 marshaller needs to be configured in order to include the blob of a SOAP request in the message instead of an attachment.

Basically, the goal is a SOAP request that looks like this:

````xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <SOAP-ENV:Body>
        <MyRequest xmlns="http://my.company.com/xsd/portals/v4_0">
            <documentList xmlns="">
                <binaryData>
                    <blob>123456789ABCDEF0</blob>
                    <extension>pdf</extension>
                </binaryData>
            </documentList>
        </MyRequest>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
````

However, what the marshaller generates when MTOM is disabled is:

```xml
  "------=_Part_0_619629247.1730830278669
Content-Type: application/xop+xml; charset=utf-8; type="text/xml"

<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
	<SOAP-ENV:Header/>
	<SOAP-ENV:Body>
		<ns3:MySoapRequest xmlns:ns3="http://my.company.com/xsd/portals/v4_0">
			<binaryData>
				<blob>
					<xop:Include xmlns:xop="http://www.w3.org/2004/08/xop/include" href="cid:aa093c15-eab7-446d-8845-a908a4a4d7f1%40null"/>
				</blob>
				<extension>pdf</extension>
			</binaryData>
		</ns3:MySoapRequest>
	</SOAP-ENV:Body>
</SOAP-ENV:Envelope>
------=_Part_0_619629247.1730830278669
Content-Type: application/pdf
Content-ID: <aa093c15-eab7-446d-8845-a908a4a4d7f1@null>
Content-Transfer-Encoding: binary

123456789ABCDEF0
------=_Part_0_619629247.1730830278669--"
```

...with the blob in an attachment, or alternatively, with MTOM disabled:

```xml
[...]
            <binaryData>
                <blob/>
                <extension>pdf</extension>
            </binaryData>
[...]
```

...with the blob nowhere to be found

# About this repository

* This repository contains the minimum viable code to reproduce this
* The core is the test class `src/test/java/com/tri_tail/jaxb2_marshalling/MySoapClientTest.java` which describes the desired behavior
* The problem is that the `blob` field of the `BinaryData` is a `DataHandler`, and I don't know how to configure either it or the marshaller in such a way that it simply writes the contents directly into the blob tag
* The goal is to get that test to pass

## "Rules"

* Since in actual practice, the SOAP-related classes are dynamically generated at runtime, they must not be modified to solve this
  * In this case, that refers to all the classes in the `generated` package (which in this case are not actually generated, but in a real use case, those would be the classes generated from the WSDL)
* Likely candidates for modification are:
  * `Jaxb2MarshallerConfig `
  * `MySoapClient `