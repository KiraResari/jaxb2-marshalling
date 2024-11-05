package com.tri_tail.jaxb2_marshalling;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.support.interceptor.ClientInterceptorAdapter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import java.util.ArrayList;
import java.util.List;

@Component
public class MySoapInterceptor extends ClientInterceptorAdapter {
    private List<SoapMessage> requests = new ArrayList<>();
    private List<SoapMessage> responses = new ArrayList<>();
    private List<SoapMessage> faults = new ArrayList<>();

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        SoapMessage soapRequest = (SoapMessage) messageContext.getRequest();
        requests.add(soapRequest);
        return false;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        SoapMessage soapResponse = (SoapMessage) messageContext.getResponse();
        responses.add(soapResponse);
        return false; 
    }

    @Override
    public boolean handleFault(MessageContext messageContext) {
        SoapMessage soapFault = (SoapMessage) messageContext.getResponse();
        faults.add(soapFault);
        return false;
    }

    public List<SoapMessage> getRequests(){
        return requests;
    }

    public List<SoapMessage> getResponses(){
        return responses;
    }

    public List<SoapMessage> getFaults(){
        return faults;
    }

    public void clear() {
        requests.clear();
        responses.clear();
        faults.clear();
    }
}