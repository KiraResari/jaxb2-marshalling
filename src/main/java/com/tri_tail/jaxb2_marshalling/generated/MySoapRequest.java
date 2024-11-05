package com.tri_tail.jaxb2_marshalling.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MySoapRequest", namespace = "http://my.company.com/xsd/portals/v4_0")
public class MySoapRequest {

    @XmlElement(required = true)
    protected BinaryData binaryData;

    public BinaryData getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(BinaryData binaryData) {
        this.binaryData = binaryData;
    }
}
