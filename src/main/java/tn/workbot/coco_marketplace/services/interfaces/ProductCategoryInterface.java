package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.ProductCategory;

import java.util.List;

public interface ProductCategoryInterface {

    ProductCategory create(ProductCategory p);

    ProductCategory update(ProductCategory p);

    List<ProductCategory> retrieveAll();

    ProductCategory getById(Long id);

    void delete(ProductCategory p);
    ProductCategory findByName(String name);

}
