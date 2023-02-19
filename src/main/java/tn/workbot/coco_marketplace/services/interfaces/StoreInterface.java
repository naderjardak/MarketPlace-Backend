package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.PromotionCode;
import tn.workbot.coco_marketplace.entities.Store;

import java.util.List;

public interface StoreInterface {

    Store create(Store s);

    Store update(Store s);

    List<Store> retrieveAll();

    Store getById(Long id);

    void delete(Store s);
}
