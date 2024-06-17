package org.microservices.order.DTO;

public record OrderLineResponse(
        Integer id,
        Integer quantity
) {
}
