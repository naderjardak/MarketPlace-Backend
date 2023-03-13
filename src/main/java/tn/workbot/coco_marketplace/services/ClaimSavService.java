package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.workbot.coco_marketplace.Api.OrderMailSenderService;
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

    @Autowired
    MailSenderService mailSenderService;

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
        claim.setStatus(ClaimSavStatusType.NONTRAITE);
        User user = ur.findById(1L).get();
        claim.setUser(user);
        ProductQuantity productQuantity=productQuantityRepository.findById(idProductQuantity).get();
        claim.setProductQuantity(productQuantity);
        String subject = "";
        String message = "Hello from CocoMarket Claim Service " +"Mr"+ user.getFirstName() + ",\n\n we inform you that your claim has been registred successfully you will recieve a message when your claim is treated, Thank you";
        mailSenderService.sendEmail(user.getEmail(),subject,message);
        crp.save(claim);


        }




    @Override
    public void updateClaim(ClaimSav claim) {
        claim.setUpdatedAt(new Date());
        //Session Manager
        User user = ur.findById(1L).get();
        //String subject = "";
        //String message = "Hello from CocoMarket Claim Service " +"Mr"+ user.getFirstName() + ",\n\n we inform you that your claim has been updated successfully you will recieve a message when your claim is treated, Thank you";
        //mailSenderService.sendEmail(user.getEmail(),subject,message);
        crp.save(claim);
    }

    @Override
    public void deleteClaim(Long id) {
        crp.deleteById(id);
    }

    @Override
    public void modifyClaimStatus(Long id, ClaimSavStatusType newStatus) {
        ClaimSav claimSav = crp.findById(id).orElseThrow(() -> new NotFoundException("ClaimSav not found"));

        if (newStatus == ClaimSavStatusType.APPROVED) {

        } else if (newStatus == ClaimSavStatusType.REJECTED) {

        } else if (newStatus == ClaimSavStatusType.COMPLETED) {

        }

        claimSav.setStatus(newStatus);
        //String subject = "";
        //String message = "Hello from CocoMarket Claim Service " +"Mr"+ user.getFirstName() + ",\n\n we inform you that your claim has been updated successfully you will recieve a message when your claim is treated, Thank you";
        //mailSenderService.sendEmail(user.getEmail(),subject,message);
        crp.save(claimSav);
    }


    @Override
    public List<ClaimSav> getClaimsByTypeAndStatus(ClaimSavType type, ClaimSavStatusType status) {
       return crp.getClaimsByTypeAndStatus(type, status);
    }

}