package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.ProductQuantity;
import tn.workbot.coco_marketplace.services.interfaces.ProductQuantityInterface;

import java.util.List;

@Controller
public class ProductQuantityController {
    @Autowired
    ProductQuantityInterface productQuantityInterface;

    @PostMapping("Save Product Quantity")
    Boolean saveProductQuantity(@RequestBody ProductQuantity productQuantity){return productQuantityInterface.saveProductQuantity(productQuantity);};
    @GetMapping("Get All Product Quantities")
    List<ProductQuantity> getAllProductQuantities(@RequestParam Long id){return productQuantityInterface.getAllProductQuantitiesByProduct(id);}
    @GetMapping("Get Product Quantity By Id")
    ProductQuantity getProductQuantityById(@RequestParam Long id){return productQuantityInterface.getProductQuantityById(id);}
    @PutMapping("Delete Product Quantity")
    Boolean deleteProductQuantity(@RequestParam Long id){return productQuantityInterface.deleteProductQuantity(id);}


}
