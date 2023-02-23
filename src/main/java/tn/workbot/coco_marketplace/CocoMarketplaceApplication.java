package tn.workbot.coco_marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CocoMarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoMarketplaceApplication.class, args);
    }

}
