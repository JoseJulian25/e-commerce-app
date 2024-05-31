package org.microservices.ecommerce.mapper;

import org.microservices.ecommerce.dto.CustomerRequest;
import org.microservices.ecommerce.dto.CustomerResponse;
import org.microservices.ecommerce.entities.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getLastname(),
                customer.getAddress()
        );
    }
}
