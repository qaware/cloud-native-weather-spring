package de.qaware.springbootweather.service;

import de.qaware.springbootweather.model.Weather;
import de.qaware.springbootweather.openWeather.OpenWeatherConnector;
import de.qaware.springbootweather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.Iterator;

@Component
public class WeatherService implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private ApplicationContext applicationContext;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private OpenWeatherConnector connector;

    public String findWeatherByCity(String city) {
        Iterable<Weather> weatherForCity = weatherRepository.findWeatherByCity(city);
        if (weatherForCity.iterator().hasNext()) {
            // weather is stored in the database
            logger.info(String.format("Weather for %s could be retrieved from the database!", city));
            return findMostRecentData(weatherForCity).toString();
        } else {
            // weather must be retrieved from OpenWeatherMap
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setWeather(connector.getWeather(city));
            weather.setDate(Date.from(Instant.now()));
            logger.info(String.format("Weather for %s retrieved form OpenWeatherMap!", city));
            weatherRepository.save(weather);
            return weather.toString();
        }
    }

    private Weather findMostRecentData(Iterable<Weather> data) {
        Iterator<Weather> iterator = data.iterator();
        Weather mostRecentWeather = iterator.next();

        while (iterator.hasNext()) {
            Weather nextWeather = iterator.next();
            if (nextWeather.getDate().after(mostRecentWeather.getDate())) {
                mostRecentWeather = nextWeather;
            }
        }

        return mostRecentWeather;
    }

    public String addWeather(String city, String weather) {
        Weather weatherData = new Weather();
        weatherData.setCity(city);
        weatherData.setWeather(weather);
        weatherData.setDate(Date.from(Instant.now()));
        weatherData = weatherRepository.save(weatherData);
        logger.info(String.format("Added weather data with id %s successfully", weatherData.getId()));
        return weatherData.getId().toString();
    }

    public String deleteWeather(Integer id) {
        weatherRepository.deleteWeatherById(id);
        logger.info(String.format("Deleted weather with id %s", id));
        return "Deleted successfully";
    }

    public Weather getWeather(Integer id) {
        logger.info(String.format("Retrieved weather with id %s", id));
        return weatherRepository.findWeatherById(id);
    }

    public Iterable<Weather> listWeather() {
        Iterable<Weather> weatherData = weatherRepository.findAll();
        logger.info("Retrieved all weather data from the database.");
        return weatherData;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
