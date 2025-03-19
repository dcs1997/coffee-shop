package com.coffee.ecommerce.order;

import com.coffee.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
       @Positive(message= "order amount should be positive")
        BigDecimal amount,
        @Positive(message= "payment method should be precised")
        PaymentMethod paymentMethod,
@NotNull(message ="Customer should be present")
@NotBlank(message ="Customer should be present")
@NotEmpty(message ="Customer should be present")
        String customerId,
@NotEmpty(message = "You need to at least purchase one product")
         List<PurchaseRequest> products


) {
}
