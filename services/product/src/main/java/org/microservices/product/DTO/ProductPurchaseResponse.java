package org.microservices.product.DTO;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String name,
        String description,
        double price,
        int quantity
) {
}
