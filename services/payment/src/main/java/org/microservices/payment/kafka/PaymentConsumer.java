package org.microservices.payment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.microservices.payment.DTO.PaymentRequest;
import org.microservices.payment.kafka.order.OrderConfirmation;
import org.microservices.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-topic")
    public void consumeOrderedProcessPayment(OrderConfirmation order){
        log.info("Consume order topic from Payment Service.");
        PaymentRequest payment = new PaymentRequest(
                order.orderId(),
                order.totalAmount(),
                order.paymentMethod(),
                order.orderReference(),
                order.customer()
        );

        paymentService.createPayment(payment);
    }
}
