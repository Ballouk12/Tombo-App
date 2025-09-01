package tomo.ma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tomo.ma.service.StatistiquesService;

import java.util.Map;

@RestController
@RequestMapping("stats")
public class StatistiquesController {

    @Autowired
    private StatistiquesService statistiquesService;
    @GetMapping("all/{userId}")
    public ResponseEntity<Map<String ,Integer>> getStatistiques(@PathVariable long userId){
        Map<String ,Integer>  statistiques = statistiquesService.statistiques(userId);
        if(statistiques == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statistiques);
    }
}
