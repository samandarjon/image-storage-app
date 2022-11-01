package io.storage.imagestorageapp.listener.notification;

import com.amazonaws.services.sqs.model.Message;
import io.storage.imagestorageapp.notification.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private final NotificationEvent notificationService;

    public void readMessagesFromQueue() {
        List<Message> messages = notificationService.readMessages();
        messages.forEach(message -> notificationService.sendMessageToTopic(message.getBody()));
    }
}
