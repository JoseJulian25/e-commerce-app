package org.microservices.product.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Integer id,
         @NotNull(message = "Product name is required")
         String name,
         @NotNull(message = "Product description is required")
         String description,
         @Positive(message = "availableQuantity should be positive")
         int availableQuantity,
         @Positive(message = "price should be positive")
         BigDecimal price,
         @NotNull(message = "category is required")
         Integer categoryId
) {
}
