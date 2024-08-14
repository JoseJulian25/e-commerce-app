package org.microservices.order.DTO;

import org.microservices.order.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Integer orderId,
        String orderReference,
        double totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
