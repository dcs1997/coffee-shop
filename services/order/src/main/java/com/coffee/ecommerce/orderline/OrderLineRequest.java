package com.coffee.ecommerce.orderline;

public record OrderLineRequest(Integer id,
                               Integer oderId,
                               Integer productId,
                               double quantity) {
}
