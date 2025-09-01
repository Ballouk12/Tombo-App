package tomo.ma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomo.ma.model.AUser;
import tomo.ma.model.Alert;
import tomo.ma.repositrory.AlertRepository;
import tomo.ma.repositrory.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Alert> saveAlert(Alert alert) {
        alert.setCreated_at(new Date());
        return Optional.ofNullable(alertRepository.save(alert));
    }
    public Optional<Alert> updateAlert(Alert alert) {
        return alertRepository.findById(alert.getId()).map(up_alert -> {
            up_alert.setBrand(alert.getBrand());
            up_alert.setLocation(alert.getLocation());
            up_alert.setModel(alert.getModel());
            up_alert.setMileage_max(alert.getMileage_max());
            up_alert.setPrice_max(alert.getPrice_max());
            up_alert.setPrice_min(alert.getPrice_min());
            up_alert.setHas_defects(alert.isHas_defects());
            up_alert.setActive(alert.isActive());
            up_alert.setFuel_type(alert.getFuel_type());
            up_alert.setTransmission(alert.getTransmission());
            up_alert.setYear_max(alert.getYear_max());
            up_alert.setYear_min(alert.getYear_min());
            up_alert.setCreated_at(new Date());
            return alertRepository.save(up_alert);
        });
    }

    public boolean deleteAlert(long idAlert) {
        return alertRepository.findById(idAlert).map(del_alert -> {
            alertRepository.delete(del_alert);return true ;
        }).orElse(false);
    }


    public Optional<List<Alert>> getAllAlertsByUser(long user_id) {
        return Optional.ofNullable(alertRepository.findByUser(user_id));
    }

    public boolean deleteAllAlertsByUserId(long userId) {
        List<Alert> alerts = alertRepository.findByUser(userId);
        if(alerts.isEmpty()) {return false;}
        else {alertRepository.deleteAll(alerts);return true ;}
    }

}
