package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.PromotionCode;
import tn.workbot.coco_marketplace.repositories.PromotionCodeRepository;
import tn.workbot.coco_marketplace.services.interfaces.PromotionCodeInterface;

import java.util.List;

@Service
public class PromotionCodeService implements PromotionCodeInterface {

    @Autowired
    PromotionCodeRepository promotionCodeRepository;


    @Override
    public PromotionCode create(PromotionCode p) {
        return promotionCodeRepository.save(p);
    }

    @Override
    public PromotionCode update(PromotionCode p) {
        return promotionCodeRepository.save(p);
    }

    @Override
    public List<PromotionCode> retrieveAll() {
        return promotionCodeRepository.findAll();
    }

    @Override
    public PromotionCode getById(Long id) {
        return promotionCodeRepository.findById(id).get();
    }

    @Override
    public void delete(PromotionCode p) {
        promotionCodeRepository.delete(p);
    }
}
