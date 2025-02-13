package com.genemes.pay.notification;

import com.genemes.pay.trasaction.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final KafkaTemplate<Object, Transaction> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<Object, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Transaction transaction) {
        kafkaTemplate.send("transactions", transaction);
    }
}
