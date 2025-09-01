package tomo.ma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomo.ma.repositrory.AlertRepository;
import tomo.ma.repositrory.AnnonceRepository;
import tomo.ma.repositrory.NotificationRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatistiquesService {
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public Map<String , Integer> statistiques(long userId){
        Map<String , Integer> statistiques = new HashMap<String , Integer>();
        int nbrAnnonce = annonceRepository.findByUserId(userId).size();
        int nbrAlert = alertRepository.findByUser(userId).stream()
                .filter(alert -> {return alert.isActive() ;})
                .toList()
                .size();
        int nbrNotification = notificationRepository.findByUserId(userId).size();
        statistiques.put("nbrAnnonce", nbrAnnonce);
        statistiques.put("nbrAlert", nbrAlert);
        statistiques.put("nbrNotification", nbrNotification);
        return statistiques;

    }
}
