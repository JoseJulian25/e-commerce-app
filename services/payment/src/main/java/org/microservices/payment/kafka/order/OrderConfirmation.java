package org.microservices.payment.kafka.order;

import org.microservices.payment.DTO.Customer;
import org.microservices.payment.entities.PaymentMethod;

import java.math.BigDecimal;

public record OrderConfirmation(
        Integer orderId,
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer
) {
}
