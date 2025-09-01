package tomo.ma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;
    private String model;
    private String fuelType;
    private String location;
    private float price;
    @Min(value = 1990)
    private int year;
    @Min(value = 0)
    private int mileage;
    private String transmission;
    @Column(length = 5000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AUser user;
    @OneToMany(mappedBy = "annonce" , cascade = CascadeType.ALL , orphanRemoval = true  )
    @JsonIgnore
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "annonce" , cascade = CascadeType.ALL , orphanRemoval = true  )
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "annonce" ,cascade = CascadeType.ALL , orphanRemoval = true  )
    private List<Defect> defects = new ArrayList<>();

}
