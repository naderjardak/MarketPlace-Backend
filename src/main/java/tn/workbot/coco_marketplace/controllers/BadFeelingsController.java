package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.entities.BadFeelings;
import tn.workbot.coco_marketplace.services.BadFeelingsService;

@RestController
@RequestMapping("BadFeelings")
@PreAuthorize("hasAuthority('ADMIN')")
public class BadFeelingsController {
    @Autowired
    BadFeelingsService badFeelingsService;

    @PostMapping("addNewBadFeelings")
    public void addNewBadFeelings(BadFeelings badFeelings){
        badFeelingsService.addNewBadFeelings(badFeelings);
    }
}
