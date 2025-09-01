package tomo.ma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String location;
    private String brand;
    private String model;
    @Min(value = 1990)
    @Max(value = 2025)
    private int year_min;
    @Min(value = 1990)
    @Max(value = 2025)
    private int year_max;
    private double price_min ;
    private double price_max;
    @Min(value = 0)
    private int mileage_max;
    private String fuel_type;
    private String transmission;
    private boolean has_defects;
    private boolean active;
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AUser user;

    @OneToMany(mappedBy = "alert")
    @JsonIgnore  // Changer de JsonManagedReference à JsonIgnore
    private List<Notification> notifications;
}