package tn.workbot.coco_marketplace.Api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @JsonProperty("main")
    private String main;

    @JsonProperty("description")
    private String description;

    @JsonProperty("temp")
    private double temperature;

    @JsonProperty("humidity")
    private int humidity;


    // getters and setters
}
