
        package tn.esprit.waelbennoamne4cce11.service;

import tn.esprit.waelbennoamne4cce11.domaine.Maintenance;

import java.util.List;

public interface IMaintenanceService {

    Maintenance create(Maintenance maintenance);

    Maintenance findById(Long id);

    List<Maintenance> findAll();

    Maintenance update(Long id, Maintenance maintenance);

    void deleteById(Long id);
}
