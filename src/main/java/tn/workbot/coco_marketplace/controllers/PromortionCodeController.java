package tn.workbot.coco_marketplace.controllers;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.workbot.coco_marketplace.Api.QRCodeGenerator;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.PromotionCode;
import tn.workbot.coco_marketplace.services.ProductService;
import tn.workbot.coco_marketplace.services.interfaces.PromotionCodeInterface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("PromotionCode")
public class PromortionCodeController {

    @Autowired
    private PromotionCodeInterface promotionCodeInterface;
    @Autowired
    private ProductService productService;

    @PostMapping("savePromotionCode")
    public ResponseEntity<InputStreamResource>  create(@RequestBody PromotionCode p,@RequestParam Long id) {
        try {
            BufferedImage qrCodeImage = QRCodeGenerator.generateQRCodeImage(p.getVoucher(), 200, 200);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", bos);

            byte[] imageBytes = bos.toByteArray();

            InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(imageBytes));
            HttpHeaders headers = new HttpHeaders();
            Product product=productService.getById(id);

            headers.setContentDispositionFormData("attachment", product.getName());
            headers.setContentType(MediaType.IMAGE_PNG);
            promotionCodeInterface.create(p, id);
            return new ResponseEntity<>(isr, headers, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("UpdatePromotionCode")
    public PromotionCode update(PromotionCode p) {
        return promotionCodeInterface.update(p);
    }

    @GetMapping("RetrieveAllPromotionCodes")
    public List<PromotionCode> retrieveAll() {
        return promotionCodeInterface.retrieveAll();
    }

    @GetMapping("GetPCById")
    public PromotionCode getById(Long id) {
        return promotionCodeInterface.getById(id);
    }

    @DeleteMapping("DeletePromotionCode")
    public void delete(PromotionCode p) {
        promotionCodeInterface.delete(p);
    }

    @PostMapping("createProductAndAssignPC")
    public PromotionCode createAndAssignPromortionCodeToProduct(@RequestParam Long idp, @RequestParam String voucher , @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date promotionCodeSatrDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam int discountValue){
        return promotionCodeInterface.createAndAssignPromortionCodeToProduct(idp,voucher,promotionCodeSatrDate,endDate,discountValue);
    }

}
