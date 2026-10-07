package tn.esprit.waelbennoamne4cce11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tn.esprit.waelbennoamne4cce11.domaine.Vehicule;
import tn.esprit.waelbennoamne4cce11.domaine.StatutVehicule;
import tn.esprit.waelbennoamne4cce11.domaine.CategorieVehicule;
import tn.esprit.waelbennoamne4cce11.repository.IVehiculeRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class Waelbennoamne4cce11Application {

    private static final Logger logger =
            LoggerFactory.getLogger(Waelbennoamne4cce11Application.class);

    public static void main(String[] args) {
        SpringApplication.run(
                Waelbennoamne4cce11Application.class,
                args
        );
    }

    @Bean
    CommandLineRunner testVehiculeRepository(
            IVehiculeRepository vehiculeRepository) {

        return args -> {

            // =========================
            // 1. SAVE
            // =========================

            Vehicule vehicule = new Vehicule();

            vehicule.setImmatriculation("123-TUN-456");
            vehicule.setMarque("Toyota");
            vehicule.setModele("Corolla");

            // Correction : la catégorie est obligatoire
            vehicule.setCategorie(CategorieVehicule.CITADINE);

            vehicule.setTarifJournalier(
                    new BigDecimal("150.00")
            );

            vehicule.setStatut(
                    StatutVehicule.DISPONIBLE
            );

            Vehicule vehiculeSaved =
                    vehiculeRepository.save(vehicule);

            logger.info(
                    "Véhicule enregistré avec ID : {}",
                    vehiculeSaved.getIdVehicule()
            );


            // =========================
            // 2. FIND BY ID
            // =========================

            Long idVehicule =
                    vehiculeSaved.getIdVehicule();

            vehiculeRepository.findById(idVehicule)
                    .ifPresent(vehiculeTrouve ->
                            logger.info(
                                    "Véhicule trouvé : ID = {}, immatriculation = {}, marque = {}, modèle = {}, catégorie = {}, tarif = {} DT/jour, statut = {}",
                                    vehiculeTrouve.getIdVehicule(),
                                    vehiculeTrouve.getImmatriculation(),
                                    vehiculeTrouve.getMarque(),
                                    vehiculeTrouve.getModele(),
                                    vehiculeTrouve.getCategorie(),
                                    vehiculeTrouve.getTarifJournalier(),
                                    vehiculeTrouve.getStatut()
                            )
                    );


            // =========================
            // 3. FIND ALL
            // =========================

            logger.info("Liste des véhicules :");

            vehiculeRepository.findAll()
                    .forEach(vehiculeTrouve ->
                            logger.info(
                                    "{} - {} - {} {} - Catégorie : {} - {} DT/jour - Statut : {}",
                                    vehiculeTrouve.getIdVehicule(),
                                    vehiculeTrouve.getImmatriculation(),
                                    vehiculeTrouve.getMarque(),
                                    vehiculeTrouve.getModele(),
                                    vehiculeTrouve.getCategorie(),
                                    vehiculeTrouve.getTarifJournalier(),
                                    vehiculeTrouve.getStatut()
                            )
                    );


            // =========================
            // 4. DELETE BY ID
            // =========================

            vehiculeRepository.deleteById(idVehicule);

            logger.info(
                    "Véhicule supprimé avec ID : {}",
                    idVehicule
            );
        };
    }
}