package de.qaware.springbootweather.openWeather;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenWeatherConnector {

    @Autowired
    private OpenWeatherConfiguration openWeatherConfiguration;

    public String getWeather(String city) {
        String uri = openWeatherConfiguration.getWeatherUri();
        String appId = openWeatherConfiguration.getWeatherAppId();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(uri));
        JSONObject result = new JSONObject(restTemplate.getForObject("/data/2.5/weather" + "?q=" + city + "&APPID=" + appId , String.class));
        return result.getJSONArray("weather").getJSONObject(0).getString("main");
    }
}
