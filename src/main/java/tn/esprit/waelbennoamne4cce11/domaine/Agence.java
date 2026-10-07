package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;

    private String ville;

    private String adresse;

    private String telephone;

    // Une agence possède plusieurs employés
    @OneToMany(
            mappedBy = "agence",
            fetch = FetchType.LAZY
    )
    private List<Employe> employes = new ArrayList<>();

    // Une agence possède plusieurs véhicules
    @OneToMany(
            mappedBy = "agence",
            fetch = FetchType.LAZY
    )
    private List<Vehicule> vehicules = new ArrayList<>();
}