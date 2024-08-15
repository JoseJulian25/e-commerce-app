package org.microservices.order.clients;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.microservices.order.exception.CustomerNotFoundException;
import org.microservices.order.exception.ServiceUnavailableException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 404 -> new CustomerNotFoundException("Customer Not Found");
            case 503 -> new ServiceUnavailableException("Customer API is unavailable");
            default -> new Exception("Exception while getting customer details");
        };
    }
}
