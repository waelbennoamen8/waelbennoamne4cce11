
package tn.esprit.waelbennoamne4cce11.service;

import tn.esprit.waelbennoamne4cce11.domaine.Reservation;
import java.util.List;

public interface IReservationService {

    Reservation create(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Long id, Reservation reservation);

    void deleteById(Long id);
}
