package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.entities.GoodFeelings;
import tn.workbot.coco_marketplace.services.GoodFeelingsService;

@RestController
@RequestMapping("GoodFeelings")
@PreAuthorize("hasAuthority('ROLE!!!')")

public class GoodFeelingsController {
    @Autowired
    GoodFeelingsService goodFeelingsService;

    @PostMapping("addNewGoodFeelings")
    public void addNewGoodFeelings(GoodFeelings goodFeelings){
        goodFeelingsService.addNewGoodFeelings(goodFeelings);
    }
}
