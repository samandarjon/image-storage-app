package io.storage.imagestorageapp.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Samandar_Akbarov
 * Date: 10/28/2022
 */

@RequiredArgsConstructor
@RestController
public class NotificationController {
    private final NotificationService notificationService;

        @GetMapping("/api/subscribe")
    public ResponseEntity<String> doSubscribe(@RequestParam String email) {
        notificationService.subscribe(email);
        return ResponseEntity.ok("Send confirmation email");
    }

    @GetMapping("/api/unsubscribe")
    public ResponseEntity<String> doUnsubscribe(@RequestParam String email) {
        notificationService.unsubscribe(email);
        return ResponseEntity.ok("Subscription is deleted");
    }
}
