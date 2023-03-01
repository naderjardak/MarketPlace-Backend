package tn.workbot.coco_marketplace.services.interfaces;

import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.SupplierRequest;

import java.util.List;

public interface SupplierRequestInterface {
    SupplierRequest create(SupplierRequest s,Long productId);

    SupplierRequest update(SupplierRequest s);

    List<SupplierRequest> retrieveAll();

    SupplierRequest getById(Long id);

    void deleteById(Long id) throws Exception;

    List<Product> retriveProductsOutOfStock();

    List<SupplierRequest> retriveRequestsByProduct(Long idProduct);

    void accpetRequestBySeller(Long supplierRequestId);
}
