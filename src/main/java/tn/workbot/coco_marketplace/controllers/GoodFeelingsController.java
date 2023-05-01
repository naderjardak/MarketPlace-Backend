package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.GoodFeelings;
import tn.workbot.coco_marketplace.services.GoodFeelingsService;

import java.util.List;

@RestController
@RequestMapping("GoodFeelings")
@PreAuthorize("hasAuthority('ADMIN')")

public class GoodFeelingsController {
    @Autowired
    GoodFeelingsService goodFeelingsService;

    @PostMapping("addNewGoodFeelings")
    public void addNewGoodFeelings(@RequestBody GoodFeelings goodFeelings){
        goodFeelingsService.addNewGoodFeelings(goodFeelings);
    }

    @GetMapping("getAllGoodFeelings")
    public List<GoodFeelings> getAllGoodFeelings(){
        return goodFeelingsService.getAllGoodFeelings();
    }

    @PutMapping("updateGoodFeelings")
    public void updateGoodFeelings(@RequestBody GoodFeelings goodFeelings){
        goodFeelingsService.updateGoodFeelings(goodFeelings);
    }

    @DeleteMapping("deleteGoodFeelings")
    public void deleteGoodFeelings(@RequestParam Long id){
        goodFeelingsService.deleteGoodFeelings(id);
    }
}
