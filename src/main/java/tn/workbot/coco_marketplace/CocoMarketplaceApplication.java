package tn.workbot.coco_marketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Coco Marketplace", version = "1.0", description = "workbot <img src=\"https://www.google.co.uk/imgres?imgurl=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2F2%2F23%2FLatin_F.png&imgrefurl=https%3A%2F%2Fen.wiktionary.org%2Fwiki%2Ff&tbnid=15rkC3obUzsbsM&vet=12ahUKEwjFtInVrtr9AhUa_7sIHekFBzIQMygCegUIARCZAQ..i&docid=lqGywpqz59xYDM&w=398&h=678&q=f&hl=fr&ved=2ahUKEwjFtInVrtr9AhUa_7sIHekFBzIQMygCegUIARCZAQ\"/>"))
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)

public class CocoMarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoMarketplaceApplication.class, args);
    }

}
