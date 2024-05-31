package org.microservices.ecommerce.dto;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors
){}
