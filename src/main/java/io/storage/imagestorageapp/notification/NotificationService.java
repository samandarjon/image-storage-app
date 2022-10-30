package io.storage.imagestorageapp.notification;

import com.amazonaws.services.sqs.model.Message;

import java.util.List;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */
public interface NotificationService {

    void subscribe(String email);

    void unsubscribe(String email);

}
