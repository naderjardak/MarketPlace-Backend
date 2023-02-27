package tn.workbot.coco_marketplace.AOP;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import tn.workbot.coco_marketplace.entities.Product;

@Component
@Aspect
@Slf4j
public class ProductAOP {



    @Before("execution(* tn.workbot.coco_marketplace.services.ProductService.create(..)) && args(p)")
    public void deliveryPrice(Product p) {
        if (p.getProductWeight() <= 1) {
            p.setDeliveryPrice(6);
        } else if (p.getProductWeight() > 1) {
            p.setDeliveryPrice(6 + (p.getProductWeight() - 1) * 2.5f);

        }
    }
}
