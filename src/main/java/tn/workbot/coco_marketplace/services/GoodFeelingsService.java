package tn.workbot.coco_marketplace.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.GoodFeelings;
import tn.workbot.coco_marketplace.repositories.GoodFeelingsRepository;
import tn.workbot.coco_marketplace.services.interfaces.GoodFeelingsInterface;

import java.util.List;

@Service
public class GoodFeelingsService implements GoodFeelingsInterface {
    @Autowired
    GoodFeelingsRepository goodFeelingsRepository;

    @Override
    public void addNewGoodFeelings(GoodFeelings goodFeelings) {
        goodFeelingsRepository.save(goodFeelings);

    }

    @Override
    public void updateGoodFeelings(GoodFeelings goodFeelings) {
        goodFeelingsRepository.save(goodFeelings);
    }

    @Override
    public void deleteGoodFeelings(Long id) {

        goodFeelingsRepository.deleteById(id);
    }

    @Override
    public List<GoodFeelings> getAllGoodFeelings() {
        return goodFeelingsRepository.findAll();
    }
}
