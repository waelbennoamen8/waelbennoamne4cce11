
package tn.esprit.waelbennoamne4cce11.service;

import tn.esprit.waelbennoamne4cce11.domaine.Paiement;

import java.util.List;

public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}
