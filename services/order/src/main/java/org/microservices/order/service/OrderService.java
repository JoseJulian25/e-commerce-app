package org.microservices.order.service;

import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.CustomerResponse;
import org.microservices.order.DTO.OrderLineRequest;
import org.microservices.order.DTO.OrderRequest;
import org.microservices.order.clients.CustomerClient;
import org.microservices.order.clients.ProductClient;
import org.microservices.order.entities.Order;
import org.microservices.order.exception.BusinessException;
import org.microservices.order.mapper.OrderMapper;
import org.microservices.order.product.PurchaseRequest;
import org.microservices.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(OrderRequest request) {
        CustomerResponse customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("No customer exists with the provide id: " + request.customerId()));

        this.productClient.purchaseProducts(request.products());

        Order order = this.orderRepository.save(orderMapper.toOrder(request));

        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity())
            );
        }

        return null;
    }
}
