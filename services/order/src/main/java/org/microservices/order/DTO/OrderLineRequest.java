package org.microservices.order.DTO;

public record OrderLineRequest(Integer id, Integer orderId, Integer productId ,Integer quantity) {
}
