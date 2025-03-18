package com.coffee.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductPurchaseResponse(Integer id,

                                      String name,

                                      String description,

                                      double quantity,

                                      BigDecimal price) {
}
