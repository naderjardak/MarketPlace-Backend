package tn.workbot.coco_marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAspectJAutoProxy
@EnableScheduling
@EnableSwagger2
public class CocoMarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoMarketplaceApplication.class, args);
    }




}
