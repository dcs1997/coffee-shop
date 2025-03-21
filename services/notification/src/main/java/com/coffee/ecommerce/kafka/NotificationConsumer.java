package com.coffee.ecommerce.kafka;

import com.coffee.ecommerce.email.EmailService;
import com.coffee.ecommerce.kafka.order.OrderConfirmation;
import com.coffee.ecommerce.kafka.payment.PaymentConfirmation;
import com.coffee.ecommerce.notification.Notification;
import com.coffee.ecommerce.notification.NotificationRepository;
import com.coffee.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
     private final EmailService emailService;

@KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessfulNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
log.info(String.format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
notificationRepository.save(
        Notification.builder()
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build()
);


var customerName = paymentConfirmation.customerFirstName()+ " "+ paymentConfirmation.customerLastName();
emailService.sendPaymentSuccessEmail(paymentConfirmation.customerEmail(),
        customerName,
        paymentConfirmation.amount(),
        paymentConfirmation.OrderReference());

    }



    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from order-topic Topic:: %s", orderConfirmation));
        notificationRepository.save(
                Notification.builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );


        var customerName = orderConfirmation.customer().firstName()+ " "+ orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products());

    }
}
