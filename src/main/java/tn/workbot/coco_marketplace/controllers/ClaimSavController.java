package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.ClaimSav;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavStatusType;
import tn.workbot.coco_marketplace.entities.enmus.ClaimSavType;
import tn.workbot.coco_marketplace.repositories.UserrRepository;
import tn.workbot.coco_marketplace.services.ClaimSavService;
import tn.workbot.coco_marketplace.services.MailSenderService;
import tn.workbot.coco_marketplace.services.UserService;
import tn.workbot.coco_marketplace.services.interfaces.ClaimSavInterface;

import java.util.List;

@RestController
@RequestMapping("claims")
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

    @Autowired
    private MailSenderService sendMailService;

    @GetMapping("/send-email-to-user-1")
    public String sendEmailToUser() {
        User user = userService.GetById(1);
        String subject = "Example Subject";
        String message = "Hello " + user.getFirstName() + ",\n\nFrom CocoMarcet Claim Service";
        sendMailService.sendEmail(user.getEmail(), subject, message);
        return "Email sent to user w";
    }
}
