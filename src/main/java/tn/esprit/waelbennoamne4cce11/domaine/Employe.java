package tn.esprit.waelbennoamne4cce11.domaine;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmploye;

    private String nom;

    private String prenom;

    @Enumerated(EnumType.STRING)
    private RoleEmploye role;

    // Plusieurs employés appartiennent à une seule agence
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence")
    private Agence agence;
}