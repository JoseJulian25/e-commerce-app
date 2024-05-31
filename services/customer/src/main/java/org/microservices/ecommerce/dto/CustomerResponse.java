package org.microservices.ecommerce.dto;


import org.microservices.ecommerce.entities.Address;

public record CustomerResponse (
        String id,
        String firstname,
        String lastname,
        String email,
        Address address
){}
