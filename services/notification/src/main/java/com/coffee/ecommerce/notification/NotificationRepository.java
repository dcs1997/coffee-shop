package com.coffee.ecommerce.notification;

import com.coffee.ecommerce.kafka.payment.PaymentConfirmation;
import com.coffee.ecommerce.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
