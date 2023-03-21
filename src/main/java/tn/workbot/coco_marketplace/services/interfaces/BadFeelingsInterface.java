package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.BadFeelings;

import java.util.List;

public interface BadFeelingsInterface {

    public void addNewBadFeelings(BadFeelings badFeelings);

    public void updateBadFeelings(BadFeelings badFeelings);

    public void deleteBadFeelings(Long id);

    public List<BadFeelings> getAllBadFeelings();
}
