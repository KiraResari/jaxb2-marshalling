package com.tri_tail.jaxb2_marshalling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Jaxb2MarshallerConfig {

    @Bean
    public Jaxb2Marshaller myMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.tri_tail.jaxb2_marshalling.generated");
        marshaller.setMtomEnabled(true); 
        return marshaller;
    }
}
