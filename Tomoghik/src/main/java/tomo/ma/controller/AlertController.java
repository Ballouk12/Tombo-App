package tomo.ma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tomo.ma.model.Alert;
import tomo.ma.service.AlertService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("alert")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("create")
    public ResponseEntity<String> createAlert(@RequestBody Alert alert) {
        if(alertService.saveAlert(alert).isPresent()) { return ResponseEntity.ok("Alert created"); }
        else { return ResponseEntity. status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable("id") int id, @RequestBody Alert alert) {
        Optional<Alert> updatedAlert = alertService.updateAlert(alert);
        if(updatedAlert.isPresent()) { return ResponseEntity.ok(updatedAlert.get()); }
        else { return ResponseEntity. status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteAlert(@PathVariable("id") int id) {
        if(alertService.deleteAlert(id)) { return ResponseEntity.ok("Alert deleted"); }
        else { return ResponseEntity. status(HttpStatus.NOT_FOUND).build(); }
    }

    @DeleteMapping("dleleteall/{idUser}")
    public ResponseEntity<String> deleteAllAlert(@PathVariable("idUser") long idUser) {
        if(alertService.deleteAllAlertsByUserId(idUser)) { return ResponseEntity.ok("Alerts deleted"); }
        else { return ResponseEntity. status(HttpStatus.NOT_FOUND).build(); }
    }

    @GetMapping("getall/{id}")
    public ResponseEntity<List<Alert>> getAlerts(@PathVariable long id) {
        Optional<List<Alert>> alerts = alertService.getAllAlertsByUser(id);
        if(alerts.isPresent()) { return ResponseEntity.ok(alerts.get()); }
        else { return ResponseEntity. status(HttpStatus.NOT_FOUND).build(); }
    }

}
