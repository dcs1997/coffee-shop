package com.coffee.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String OrderReference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        String customerFirstName,

        String customerLastName,

        String customerEmail
) {
}
