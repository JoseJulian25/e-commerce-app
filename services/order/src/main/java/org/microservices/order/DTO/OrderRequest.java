package org.microservices.order.DTO;

import jakarta.validation.constraints.*;
import org.microservices.order.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        double amount,
        @NotNull(message = "payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "payment method should be present")
        @NotEmpty(message = "payment method should be present")
        @NotBlank(message = "payment method should be present")
        String customerId,
        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {
}
