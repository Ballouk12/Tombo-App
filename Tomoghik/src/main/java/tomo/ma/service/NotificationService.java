package tomo.ma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomo.ma.dto.State;
import tomo.ma.model.AUser;
import tomo.ma.model.Alert;
import tomo.ma.model.Annonce;
import tomo.ma.model.Notification;
import tomo.ma.repositrory.AlertRepository;
import tomo.ma.repositrory.NotificationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private SmsService smsService;


    public void notify(Annonce annonce) {
        List<Alert> alerts = alertRepository.findAll();

        for (Alert alert : alerts) {
            if (matches(alert, annonce)) {
                // Créer une notification
                Notification notif = new Notification();
                notif.setAlert(alert);
                notif.setAnnonce(annonce);
                notif.setState(State.UNREAD);
                notif.setMessage("Nouvelle annonce correspondante à votre alerte : " + annonce.getBrand() + " " + annonce.getModel());
                notif.setDate(new Date());
                annonce.getNotifications().add(notif);
                alert.getNotifications().add(notif);
                notificationRepository.save(notif);

                // Envoyer SMS
                AUser user = alert.getUser();
                smsService.sendSms(user.getPhoneNumber(), notif.getMessage());
            }
        }

    }

    public List<Notification> getNotificationsByUseId(long useId) {
        List<Notification> notifications = notificationRepository.findByUserId(useId);
        markNotifsAsRead(useId);
        return notifications;
    }

    public boolean markNotifsAsRead(long id) {
        List<Notification> notifications = notificationRepository.findUnReadByUserId(id);
        notifications.stream().forEach( notif -> {
            notif.setState(State.READ);
        });
        notificationRepository.saveAll(notifications);
        return !notifications.isEmpty();
    }

    public boolean deleteNotification(long id) {
        Optional<Notification> notif = notificationRepository.findById(id);
        if (notif.isPresent()) {
            notificationRepository.delete(notif.get());
            return true;
        }
        return false;
    }



    private boolean matches(Alert alert, Annonce annonce) {
        return alert.isActive() &&
                (alert.getBrand() == null || alert.getBrand().equalsIgnoreCase(annonce.getBrand())) &&
                (alert.getModel() == null || alert.getModel().equalsIgnoreCase(annonce.getModel())) &&
                (alert.getFuel_type() == null || alert.getFuel_type().equalsIgnoreCase(annonce.getFuelType())) &&
                (alert.getLocation() == null || alert.getLocation().equalsIgnoreCase(annonce.getLocation())) &&
                (alert.getTransmission() == null || alert.getTransmission().equalsIgnoreCase(annonce.getTransmission())) &&
                (alert.getPrice_min() <= annonce.getPrice() && annonce.getPrice() <= alert.getPrice_max()) &&
                (alert.getMileage_max() == 0 || annonce.getMileage() <= alert.getMileage_max()) &&
                (alert.getYear_min() <= annonce.getYear() && annonce.getYear() <= alert.getYear_max());
    }



}
