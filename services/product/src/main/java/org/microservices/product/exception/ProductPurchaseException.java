package org.microservices.product.exception;

public class ProductPurchaseException extends RuntimeException {

    public ProductPurchaseException(String s) {
        super(s);
    }
}
