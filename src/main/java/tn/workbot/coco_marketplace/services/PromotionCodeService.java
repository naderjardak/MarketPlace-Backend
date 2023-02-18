package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.repositories.PromotionCodeRepository;

@Service
public class PromotionCodeService {

    @Autowired
    PromotionCodeRepository promotionCodeRepository;

}
