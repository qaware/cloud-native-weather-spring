package de.qaware.springbootweather.provider;

import org.springframework.stereotype.Component;

@Component
public interface WeatherProvider {
    String getWeather();
}
