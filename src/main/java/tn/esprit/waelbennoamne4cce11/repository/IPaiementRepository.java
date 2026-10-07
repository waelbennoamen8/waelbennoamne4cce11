package tn.esprit.waelbennoamne4cce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.waelbennoamne4cce11.domaine.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}