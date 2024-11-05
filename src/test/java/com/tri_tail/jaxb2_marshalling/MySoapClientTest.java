package com.tri_tail.jaxb2_marshalling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ws.soap.SoapMessage;

@SpringBootTest
public class MySoapClientTest {
    @Autowired
    MySoapClient client;
    @Autowired
    MySoapInterceptor interceptor;

    @BeforeEach
    void setup(){
        interceptor.clear();
    }

    @Test
    void marshallerShouldCreateCorrectRequest() throws IOException{
        BlobRequest blobRequest = new BlobRequest();
        blobRequest.setBlob("123456789ABCDEF0");
        blobRequest.setExtension("pdf");

        client.sendDocuments(blobRequest);

        SoapMessage request = interceptor.getRequests().get(0);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        request.writeTo(out);
        String soapMessageString = out.toString(StandardCharsets.UTF_8.name());
        assertThat(soapMessageString).contains("<blob>123456789ABCDEF0</blob>");
    }
}
