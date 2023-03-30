package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.ProductCategory;
import tn.workbot.coco_marketplace.services.interfaces.ProductCategoryInterface;

import java.util.List;

@RestController
@RequestMapping("productCategory")
@CrossOrigin(origins = "http://localhost:4200/")

@PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryInterface productCategoryInterface;

    @GetMapping("GetAllProductCategoriesAndSub")
    public List<ProductCategory> retrieveAll() {
        return productCategoryInterface.retrieveAll();
    }

    @GetMapping("GetProductCategoryById")
    public ProductCategory getById(@RequestParam Long id) {
        return productCategoryInterface.getById(id);
    }

    @PostMapping("SaveProductCategory")
    public ProductCategory createProduct(@RequestBody ProductCategory p) {
        return productCategoryInterface.create(p);
    }

    @PutMapping("UpdateProductCategory")
    public ProductCategory updateProduct(@RequestBody ProductCategory p) {
        return productCategoryInterface.update(p);
    }

    @DeleteMapping("DeleteProductCategory")
    public void delete(ProductCategory p) {
        productCategoryInterface.delete(p);
    }
    @GetMapping("GetAllProductCategories")
    public List<ProductCategory> retrieveAllCategories() {
        return productCategoryInterface.findAllCategories();
    }
    @GetMapping("GetAllProductSubCategories")
    public List<ProductCategory> retrieveAllSubCategories() {
        return productCategoryInterface.findAllSubCategories();
    }


}
