package org.microservices.notification.email;

import lombok.Getter;

@Getter
public enum EmailTemplate {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment-successfully proccessed"),
    ORDER_CONFIRMATION("order-confirmation.html", "order-comfirmation");

    private final String template;
    private final String subject;

    EmailTemplate(String template, String subject){
        this.template = template;
        this.subject = subject;
    }
}
