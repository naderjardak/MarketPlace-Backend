package tn.workbot.coco_marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class CocoMarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoMarketplaceApplication.class, args);
    }

}
