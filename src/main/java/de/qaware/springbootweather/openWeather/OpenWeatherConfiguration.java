package de.qaware.springbootweather.openWeather;

import org.springframework.stereotype.Component;

@Component
public class OpenWeatherConfiguration {

    private final String weatherAppId = "5b3f51e527ba4ee2ba87940ce9705cb5";
    private final String weatherUri = "https://api.openweathermap.org";

    public String getWeatherAppId() {
        return weatherAppId;
    }

    public String getWeatherUri() {
        return weatherUri;
    }
}
