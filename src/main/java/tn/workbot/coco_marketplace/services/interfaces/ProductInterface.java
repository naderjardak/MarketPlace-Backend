package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.SupplierRequest;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ProductInterface {

    Product create(Product p,String storeName) throws Exception;
    Product update(Product p);
    List<Product> retrieveAll();

    Product getById(Long id);

    void delete(Product p);

    //Product createAndAssignToStore(Product p,Long idStore);
    List<SupplierRequest> retriveRequestsByProduct(Long idProduct);


    Product createAndAssignCategoryAndSubCategory(Product p,String categoryName,String subCatName,String storeName) throws Exception;

    ByteArrayInputStream allSupplierRequestsOnProduct(Long id,String status);

    List<Product> getProductBySeller();




}
