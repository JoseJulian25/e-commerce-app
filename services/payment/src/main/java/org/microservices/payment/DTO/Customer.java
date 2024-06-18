package org.microservices.payment.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
    String id,
    @NotBlank(message = "Firstname is required")
    String firstname,
    @NotBlank(message = "lastname is required")
    String lastname,
    @NotBlank(message = "email is required")
    @Email(message = "The customer email is not valid")
    String email
) {
}
