
package tn.esprit.waelbennoamne4cce11.service;

import tn.esprit.waelbennoamne4cce11.domaine.Vehicule;

import java.util.List;
import java.util.Optional;

public interface IVehiculeServices {

    Vehicule createVehicule(Vehicule vehicule);

    Vehicule updateVehicule(Vehicule vehicule);

    void deleteVehiculeById(Long id);

    Optional<Vehicule> getVehiculeById(Long id);

    List<Vehicule> getAllVehicules();
}
