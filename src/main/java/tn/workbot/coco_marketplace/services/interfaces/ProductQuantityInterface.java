package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.ProductQuantity;

import java.util.List;

public interface ProductQuantityInterface {

    public Boolean saveProductQuantity(ProductQuantity productQuantity); // saves a product quantity object to the database
    public List<ProductQuantity> getAllProductQuantitiesByProduct(Long id); // retrieves all product quantity objects from the database
    public ProductQuantity getProductQuantityById(Long id);    // Retrieve a ProductQuantity entity by its ID
    public Boolean deleteProductQuantity(Long id);    // Delete a ProductQuantity entity by its ID

}
