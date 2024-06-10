package org.microservices.order.DTO;

public record PurchaseResponse(

        Integer productId,
        String name,
        String description,
        int quantity
) {
}
