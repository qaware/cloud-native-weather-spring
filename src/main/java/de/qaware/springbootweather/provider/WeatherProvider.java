package de.qaware.springbootweather.provider;

import org.springframework.stereotype.Component;

// Local WeatherProvider for prepared weather data, replaced by an external API at 08. July 2022 (OpenWeatherMap)
@Component
public interface WeatherProvider {
    String getWeather();
}
