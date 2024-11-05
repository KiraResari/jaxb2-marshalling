package com.tri_tail.jaxb2_marshalling;

import jakarta.activation.DataHandler;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryData", propOrder = {"blob", "extension"})
public class BinaryData {

    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler blob;

    @XmlElement(required = true)
    protected String extension;

    public DataHandler getBlob() {
        return blob;
    }

    public void setBlob(DataHandler blob) {
        this.blob = blob;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
