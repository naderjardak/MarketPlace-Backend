package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.ClaimSav;
import tn.workbot.coco_marketplace.services.ClaimSavService;
import tn.workbot.coco_marketplace.services.interfaces.ClaimSavInterface;

import java.util.List;

@RestController
@RequestMapping("claims")
public class ClaimSavController {

    @Autowired
    ClaimSavService claimSavService;

    @GetMapping("GetAllClaims")
    public List<ClaimSav> getAllClaims() {
        return claimSavService.getAllClaims();
    }

    @GetMapping("GetClaimsById")
    public ClaimSav getClaimById(@RequestParam Long id){
        return claimSavService.getClaimById(id);
    }


    @PostMapping("AddClaim")
    public void addClaim(@RequestBody ClaimSav claim){
        claimSavService.addClaim(claim);
    }

    @PutMapping("UpdateClaim")
    public void updateClaim(@RequestBody ClaimSav claim){
        claimSavService.updateClaim(claim);
    }

    @DeleteMapping("DeleteClaim")
    public void deleteClaim(@RequestParam Long id){
        claimSavService.deleteClaim(id);
    }


}
