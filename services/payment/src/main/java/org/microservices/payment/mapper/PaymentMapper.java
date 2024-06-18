package org.microservices.payment.mapper;

import org.microservices.payment.DTO.PaymentRequest;
import org.microservices.payment.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
}
