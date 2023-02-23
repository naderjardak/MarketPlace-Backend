package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.Pickup;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.services.interfaces.PickupIService;

import java.util.List;

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

    @GetMapping("RetrievePickup")
    public Pickup RetrievePickup(@RequestParam Long id) {
        return pis.RetrievePickup(id);
    }

    @GetMapping("RetriveAllPickup")
    public List<Pickup> RetrievePickups() {
        return pis.RetrievePickups();
    }
    @PutMapping("UpdatePickup")
    public Pickup updatePickup(@RequestBody Pickup pickup){
        return  pis.updatePickup(pickup);
    }
    @PostMapping("AssignPickupByOder")
    public Pickup AssignPickupByOder(@RequestBody Pickup pickup, @RequestParam Long id) {
        return pis.AssignPickupByOder(pickup, id);
    }
    @GetMapping("RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen")
    public List<Pickup> RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(@RequestParam Long id){
        return  pis.RetrievePickupsByGovernoratBetweenPickupAndStoreAndDeliveryAgencyMen(id);
    }
    @GetMapping("RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer")
    public List<Pickup> RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer() {
        return pis.RetrievePickupsByGovernoratBetweenStoreAndDeliveryMenFreelancer();
    }
    @GetMapping("test")
    public List<Pickup> testretrieve(){
        return pis.testretrieve();
    }

    @GetMapping("RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat")
    public List<Pickup> RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat() {
        return pis.RetrievePickupsbetweenAgencyBranchAndStoreInTheSomeGovernorat();
    }
 /*   @PostMapping("AssignPickupBySeller")
    public Pickup AssignPickupBySeller(@RequestBody Pickup pickup){
        return pis.AssignPickupBySeller(pickup);
    }*/
}
