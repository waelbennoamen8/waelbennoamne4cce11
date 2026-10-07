package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;

    private LocalDate dateSignature;

    private BigDecimal montantTotal;

    private boolean valide;

    // Contrat est le côté propriétaire de la relation avec Reservation
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    // Un contrat peut avoir plusieurs paiements
    @OneToMany(
            mappedBy = "contrat",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Paiement> paiements = new ArrayList<>();
}