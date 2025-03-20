package com.coffee.ecommerce.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment Successfully Processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order Confirmation");

    private final String template;
    private final String subject;
}
