package com.coffee.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(String id,
                              @NotNull(message = "Customer first name can't be null")
                              String firstName,
                              @NotNull(message = "Customer last name can't be null")
                              String lastName,
                              @NotNull(message = "Customer email can't be null")
                              @Email(message = "Customer email is not valid")
                              String email,
                              Address address) {
}
