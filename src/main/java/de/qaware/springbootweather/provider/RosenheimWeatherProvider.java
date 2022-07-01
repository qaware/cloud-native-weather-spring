package de.qaware.springbootweather.provider;

import org.springframework.stereotype.Component;

@Component("Rosenheim")
public class RosenheimWeatherProvider implements WeatherProvider {

    @Override
    public String getWeather() {
        return "Mega Sunshine";
    }
}
