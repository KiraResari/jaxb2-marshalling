package com.tri_tail.jaxb2_marshalling;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import com.tri_tail.jaxb2_marshalling.generated.BinaryData;
import com.tri_tail.jaxb2_marshalling.generated.MySoapRequest;

import jakarta.activation.DataHandler;

@Component
public class MySoapClient extends WebServiceGatewaySupport {
    private final WebServiceTemplate template;

    public MySoapClient (
        Jaxb2Marshaller marshaller,
        MySoapInterceptor soapInterceptor
    ) {
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setDefaultUri("http://www.test.com");
        template = getWebServiceTemplate();
        template.setInterceptors(new ClientInterceptor[] { soapInterceptor });
    }

    public void sendDocuments(BlobRequest blobRequest) {
        var binaryData = new BinaryData();
        DataHandler dataHandler = new DataHandler(blobRequest.getBlob(), "application/pdf");
        binaryData.setBlob(dataHandler);
        binaryData.setExtension(blobRequest.getExtension());
        var soapRequest = new MySoapRequest();
        soapRequest.setBinaryData(binaryData);

        try {
            template.marshalSendAndReceive(soapRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
