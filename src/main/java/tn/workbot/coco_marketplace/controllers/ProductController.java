package tn.workbot.coco_marketplace.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.User;
import tn.workbot.coco_marketplace.repositories.StoreRepository;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;
import tn.workbot.coco_marketplace.services.interfaces.UserInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("product")
@PreAuthorize("hasAuthority('SELLER')")
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
    public Product createProduct(@RequestBody Product p, @RequestParam String storeName) throws Exception {
        return productInterface.create(p, storeName);
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
    public Product createAndAssignCategoryAndSubCategory(@RequestBody Product p, @RequestParam String categoryName, @RequestParam String subCatName, @RequestParam String storeName) throws Exception {
        return productInterface.createAndAssignCategoryAndSubCategory(p, categoryName, subCatName, storeName);

    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Upload Excel File of Products ")
    //@ImplicitParam(name = "file", value = "File to upload", required = true, dataType = "java.io.File", paramType = "formData")
    public void mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws Exception {

        User user = userInterface.getUserById(1);
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Product p = new Product();

            XSSFRow row = worksheet.getRow(i);

            p.setName(row.getCell(0).getStringCellValue());
            p.setProductWeight((float) row.getCell(1).getNumericCellValue());
            float price = (float) row.getCell(2).getNumericCellValue();
            p.setProductPrice(price);
            p.setQuantity((int) row.getCell(3).getNumericCellValue());
            String cat = (row.getCell(4).getStringCellValue());
            String subCat = (row.getCell(5).getStringCellValue());
            String storeName = row.getCell(7).getStringCellValue().toLowerCase();
            //p.setStore(storeRepository.findStoreByNameAndAndSeller(storeName, user));
            p.setDescription(row.getCell(6).getStringCellValue());
            p.setAdditionalDeliveryInstructions(row.getCell(8).getStringCellValue());

            log.info(p.getName());

            productInterface.createAndAssignCategoryAndSubCategory(p, cat, subCat, storeName);
        }
    }

    @GetMapping(value = "allSupplierRequestsOnProduct", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> allSupplierRequestsOnProduct(Long id, @Schema(allowableValues = {"ALL", "ACCEPTED", "REJECTED", "DELIVERED", "PENDING"}) String status) throws IOException {

        ByteArrayInputStream pdf = productInterface.allSupplierRequestsOnProduct(id, status);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename=" + new Date(System.currentTimeMillis()) + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

//    @GetMapping("/qrcode")
//    public ResponseEntity<InputStreamResource> downloadQRCodeImage(@RequestParam String text,
//                                                                   @RequestParam(defaultValue = "200") int width,
//                                                                   @RequestParam(defaultValue = "200") int height) {
//        try {
//            BufferedImage qrCodeImage = QRCodeGenerator.generateQRCodeImage(text, width, height);
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ImageIO.write(qrCodeImage, "png", bos);
//            byte[] imageBytes = bos.toByteArray();
//
//            InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(imageBytes));
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentDispositionFormData("attachment", "qrcode.png");
//            headers.setContentType(MediaType.IMAGE_PNG);
//
//            return new ResponseEntity<>(isr, headers, HttpStatus.OK);
//        } catch (WriterException | IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}