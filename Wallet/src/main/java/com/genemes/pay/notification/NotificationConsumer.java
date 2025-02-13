package com.genemes.pay.notification;

import com.genemes.pay.exception.UnathorizedException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class NotificationConsumer {
    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.genemes.com")
                .build();
    }

    @KafkaListener(topics = "wallet-notifications", groupId = "wallet-group")
    public void receiveNotification(Notification notification) {
        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if(response.getStatusCode().isError()
                || Boolean.FALSE.equals(Objects.requireNonNull(response.getBody()).message())) {
            throw new UnathorizedException("Notification failed");
        }
    }
}
