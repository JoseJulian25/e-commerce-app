package org.microservices.order.DTO;

import java.math.BigDecimal;

public record PurchaseResponse(

        Integer productId,
        String name,
        String description,
        double price,
        int quantity
) {
}
