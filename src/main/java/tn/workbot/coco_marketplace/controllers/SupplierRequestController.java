package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.SupplierRequest;
import tn.workbot.coco_marketplace.services.interfaces.SupplierRequestInterface;

import java.util.List;

@RestController
@RequestMapping("SupplierRequest")
public class SupplierRequestController {

    @Autowired
    SupplierRequestInterface supplierRequestInterface;

    @PostMapping("createRequest")
    SupplierRequest create(@RequestBody SupplierRequest s, @RequestParam Long productId) {
        return supplierRequestInterface.create(s, productId);
    }

    @PutMapping("updateRequest")
    SupplierRequest update(@RequestBody SupplierRequest s) {
        return supplierRequestInterface.update(s);
    }

    @GetMapping("RetrieveRequests")
    List<SupplierRequest> retrieveAll() {
        return supplierRequestInterface.retrieveAll();
    }

    @GetMapping("getRequestById")
    SupplierRequest getById(Long id){
        return supplierRequestInterface.getById(id);
    }

    @DeleteMapping("DeleteRequest")
       void delete(Long id) throws Exception {
        supplierRequestInterface.deleteById(id);
    }

    @GetMapping("ProductsOutOfStock")
    List<Product> retriveProductsOutOfStock(){
        return supplierRequestInterface.retriveProductsOutOfStock();
    }

    @GetMapping("retriveRequestsByProduct")
    List<SupplierRequest> retriveRequestsByProduct(Long idProduct){
        return supplierRequestInterface.retriveRequestsByProduct(idProduct);
    }

    @PutMapping("AcceptRequest")
    void accpetRequestBySeller(Long supplierRequestId){
        supplierRequestInterface.accpetRequestBySeller(supplierRequestId);

    }
}
