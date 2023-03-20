package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.services.interfaces.LastVuedInterface;

import java.util.List;

@RestController
@RequestMapping("orderLastVued")
public class LastVuedController {
    @Autowired
    LastVuedInterface lastVuedInterface;

    @PostMapping("CreateNewVued")
    public void createNewVueOrAddNb(@RequestParam Long id){lastVuedInterface.createNewVueOrAddNb(id);}

    @PostMapping("DisplayAllLastVued")
    public List<Product> afficherLastVued(){ return lastVuedInterface.afficherLastVued();}

}
