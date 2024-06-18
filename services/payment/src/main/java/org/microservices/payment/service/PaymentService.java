package org.microservices.payment.service;

import lombok.RequiredArgsConstructor;
import org.microservices.payment.DTO.PaymentNotificationRequest;
import org.microservices.payment.DTO.PaymentRequest;
import org.microservices.payment.entities.Payment;
import org.microservices.payment.mapper.PaymentMapper;
import org.microservices.payment.notification.NotificationProducer;
import org.microservices.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        Payment payment = repository.save(mapper.toPayment(request));

        PaymentNotificationRequest paymentNotification = new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email()
        );

        notificationProducer.sendNotification(paymentNotification);
        return payment.getId();
    }
}
