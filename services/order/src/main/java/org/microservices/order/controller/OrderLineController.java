package org.microservices.order.controller;

import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.OrderLineResponse;
import org.microservices.order.service.OrderLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-line")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService service;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderId(@PathVariable Integer orderId){
        return ResponseEntity.ok(service.findAllByOrderId(orderId));
    }
}
