package com.coffee.ecommerce.kafka;

import com.coffee.ecommerce.customer.CustomerResponse;
import com.coffee.ecommerce.order.PaymentMethod;
import com.coffee.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(

        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,

        CustomerResponse customer,

        List<PurchaseResponse> products
) {

}
