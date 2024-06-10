package org.microservices.order.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "product is mandatory")
        Integer productId,
        @Positive(message = "Quantity should be positive")
        Integer quantity
) {
}
