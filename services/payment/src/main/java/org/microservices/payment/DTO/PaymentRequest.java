package org.microservices.payment.DTO;

import org.microservices.payment.entities.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer orderId,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderReference,
        Customer customer
) {
}
