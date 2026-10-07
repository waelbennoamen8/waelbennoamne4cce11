package tn.esprit.waelbennoamne4cce11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.waelbennoamne4cce11.domaine.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
}