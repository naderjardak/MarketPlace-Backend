package tn.workbot.coco_marketplace.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.services.ProductService;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

import java.util.List;

@RestController
@RequestMapping("store")
@CrossOrigin(origins = "http://localhost:4200/")

@PreAuthorize("hasAuthority('SELLER') || hasAuthority('ADMIN')")
@Slf4j
public class StoreController {

    @Autowired
    StoreInterface storeInterface;

    @Autowired
    ProductService productService;

    @GetMapping("GetAllStores")
    public List<Store> retrieveAll() {
        return storeInterface.retrieveAll();
    }

    @GetMapping("GetStoreById")
    public Store getById(@RequestParam Long id) {
        return storeInterface.getById(id);
    }


    @PostMapping("SaveStore")
    public Store createProduct(@RequestBody Store s) {
        return storeInterface.create(s);
    }

    @PutMapping("UpdateProductCategory")
    public Store updateProduct(@RequestBody Store s) {
        return storeInterface.update(s);
    }

    @DeleteMapping("DeleteProductCategory")
    public void delete(Store s) {
        storeInterface.delete(s);
    }

    @GetMapping("getStoresByUser")
    public List<Store> getStoreByUser(@RequestParam Long id) {
        return storeInterface.getStoresByUser(id);
    }
}
