package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.ClaimSav;


import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavStatusType;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavType;
import tn.workbot.coco_marketplace.repositories.ClaimSavRepository;
import tn.workbot.coco_marketplace.repositories.ProductQuantityRepository;
import tn.workbot.coco_marketplace.repositories.ProductRepository;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.interfaces.ClaimSavInterface;


import java.util.Date;
import java.util.List;

@Service
public class ClaimSavService implements ClaimSavInterface {
    @Autowired
    ClaimSavRepository crp;
    @Autowired
    UserrRepository ur;

    @Autowired
    ProductQuantityRepository productQuantityRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ClaimSav> getAllClaims() {
        return crp.findAll();
    }

    @Override
    public ClaimSav getClaimById(Long id) {
        return crp.findById(id).orElse(null);
    }

    @Override
    public void addClaim(ClaimSav claim,Long idProductQuantity) {
        claim.setCreatedAt(new Date());
        User user = ur.findById(1L).get();
        claim.setUser(user);
        ProductQuantity productQuantity=productQuantityRepository.findById(1L).get();
        claim.setProductQuantity(productQuantity);
        crp.save(claim);


        }




    @Override
    public void updateClaim(ClaimSav claim) {
        claim.setUpdatedAt(new Date());
        //Session Manager
        User user = ur.findById(1L).get();
        crp.save(claim);
    }

    @Override
    public void deleteClaim(Long id) {
        crp.deleteById(id);
    }

    @Override
    public List<ClaimSav> getClaimsByTypeAndStatus(ClaimSavType type, ClaimSavStatusType status) {
       return crp.getClaimsByTypeAndStatus(type, status);
    }

}