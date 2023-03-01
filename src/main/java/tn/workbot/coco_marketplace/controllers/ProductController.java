package tn.workbot.coco_marketplace.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/product")
@Tag(name = "Product Management")
@Slf4j
public class ProductController {

    @Autowired
    UserInterface userInterface;
    @Autowired
    StoreRepository storeRepository;
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
    public void delete(@RequestBody Product p) {
        productInterface.delete(p);
    }

    @PostMapping("CreateProductAndAssignCatAndSub")
    public Product createAndAssignCategoryAndSubCategory(@RequestBody Product p, @RequestParam String categoryName, @RequestParam String subCatName) {
        return productInterface.createAndAssignCategoryAndSubCategory(p, categoryName, subCatName);

    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Upload Excel File of Products ")
    //@ImplicitParam(name = "file", value = "File to upload", required = true, dataType = "java.io.File", paramType = "formData")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {

        User user = userInterface.GetById(1);
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Product p = new Product();

            XSSFRow row = worksheet.getRow(i);

            p.setName(row.getCell(0).getStringCellValue());
            p.setProductWeight((float) row.getCell(1).getNumericCellValue());
            float price = (float) row.getCell(2).getNumericCellValue();
            p.setProductPrice(price);
            p.setProductPriceBeforeDiscount(price);
            p.setQuantity((int) row.getCell(3).getNumericCellValue());
            String cat = (row.getCell(4).getStringCellValue());
            String subCat = (row.getCell(5).getStringCellValue());
            String storeName = row.getCell(7).getStringCellValue();
            p.setStore(storeRepository.findStoreByNameAndAndSeller(storeName, user));
            p.setDescription(row.getCell(6).getStringCellValue());
            p.setProductStatus(ProductStatus.WAITING_FOR_VALIDATION);
            if (p.getProductWeight() <= 1) {
                p.setDeliveryPrice(6);
            } else if (p.getProductWeight() > 1) {
                p.setDeliveryPrice(6 + (p.getProductWeight() - 1) * 2.5f);

            }

            log.info(p.getName());

            productInterface.createAndAssignCategoryAndSubCategory(p, cat, subCat);
        }
    }

}