package org.microservices.order.clients;

import org.microservices.order.DTO.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Primary
@FeignClient(name = "customer-service",
        url = "${application.config.customer-url}",
        configuration = {CustomErrorDecoder.class})
public interface CustomerClient {

    @GetMapping("/{customerId}")
    Optional<CustomerResponse> findCustomerById(@PathVariable(value = "customerId") String customerId);
}
