package tn.workbot.coco_marketplace.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.entities.Product;
import tn.workbot.coco_marketplace.entities.PromotionCode;
import tn.workbot.coco_marketplace.repositories.PromotionCodeRepository;
import tn.workbot.coco_marketplace.services.interfaces.PromotionCodeInterface;

import java.util.Date;
import java.util.List;

@Service
public class PromotionCodeService implements PromotionCodeInterface {

    @Autowired
    PromotionCodeRepository promotionCodeRepository;


    @Autowired
    ProductService productService;

    @Override
    public PromotionCode create(PromotionCode p, Long idProduct) {
        Product product=productService.getById(idProduct);
        p.setProduct(product);
        return promotionCodeRepository.save(p);
    }

    @Override
    public PromotionCode update(PromotionCode p) {
        return promotionCodeRepository.save(p);
    }

    @Override
    public List<PromotionCode> retrieveAll() {
        return promotionCodeRepository.findAll();
    }

    @Override
    public PromotionCode getById(Long id) {
        if(promotionCodeRepository.findById(id).isPresent())
                return promotionCodeRepository.findById(id).get();

        return new PromotionCode();
    }

    @Override
    public void delete(PromotionCode p) {
        promotionCodeRepository.delete(p);
    }

    @Override
    public PromotionCode createAndAssignPromortionCodeToProduct(Long idp, String voucher , Date promotionCodeSatrDate, Date endDate, int discountValue){
        PromotionCode promotionCode = promotionCodeRepository.findByVoucherAndStartDateAndDiscountValue(voucher, promotionCodeSatrDate,discountValue);
            Product p=productService.getById(idp);

        if (promotionCode == null) {
            promotionCode = new PromotionCode();
            promotionCode.setVoucher(voucher);
            promotionCode.setEndtDate(endDate);
            promotionCode.setDiscountValue(discountValue);
            promotionCode.setStartDate(promotionCodeSatrDate);
        }
        //affect child to parent
        promotionCode.setProduct(p);
        return  promotionCodeRepository.save(promotionCode);
    }
}
