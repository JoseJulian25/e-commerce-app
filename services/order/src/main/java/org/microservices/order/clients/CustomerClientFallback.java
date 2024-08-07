package org.microservices.order.clients;

import lombok.extern.slf4j.Slf4j;
import org.microservices.order.DTO.CustomerResponse;
import org.microservices.order.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CustomerClientFallback implements CustomerClient{

    @Override
    public Optional<CustomerResponse> findCustomerById(String customerId) {
        log.warn("Fallback method invoked for findCustomerById with customerId: {}", customerId);
        throw new ServiceUnavailableException("Customer service is currently unavailable. Please try again later.");
    }
}
