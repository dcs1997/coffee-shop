package com.coffee.ecommerce.payment;

import com.coffee.ecommerce.customer.CustomerResponse;
import com.coffee.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,

        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,

        CustomerResponse customer
) {
}
