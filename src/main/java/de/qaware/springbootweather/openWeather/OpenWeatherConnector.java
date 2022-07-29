package de.qaware.springbootweather.openWeather;

import de.qaware.springbootweather.model.Weather;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.sql.Date;
import java.time.Instant;

@Component
public class OpenWeatherConnector {

    private static final Double KelvinToCelsius = 273.15;

    @Autowired
    private OpenWeatherConfiguration openWeatherConfiguration;

    public Weather getWeather(String city) {
        String uri = openWeatherConfiguration.getWeatherUri();
        String appId = openWeatherConfiguration.getWeatherAppId();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(uri));
        JSONObject result = new JSONObject(restTemplate.getForObject("/data/2.5/weather" + "?q=" + city + "&APPID=" + appId , String.class));

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setWeather(result.getJSONArray("weather").getJSONObject(0).getString("main"));
        weather.setTemperature(Math.round((result.getJSONObject("main").getDouble("temp") - KelvinToCelsius)*100)*0.01);
        weather.setDate(Date.from(Instant.now()));
        return weather;
    }
}
