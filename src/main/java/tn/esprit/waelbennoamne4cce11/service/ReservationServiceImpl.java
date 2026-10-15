
        package tn.esprit.waelbennoamne4cce11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.waelbennoamne4cce11.domaine.Reservation;
import tn.esprit.waelbennoamne4cce11.exception.ResourceNotFoundException;
import tn.esprit.waelbennoamne4cce11.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private static final String NOM_RESSOURCE = "Réservation";

    private final IReservationRepository reservationRepository;

    @Override
    public Reservation create(Reservation reservation) {
        verifierReservation(reservation);

        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant");
        }

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        verifierReservation(reservation);

        Reservation existante = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());
        existante.setClient(reservation.getClient());
        existante.setVehicule(reservation.getVehicule());

        return reservationRepository.save(existante);
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(NOM_RESSOURCE, id));

        reservationRepository.delete(reservation);
    }

    private void verifierReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException(
                    "La réservation ne peut pas être null");
        }

        if (reservation.getDateDebut() == null
                || reservation.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }

        if (reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }

        if (reservation.getClient() == null) {
            throw new IllegalArgumentException("Le client est obligatoire");
        }

        if (reservation.getVehicule() == null) {
            throw new IllegalArgumentException("Le véhicule est obligatoire");
        }
    }
}
