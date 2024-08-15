package org.microservices.order.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String m){
        super(m);
    }
}
