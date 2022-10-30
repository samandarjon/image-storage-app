package io.storage.imagestorageapp.notification;

import com.amazonaws.services.sqs.model.Message;

import java.util.List;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
public interface NotificationEvent {

    void sendMessageToQueue(String message);

    void sendMessageToTopic(String message);

    List<Message> readMessages();
}
