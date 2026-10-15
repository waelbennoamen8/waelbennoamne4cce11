 package tn.esprit.waelbennoamne4cce11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.waelbennoamne4cce11.domaine.Maintenance;
import tn.esprit.waelbennoamne4cce11.exception.ResourceNotFoundException;
import tn.esprit.waelbennoamne4cce11.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private static final String NOM_RESSOURCE = "Maintenance";

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        verifierMaintenance(maintenance);

        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }

        return maintenanceRepository.save(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance findById(Long id) {
        verifierId(id);

        return maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        verifierId(id);
        verifierMaintenance(maintenance);

        Maintenance existante = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());
        existante.setCout(maintenance.getCout());
        existante.setVehicule(maintenance.getVehicule());

        return maintenanceRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        verifierId(id);

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        maintenanceRepository.delete(maintenance);
    }

    private void verifierMaintenance(Maintenance maintenance) {
        if (maintenance == null) {
            throw new IllegalArgumentException(
                    "La maintenance ne peut pas être null");
        }

        if (maintenance.getDateDebut() == null
                || maintenance.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }

        if (maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }

        if (maintenance.getCout() == null || maintenance.getCout() < 0) {
            throw new IllegalArgumentException(
                    "Le coût de maintenance doit être positif ou nul");
        }

        if (maintenance.getVehicule() == null) {
            throw new IllegalArgumentException(
                    "Le véhicule associé à la maintenance est obligatoire");
        }
    }

    private void verifierId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "L'identifiant de maintenance doit être positif");
        }
    }
}
