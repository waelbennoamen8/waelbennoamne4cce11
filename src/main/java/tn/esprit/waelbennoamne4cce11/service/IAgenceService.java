
package tn.esprit.waelbennoamne4cce11.service;

import tn.esprit.waelbennoamne4cce11.domaine.Agence;

import java.util.List;

public interface IAgenceService {

    Agence create(Agence agence);

    Agence findById(Long id);

    List<Agence> findAll();

    Agence update(Long id, Agence agence);

    void deleteById(Long id);
}
