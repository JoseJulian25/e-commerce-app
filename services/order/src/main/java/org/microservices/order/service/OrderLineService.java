package org.microservices.order.service;

import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.OrderLineRequest;
import org.microservices.order.entities.OrderLine;
import org.microservices.order.mapper.OrderLineMapper;
import org.microservices.order.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        OrderLine order = mapper.toOrderLine(request);

        return repository.save(order).getId();
    }
}
