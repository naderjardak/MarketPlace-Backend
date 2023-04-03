package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.GoodFeelings;

import java.util.List;

public interface GoodFeelingsInterface {
    public void addNewGoodFeelings(GoodFeelings goodFeelings);

    public void updateGoodFeelings(GoodFeelings goodFeelings);

    public void deleteGoodFeelings(Long id);

    public List<GoodFeelings> getAllGoodFeelings();
}
