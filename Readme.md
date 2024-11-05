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
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <SOAP-ENV:Body>
        <MyRequest xmlns="http://my.company.com/xsd/portals/v4_0">
            <binaryData>
                <blob>
                    <xop:Include xmlns:xop="http://www.w3.org/2004/08/xop/include" href="cid:3be5f4d8-50ed-4f88-8e50-778f6cc70c74%40null"/>
                </blob>
                <extension>pdf</extension>
            </binaryData>
        </MyRequest>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

...with the blob in an attachment, or alternatively, with MTOM disabled:

```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <SOAP-ENV:Body>
        <MyRequest xmlns="http://my.company.com/xsd/portals/v4_0">
            <binaryData>
                <blob/>
                <extension>pdf</extension>
            </binaryData>
        </MyRequest>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

...with the blob nowhere to be found

# About this repository

* This repository contains the minimum viable code to reproduce this
* The core is the test class `src/test/java/com/tri_tail/jaxb2_marshalling/MySoapClientTest.java` which describes the desired behavior
* The problem is that the `blob` field of the `BinaryData` is a `DataHandler`, and I don't know how to configure either it or the marshaller in such a way that it simply writes the contents directly into the blob tag
* The goal is to get that test to pass

## "Rules"

* Since in actual practice, the SOAP-related classes are dynamically generated at runtime, they must not be modified to solve this
  * In this case, that refers to the following classes:
    * `BlobRequest`
    * `MySoapRequest`
    * `ObjectFactory`
* Likely candidates for modification are:
  * `Jaxb2MarshallerConfig `
  * `MySoapClient `