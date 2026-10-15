
        package tn.esprit.waelbennoamne4cce11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.waelbennoamne4cce11.domaine.Vehicule;
import tn.esprit.waelbennoamne4cce11.exception.ResourceNotFoundException;
import tn.esprit.waelbennoamne4cce11.repository.IVehiculeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private static final String NOM_RESSOURCE = "Véhicule";

    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule createVehicule(Vehicule vehicule) {
        verifierVehicule(vehicule);

        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant");
        }

        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(Vehicule vehicule) {
        if (vehicule == null || vehicule.getIdVehicule() == null) {
            throw new IllegalArgumentException(
                    "L'identifiant du véhicule est obligatoire");
        }

        verifierVehicule(vehicule);

        Vehicule existant = vehiculeRepository
                .findById(vehicule.getIdVehicule())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                NOM_RESSOURCE, vehicule.getIdVehicule()));

        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        existant.setAgence(vehicule.getAgence());

        return vehiculeRepository.save(existant);
    }

    @Override
    public void deleteVehiculeById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        vehiculeRepository.delete(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehicule> getVehiculeById(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    private void verifierVehicule(Vehicule vehicule) {
        if (vehicule == null) {
            throw new IllegalArgumentException(
                    "Le véhicule ne peut pas être null");
        }

        if (vehicule.getTarifJournalier() == null) {
            throw new IllegalArgumentException(
                    "Le tarif journalier est obligatoire");
        }

        if (vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier ne peut pas être négatif");
        }

        if (vehicule.getImmatriculation() == null
                || vehicule.getImmatriculation().isBlank()) {
            throw new IllegalArgumentException(
                    "L'immatriculation est obligatoire");
        }

        if (vehicule.getMarque() == null
                || vehicule.getMarque().isBlank()) {
            throw new IllegalArgumentException(
                    "La marque est obligatoire");
        }

        if (vehicule.getModele() == null
                || vehicule.getModele().isBlank()) {
            throw new IllegalArgumentException(
                    "Le modèle est obligatoire");
        }

        if (vehicule.getCategorie() == null) {
            throw new IllegalArgumentException(
                    "La catégorie du véhicule est obligatoire");
        }

        if (vehicule.getStatut() == null) {
            throw new IllegalArgumentException(
                    "Le statut du véhicule est obligatoire");
        }
    }
}