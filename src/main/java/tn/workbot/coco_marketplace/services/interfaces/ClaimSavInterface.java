package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.ClaimSav;

import java.util.List;

public interface ClaimSavInterface {
    List<ClaimSav> getAllClaims();
    ClaimSav getClaimById(Long id);
    void addClaim(ClaimSav claim);
    void updateClaim(ClaimSav claim);
    void deleteClaim(Long id);
}
