package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.services.interfaces.ProductQuantityInterface;

import java.util.List;

@RestController
@RequestMapping("productQuantity")
@PreAuthorize("hasAuthority('BUYER')")
public class ProductQuantityController {
    @Autowired
    ProductQuantityInterface productQuantityInterface;

    @PostMapping("SaveProductQuantity")
    Boolean saveProductQuantity(@RequestBody ProductQuantity productQuantity){return productQuantityInterface.saveProductQuantity(productQuantity);};
    @GetMapping("GetAllProductQuantities")
    List<ProductQuantity> getAllProductQuantities(@RequestParam Long id){return productQuantityInterface.getAllProductQuantitiesByProduct(id);}
    @GetMapping("GetProductQuantityById")
    ProductQuantity getProductQuantityById(@RequestParam Long id){return productQuantityInterface.getProductQuantityById(id);}
    @DeleteMapping("DeleteProductQuantity")
    Boolean deleteProductQuantity(@RequestParam Long id){return productQuantityInterface.deleteProductQuantity(id);}


}
