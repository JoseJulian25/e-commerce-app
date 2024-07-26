package org.microservices.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.*;
import org.microservices.order.entities.Order;
import org.microservices.order.grpc.GrpcProductClient;
import org.microservices.order.kafka.OrderProducer;
import org.microservices.order.mapper.OrderMapper;
import org.microservices.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final GrpcProductClient productClientGrpc;


    public Integer createdOrder(OrderRequest request) {
        CustomerResponse customer = new CustomerResponse(
                "66904ee301c1e50c0481a444", "Jose Julian", "Martinez", "jose.julianm2505@gmail.com");

        List<PurchaseResponse> purchaseProducts =  this.productClientGrpc.purchaseProducts(request.products());

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

        return 1;
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
