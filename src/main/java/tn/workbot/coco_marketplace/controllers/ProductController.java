package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductInterface productInterface;

    @GetMapping("GetAllProducts")
    public List<Product> retrieveAll() {
        return productInterface.retrieveAll();
    }

    @GetMapping("GetProductById")
    public Product getById(@RequestParam Long id) {
        return productInterface.getById(id);
    }

    @PostMapping("SaveProduct")
    public Product createProduct(@RequestBody Product p) {
        return productInterface.create(p);
    }

    @PutMapping("UpdateProduct")
    public Product updateProduct(@RequestBody Product p) {
        return productInterface.update(p);
    }

    @DeleteMapping("DeleteProduct")
    public void delete(Product p) {
        productInterface.delete(p);
    }



}
