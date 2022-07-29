package de.qaware.springbootweather.model;

import java.util.StringJoiner;

public class WeatherDto {

    private String city;
    private String weather;
    private Double temperature;

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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add("The weather in '" + city + "'")
                .add("is currently: '" + weather + "'")
                .add("and: '" + temperature + "°C'.")
                .toString();
    }
}
