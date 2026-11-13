package tn.esprit.autoloc.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.entities.Vehicule;
import tn.esprit.autoloc.services.IVehiculeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/vehicule")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @PostMapping
    public Vehicule ajouterVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.ajouterVehicule(vehicule);
    }

    @PutMapping
    public Vehicule modifierVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.modifierVehicule(vehicule);
    }

    @GetMapping
    public List<Vehicule> listerVehicules() {
        return vehiculeService.consulterVehicules();
    }

    @GetMapping("/getById/{idVehicule}")
    public Vehicule getVehiculeById(@PathVariable Long idVehicule) {
        return vehiculeService.consulterVehiculeById(idVehicule);
    }

    @DeleteMapping("/delete/{idVehicule}")
    public void supprimerVehicule(@PathVariable Long idVehicule) {
        vehiculeService.supprimerVehicule(idVehicule);
    }

    @PutMapping({"/affecter-equipement/{idVehicule}/{idEquipement}", "/affecterEquipement/{idVehicule}/{idEquipement}"})
    public Vehicule affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    @PutMapping({"/desaffecter-equipement/{idVehicule}/{idEquipement}", "/desaffecterEquipement/{idVehicule}/{idEquipement}"})
    public Vehicule desaffecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return vehiculeService.desaffecterEquipement(idVehicule, idEquipement);
    }
}
