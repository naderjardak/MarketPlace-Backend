package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.ClaimSav;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavStatusType;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavType;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.ClaimSavService;

import tn.workbot.coco_marketplace.services.UserService;


import java.util.List;

@RestController
@RequestMapping("claims")
@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('TECHNICALSUPPORT')")
public class ClaimSavController {

    @Autowired
    ClaimSavService claimSavService;

    @Autowired
    UserService userService;
    @Autowired
    UserrRepository ur;



    @GetMapping("GetAllClaims")
    public List<ClaimSav> getAllClaims() {
        return claimSavService.getAllClaims();
    }

    @GetMapping("GetClaimsById")
    public ClaimSav getClaimById(@RequestParam Long id){
        return claimSavService.getClaimById(id);
    }


    @PostMapping("AddClaim")
    public void addClaim(@RequestBody ClaimSav claim,@RequestParam Long idProductQuantity){
        claimSavService.addClaim(claim,idProductQuantity);
    }

    @PutMapping("modifyclaimstatus")
    public void modifyClaimStatus(@RequestParam Long id, @RequestBody ClaimSavStatusType newStatus){
        claimSavService.modifyClaimStatus(id, newStatus);
    }

    @PutMapping("UpdateClaim")
    public void updateClaim(@RequestBody ClaimSav claim){
        claimSavService.updateClaim(claim);
    }

    @DeleteMapping("DeleteClaim")
    public void deleteClaim(@RequestParam Long id){
        claimSavService.deleteClaim(id);
    }


    @GetMapping("GetClaimsByTypeAndStatus")
    public List<ClaimSav> getClaimsByTypeAndStatus(@RequestParam ClaimSavType type, @RequestParam ClaimSavStatusType status) {
        return  claimSavService.getClaimsByTypeAndStatus(type, status);
    }




}
