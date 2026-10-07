package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaintenance;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private String description;

    // Plusieurs maintenances concernent un seul véhicule
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicule")
    private Vehicule vehicule;
}