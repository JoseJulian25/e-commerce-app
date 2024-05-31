package org.microservices.ecommerce.repository;

import org.microservices.ecommerce.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
