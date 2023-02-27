package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import tn.workbot.coco_marketplace.entities.PromotionCode;

import java.util.Date;
import java.util.List;

public interface PromotionCodeInterface {

    PromotionCode create(PromotionCode p);

    PromotionCode update(PromotionCode p);

    List<PromotionCode> retrieveAll();

    PromotionCode getById(Long id);

    void delete(PromotionCode p);

    PromotionCode createAndAssignPromortionCodeToProduct(Long idProduct, String voucher, Date promotionCodeSatrDate, Date endDate, int discountValue);

}
