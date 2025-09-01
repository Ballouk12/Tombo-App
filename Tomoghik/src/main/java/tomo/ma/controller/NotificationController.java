package tomo.ma.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tomo.ma.service.NotificationService;

@RestController
@RequestMapping("notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("getall/{id}")
    public ResponseEntity<?> getNotifications(@PathVariable long id) {
        return ResponseEntity.ok(notificationService.getNotificationsByUseId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable long id) {
        if(notificationService.deleteNotification(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
