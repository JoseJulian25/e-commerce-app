package org.microservices.order.DTO;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
