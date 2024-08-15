package org.microservices.order.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public record ErrorResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        Date timestamp,
        int code,
        String status,
        String message
){}
