package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

@Controller
public class StoreController {

    @Autowired
    StoreInterface storeInterface;
}
