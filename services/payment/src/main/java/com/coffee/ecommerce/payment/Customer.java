package com.coffee.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
       @NotNull(message = "FirstName is required")
       String firstName,
        @NotNull(message = "lastName is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Customer email is not correctly formatted")
        String email
) {
}
