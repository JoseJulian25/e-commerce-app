package org.microservices.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.OrderRequest;
import org.microservices.order.DTO.OrderResponse;
import org.microservices.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.ok(service.createdOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Integer orderId){
        return ResponseEntity.ok(service.findById(orderId));
    }
}
