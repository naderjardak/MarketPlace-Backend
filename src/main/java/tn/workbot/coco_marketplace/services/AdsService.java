package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.AdsRepository;
import tn.workbot.coco_marketplace.services.interfaces.AdsInterface;

@Service
public class AdsService implements AdsInterface {
    @Autowired
    AdsRepository ar;
}
