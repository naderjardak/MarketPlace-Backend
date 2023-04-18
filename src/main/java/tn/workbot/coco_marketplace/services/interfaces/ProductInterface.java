package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.entities.SupplierRequest;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

public interface ProductInterface {

    Product create(Product p,String storeName) throws Exception;
    Product update(Product p) throws Exception;
    List<Product> retrieveAll();

    Product getById(Long id);

    void delete(Product p);

    //Product createAndAssignToStore(Product p,Long idStore);
    List<SupplierRequest> retriveRequestsByProduct(Long idProduct);


    Product createAndAssignCategoryAndSubCategory(Product p, String categoryName, String subCatName, Set<String> storeName) throws Exception;

    ByteArrayInputStream allSupplierRequestsOnProduct(Long id,String status);

    List<Product> getProductBySeller();

    List<Product> getProductsByStore(String store);




}
