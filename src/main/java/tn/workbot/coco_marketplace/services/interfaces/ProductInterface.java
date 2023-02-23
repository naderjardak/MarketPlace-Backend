package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Product;

import java.util.List;

public interface ProductInterface {

    Product create(Product p);
    Product update(Product p);
    List<Product> retrieveAll();

    Product getById(Long id);

    void delete(Product p);

    Product createAndAssignToStore(Product p,Long idStore);


    Product createAndAssignCategoryAndSubCategory(Product p,String categoryName,String subCatName);


}
