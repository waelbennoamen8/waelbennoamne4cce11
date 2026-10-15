
package tn.esprit.waelbennoamne4cce11.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.waelbennoamne4cce11.domaine.Vehicule;
import tn.esprit.waelbennoamne4cce11.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeRestController {

    private final IVehiculeService vehiculeServices;

    public VehiculeRestController(IVehiculeService vehiculeServices) {
        this.vehiculeServices = vehiculeServices;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule createVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeServices.createVehicule(vehicule);
    }

    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeServices.getAllVehicules();
    }

    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@PathVariable Long id) {
        return vehiculeServices.getVehiculeById(id)
                .orElseThrow(() ->
                        new org.springframework.web.server.ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Véhicule introuvable"));
    }

    @PutMapping("/{id}")
    public Vehicule updateVehicule(
            @PathVariable Long id,
            @RequestBody Vehicule vehicule) {
        vehicule.setIdVehicule(id);
        return vehiculeServices.updateVehicule(vehicule);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicule(@PathVariable Long id) {
        vehiculeServices.deleteVehiculeById(id);
    }

}
