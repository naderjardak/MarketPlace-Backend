package tn.workbot.coco_marketplace.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenWeatherMapClient {
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&units=metric&appid={apiKey}";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.apikey}")
    private String apiKey;

    public double getWeather(String city) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("city", city);
        uriVariables.put("apiKey", apiKey);

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(API_URL, Map.class, uriVariables);
        Map<String, Object> responseMap = responseEntity.getBody();

        Map<String, Object> mainMap = (Map<String, Object>) responseMap.get("main");
        double temperature = (double) mainMap.get("temp");

        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) responseMap.get("weather");
        String description = (String) weatherList.get(0).get("description");

        Weather weather = new Weather();
        weather.setDescription(description);
        weather.setTemperature(temperature);

        return weather.getTemperature();
    }
}
