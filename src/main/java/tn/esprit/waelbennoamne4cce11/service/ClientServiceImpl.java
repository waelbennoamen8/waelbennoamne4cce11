
package tn.esprit.waelbennoamne4cce11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.waelbennoamne4cce11.domaine.Client;
import tn.esprit.waelbennoamne4cce11.exception.ResourceNotFoundException;
import tn.esprit.waelbennoamne4cce11.repository.IClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {

    private static final String NOM_RESSOURCE = "Client";

    private final IClientRepository clientRepository;

    @Override
    public Client create(Client client) {
        verifierClient(client);

        if (client.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant");
        }

        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        verifierId(id);

        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, Client client) {
        verifierId(id);
        verifierClient(client);

        Client existant = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());

        return clientRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        verifierId(id);

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        clientRepository.delete(client);
    }

    private void verifierClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException(
                    "Le client ne peut pas être null");
        }

        if (client.getNom() == null || client.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom du client est obligatoire");
        }

        if (client.getEmail() == null || client.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "L'email du client est obligatoire");
        }
    }

    private void verifierId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "L'identifiant du client doit être positif");
        }
    }
}
