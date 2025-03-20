package com.coffee.ecommerce.order;

import com.coffee.ecommerce.customer.CustomerClient;
import com.coffee.ecommerce.exception.BusinessException;
import com.coffee.ecommerce.kafka.OrderProducer;
import com.coffee.ecommerce.orderline.OrderLineRequest;
import com.coffee.ecommerce.orderline.OrderLineService;
import com.coffee.ecommerce.payment.PaymentClient;
import com.coffee.ecommerce.payment.PaymentRequest;
import com.coffee.ecommerce.product.ProductClient;
import com.coffee.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


    public Integer createOrder(OrderRequest orderRequest) {
        // check the customer --> openFeign
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exist with the provided id"+orderRequest.customerId()));


/*
we can use same way for product using openFeign but just to implement something new we are using RestTemplate

Something new to learn

 */

        //purchase the product using product microservice
var purchasedProducts= this.productClient.purchaseProducts(orderRequest.products());

        //persist order object
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

for(PurchaseRequest purchaseRequest : orderRequest.products()){
    orderLineService.saveOrderLine(
            new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
            )
    );

}


        //start payment process
        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        //send order conformation -> notification message (kafka)
orderProducer.sendOrderConfirmation(
        new OrderConfirmation(
                orderRequest.reference(),
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                customer,
                purchasedProducts
        )
);

        return order.getId();
    }

    public List<OrderResponse> findAll() {
     return   orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with provided id: %d", orderId)));
    }
}
