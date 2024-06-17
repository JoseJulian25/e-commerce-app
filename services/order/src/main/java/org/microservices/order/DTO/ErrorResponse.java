package org.microservices.order.DTO;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
){}
