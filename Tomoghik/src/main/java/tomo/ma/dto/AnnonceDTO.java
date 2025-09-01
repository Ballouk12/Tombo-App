package tomo.ma.dto;

// AnnonceDTO.java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceDTO {
    private long id;
    private String brand;
    private String model;
    private String fuelType;
    private String location;
    private float price;
    private int year;
    private int mileage;
    private String transmission;
    private String description;
    private List<String> images; // URLs ou noms d’images
    private List<String> defects; // Liste des défauts
}