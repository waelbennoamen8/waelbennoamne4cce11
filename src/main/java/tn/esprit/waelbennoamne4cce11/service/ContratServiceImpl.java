
package tn.esprit.waelbennoamne4cce11.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.waelbennoamne4cce11.domaine.Contrat;
import tn.esprit.waelbennoamne4cce11.exception.ResourceNotFoundException;
import tn.esprit.waelbennoamne4cce11.repository.IContratRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private static final String NOM_RESSOURCE = "Contrat";

    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat contrat) {
        verifierContrat(contrat);

        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }

        return contratRepository.save(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat findById(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        verifierContrat(contrat);

        Contrat existant = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        existant.setReservation(contrat.getReservation());

        return contratRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        contratRepository.delete(contrat);
    }

    private void verifierContrat(Contrat contrat) {
        if (contrat == null) {
            throw new IllegalArgumentException(
                    "Le contrat ne peut pas être null");
        }

        if (contrat.getMontantTotal() == null) {
            throw new IllegalArgumentException(
                    "Le montant total est obligatoire");
        }

        if (contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le montant total ne peut pas être négatif");
        }
    }
}
