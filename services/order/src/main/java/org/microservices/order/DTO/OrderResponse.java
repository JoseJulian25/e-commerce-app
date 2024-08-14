package org.microservices.order.DTO;

import org.microservices.order.entities.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        double amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
