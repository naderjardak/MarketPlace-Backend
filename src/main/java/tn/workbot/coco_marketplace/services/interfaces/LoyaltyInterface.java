package tn.workbot.coco_marketplace.services.interfaces;

import org.springframework.http.ResponseEntity;

public interface LoyaltyInterface {

    public String generateLoyaltyLink();

    public ResponseEntity<Void> claimReward(String link);

    public int claimedPaoints();


    }
