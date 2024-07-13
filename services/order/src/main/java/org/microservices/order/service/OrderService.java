package org.microservices.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.*;
import org.microservices.order.clients.CustomerClient;
import org.microservices.order.clients.ProductClient;
import org.microservices.order.entities.Order;
import org.microservices.order.exception.BusinessException;
import org.microservices.order.kafka.OrderProducer;
import org.microservices.order.mapper.OrderMapper;
import org.microservices.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createdOrder(OrderRequest request) {
        CustomerResponse customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("No customer exists with the provide id: " + request.customerId()));

        List<PurchaseResponse> purchaseProducts =  this.productClient.purchaseProducts(request.products());

        Order order = this.orderRepository.save(orderMapper.toOrder(request));

        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null, order.getId(), purchaseRequest.productId(), purchaseRequest.quantity())
            );
        }

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        order.getId(),
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
        ));

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("No order found with the provider id: " + orderId));
    }
}
