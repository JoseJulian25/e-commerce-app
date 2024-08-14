package org.microservices.order.clients;

import lombok.extern.slf4j.Slf4j;
import org.microservices.order.DTO.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CustomerClientFallBack implements CustomerClient{

    @Override
    public Optional<CustomerResponse> findCustomerById(String customerId) {
        log.error("fallback triggered for findCustomer with id: {}", customerId);

        return Optional.empty();
    }
}
