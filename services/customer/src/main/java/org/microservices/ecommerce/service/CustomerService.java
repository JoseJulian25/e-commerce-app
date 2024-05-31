package org.microservices.ecommerce.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.microservices.ecommerce.dto.CustomerResponse;
import org.microservices.ecommerce.exception.CustomerNotFoundException;
import org.microservices.ecommerce.mapper.CustomerMapper;
import org.microservices.ecommerce.dto.CustomerRequest;
import org.microservices.ecommerce.entities.Customer;
import org.microservices.ecommerce.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        Customer customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request, String id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(
                String.format("Cannot update customer:: No Customer Found with the Provided ID:: %s", id)
        ));

        mergerCustomer(customer, request);
        repository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }
        if(StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream().map(mapper::fromCustomer).toList();
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId).map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No Customer Found with the Provided ID:: %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
