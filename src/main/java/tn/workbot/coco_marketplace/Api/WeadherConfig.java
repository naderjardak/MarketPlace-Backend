package tn.workbot.coco_marketplace.Api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WeadherConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
