package tn.workbot.coco_marketplace.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.Store;
import tn.workbot.coco_marketplace.services.ProductService;
import tn.workbot.coco_marketplace.services.interfaces.StoreInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("store")
@PreAuthorize("hasAuthority('SELLER')")
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


}
