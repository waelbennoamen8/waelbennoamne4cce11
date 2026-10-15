
        package tn.esprit.waelbennoamne4cce11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.waelbennoamne4cce11.domaine.Equipement;
import tn.esprit.waelbennoamne4cce11.exception.ResourceNotFoundException;
import tn.esprit.waelbennoamne4cce11.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private static final String NOM_RESSOURCE = "Équipement";

    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement equipement) {
        verifierEquipement(equipement);

        if (equipement.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel équipement ne doit pas avoir d'identifiant");
        }

        return equipementRepository.save(equipement);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement findById(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        verifierEquipement(equipement);

        Equipement existant = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        existant.setLibelle(equipement.getLibelle());

        return equipementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        equipementRepository.delete(equipement);
    }

    private void verifierEquipement(Equipement equipement) {
        if (equipement == null) {
            throw new IllegalArgumentException(
                    "L'équipement ne peut pas être null");
        }

        if (equipement.getLibelle() == null
                || equipement.getLibelle().isBlank()) {
            throw new IllegalArgumentException(
                    "Le libellé de l'équipement est obligatoire");
        }
    }
}
