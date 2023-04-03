package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.BadWords;
import tn.workbot.coco_marketplace.services.BadWordsService;

import java.util.List;

@RestController
@RequestMapping("BadWords")
@PreAuthorize("hasAuthority('ADMIN')")

public class BadWordsController {

    @Autowired
    BadWordsService badWordsService;

    @PostMapping("addBadWord")
    public void addNewBadWord(@RequestBody BadWords badWords){
        badWordsService.addNewBadWord(badWords);
    }

    @PutMapping("updateBadWord")
    public void updateBadWords(@RequestBody BadWords badWords){
        badWordsService.updateBadWords(badWords);
    }

    @DeleteMapping("deleteBadWord")
    public void deleteBadWords(@RequestParam Long id){
        badWordsService.deleteBadWords(id);
    }

    @GetMapping("getAllBadWords")
    public List<BadWords> getAllBadWords(){
        return badWordsService.getAllBadWords();
    }
}
