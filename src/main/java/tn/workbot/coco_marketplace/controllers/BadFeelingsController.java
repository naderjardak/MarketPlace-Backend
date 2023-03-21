package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.BadFeelings;
import tn.workbot.coco_marketplace.services.BadFeelingsService;

import java.util.List;

@RestController
@RequestMapping("BadFeelings")
@PreAuthorize("hasAuthority('ADMIN')")
public class BadFeelingsController {
    @Autowired
    BadFeelingsService badFeelingsService;

    @PostMapping("addNewBadFeelings")
    public void addNewBadFeelings(@RequestBody BadFeelings badFeelings){
        badFeelingsService.addNewBadFeelings(badFeelings);
    }

    @PutMapping("updateBadFeelings")
    public void updateBadFeelings(@RequestBody BadFeelings badFeelings){
        badFeelingsService.updateBadFeelings(badFeelings);
    }

    @DeleteMapping("deleteBadFeelings")
    public void deleteBadFeelings(@RequestParam Long id){
        badFeelingsService.deleteBadFeelings(id);
    }

    @GetMapping("getAllBadFeelings")
    public List<BadFeelings> getAllBadFeelings(){
        return badFeelingsService.getAllBadFeelings();
    }
}
