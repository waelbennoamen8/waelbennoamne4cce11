package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private String numPermis;

    private LocalDate dateInscription;

    // Un client peut avoir plusieurs réservations
    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<Reservation> reservations = new ArrayList<>();
}