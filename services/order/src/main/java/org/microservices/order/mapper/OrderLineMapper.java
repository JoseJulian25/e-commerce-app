package org.microservices.order.mapper;

import org.microservices.order.DTO.OrderLineRequest;
import org.microservices.order.entities.Order;
import org.microservices.order.entities.OrderLine;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(Order.builder()
                        .id(request.orderId())
                        .build())
                .productId(request.productId())
                .build();
    }
}
