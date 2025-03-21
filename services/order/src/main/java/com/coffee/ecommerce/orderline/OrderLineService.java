package com.coffee.ecommerce.orderline;

import com.coffee.ecommerce.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {

        var order = Order.builder()
                .id(orderLineRequest.orderId())  // No need to find the order, it's already passed
                .build();

        var orderLine = OrderLine.builder()
                .quantity(orderLineRequest.quantity())
                .order(order)  // Set the existing order here
                .productId(orderLineRequest.productId())
                .build();

        return orderLineRepository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
      return  orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper:: toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
