package tn.workbot.coco_marketplace.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

import java.util.List;

@RestController
@RequestMapping("store")
public class StoreController {

    @Autowired
    StoreInterface storeInterface;

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

}
