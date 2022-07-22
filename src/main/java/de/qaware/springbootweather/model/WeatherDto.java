package de.qaware.springbootweather.model;

import java.util.StringJoiner;

public class WeatherDto {

    private String city;
    private String weather;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add("The weather in '" + city + "'")
                .add("is currently: '" + weather + "'.")
                .toString();
    }
}
