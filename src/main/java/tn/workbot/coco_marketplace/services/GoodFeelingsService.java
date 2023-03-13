package tn.workbot.coco_marketplace.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.GoodFeelings;
import tn.workbot.coco_marketplace.repositories.GoodFeelingsRepository;
import tn.workbot.coco_marketplace.services.interfaces.GoodFeelingsInterface;

@Service
public class GoodFeelingsService implements GoodFeelingsInterface {
    @Autowired
    GoodFeelingsRepository goodFeelingsRepository;

    @Override
    public void addNewGoodFeelings(GoodFeelings goodFeelings) {
        goodFeelingsRepository.save(goodFeelings);

    }
}
