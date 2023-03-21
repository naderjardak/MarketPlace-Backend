package tn.workbot.coco_marketplace.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.workbot.coco_marketplace.entities.BadWords;
import tn.workbot.coco_marketplace.services.BadWordsService;

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
}
