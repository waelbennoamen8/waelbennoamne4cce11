package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    // Plusieurs réservations peuvent appartenir au même client
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    // Plusieurs réservations peuvent concerner le même véhicule
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicule")
    private Vehicule vehicule;

    // Relation inverse avec Contrat
    // Contrat est le côté propriétaire
    @OneToOne(
            mappedBy = "reservation",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Contrat contrat;
}