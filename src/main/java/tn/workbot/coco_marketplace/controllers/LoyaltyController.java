package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.repositories.LoyaltyRepository;
import tn.workbot.coco_marketplace.services.interfaces.LoyaltyInterface;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("Loyalty")
@PreAuthorize("hasAuthority('BUYER') || hasAuthority('Admin')")

public class LoyaltyController {
    @Autowired
    LoyaltyRepository loyaltyRepository;

    @Autowired
    LoyaltyInterface loyaltyInterface;


    @GetMapping("LoyaltyToken")
    public String generateLoyaltyLink() {return  loyaltyInterface.generateLoyaltyLink();}


    @GetMapping("claim")
    public ResponseEntity<Void> claimReward(@RequestParam String link) {return loyaltyInterface.claimReward(link);}

    @GetMapping("points")
    public int pointsClaimed(){return loyaltyInterface.claimedPaoints();}

}
