package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.AgencyBranch;
import tn.workbot.coco_marketplace.entities.AgencyDeliveryMan;
import tn.workbot.coco_marketplace.services.interfaces.AgencyDeliveryManIService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("AgencyDeliveryMan")
@PreAuthorize(" hasAuthority('ADMIN') || hasAuthority('DELIVERYAGENCY')")

public class AgencyDeliveryManController  {
    @Autowired
    AgencyDeliveryManIService ad;
    @PostMapping("addAgencyDeliveryMan")
    public AgencyDeliveryMan addAgencyDeliveryMan(@RequestBody AgencyDeliveryMan agencyDeliveryMan) {
        return ad.addAgencyDeliveryMan(agencyDeliveryMan);
    }

    @DeleteMapping("removeAgencyDeliveryMan")
    public void removeAgencyDeliveryMan(@RequestParam Long id) {
          ad.removeAgencyDeliveryMan(id);
    }

    @GetMapping("RetrieveAgencyDeliveryMan")
    public AgencyDeliveryMan RetrieveAgencyDeliveryMan(@RequestParam Long id) {
        return ad.RetrieveAgencyDeliveryMan(id);
    }

    @GetMapping("RetrieveAgencyDeliveryMen")
    public List<AgencyDeliveryMan> RetrieveAgencyDeliveryMen() {
        return ad.RetrieveAgencyDeliveryMen();
    }
    @PutMapping("UpdateAgencyDeliveryMan")
    public AgencyDeliveryMan UpdateAgencyDeliveryMan(@RequestBody AgencyDeliveryMan agencyDeliveryMan) {
        return ad.UpdateAgencyDeliveryMan(agencyDeliveryMan);
    }
    @PostMapping("AssignAgencyDeliveryManByBranch")
    public AgencyDeliveryMan AssignAgencyDeliveryManByBranch(@RequestBody AgencyDeliveryMan agencyDeliveryMan,@RequestParam Long Id) {
        return ad.AssignAgencyDeliveryManByBranch(agencyDeliveryMan,Id);
    }
    @GetMapping("RetrieveDeliverymenByagencyWhenThegovernorateOfPickupisSomeGovernorateofdeliverymen")
    public List<AgencyDeliveryMan> RetrieveDeliverymenByagencyWhenThegovernorateOfPickupisSomeGovernorateofdeliverymen(Long idPickup) {
        return ad.RetrieveDeliverymenByagencyWhenThegovernorateOfPickupisSomeGovernorateofdeliverymen(idPickup);
    }
    }
