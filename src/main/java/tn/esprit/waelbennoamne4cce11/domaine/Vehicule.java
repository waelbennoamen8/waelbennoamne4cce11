package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String immatriculation;

    private String marque;

    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    // Plusieurs véhicules appartiennent à une seule agence
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence")
    private Agence agence;

    // Un véhicule peut avoir plusieurs maintenances
    @OneToMany(
            mappedBy = "vehicule",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<Maintenance> maintenances = new ArrayList<>();

    // Relation ManyToMany avec Equipement
    // Vehicule est le côté propriétaire
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "id_equipement")
    )
    private Set<Equipement> equipements = new HashSet<>();

    // Un véhicule peut avoir plusieurs réservations
    @OneToMany(
            mappedBy = "vehicule",
            fetch = FetchType.LAZY
    )
    private List<Reservation> reservations = new ArrayList<>();
}