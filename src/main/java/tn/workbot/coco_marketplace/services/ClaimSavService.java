package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.ClaimSav;
import tn.workbot.coco_marketplace.repositories.ClaimSavRepository;
import tn.workbot.coco_marketplace.services.interfaces.ClaimSavInterface;

import java.util.Date;
import java.util.List;

@Service
public class ClaimSavService implements ClaimSavInterface {
    @Autowired
    ClaimSavRepository crp;

    @Override
    public List<ClaimSav> getAllClaims() {
        return crp.findAll();
    }

    @Override
    public ClaimSav getClaimById(Long id) {
        return crp.findById(id).orElse(null);
    }

    @Override
    public void addClaim(ClaimSav claim) {
        claim.setCreatedAt(new Date());
        claim.setUpdatedAt(new Date());
        crp.save(claim);
    }

    @Override
    public void updateClaim(ClaimSav claim) {
        claim.setUpdatedAt(new Date());
        crp.save(claim);
    }

    @Override
    public void deleteClaim(Long id) {
        crp.deleteById(id);
    }
}