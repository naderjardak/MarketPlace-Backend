package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import java.util.List;
import java.util.Optional;

@RestController
public class PickupController  {
    @Autowired
    PickupIService pis;


    @PostMapping("addPickup")
    public Pickup addPickup(@RequestBody Pickup pickup) {
        return pis.addPickup(pickup);
    }

    @DeleteMapping("RemovePickup")
    public void removePickup(@RequestParam Long id) {
             pis.removePickup(id);
    }

    @GetMapping("Retrieve Pickup")
    public Pickup RetrievePickup(@RequestParam Long id) {
        return pis.RetrievePickup(id);
    }

    @GetMapping("Retrive All Pickup")
    public List<Pickup> RetrievePickups() {
        return pis.RetrievePickups();
    }
    @PutMapping("Update Pickup")
    public Pickup updatePickup(@RequestBody Pickup pickup){
        return  pis.updatePickup(pickup);
    }
}
