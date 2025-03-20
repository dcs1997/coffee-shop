package com.coffee.ecommerce.kafka.order;

import java.math.BigDecimal;

public record Product(
        Integer ProductId,
        String name,
        String Description,

        BigDecimal price,
        double quantity
) {
}
