package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Ads;
import tn.workbot.coco_marketplace.services.interfaces.AdsInterface;

@RestController
public class AdsController {
    @Autowired
    AdsInterface ai;
    @PostMapping ("AssignAdsToProduct")
    public Ads assignAdsToProduct(@RequestBody Ads ads,@RequestParam Long idProduct) {
        return ai.assignAdsToProduct(ads,idProduct);
    }
    }
