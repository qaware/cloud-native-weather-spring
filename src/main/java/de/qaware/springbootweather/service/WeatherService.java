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
import org.springframework.web.client.HttpClientErrorException;

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
            logger.info(String.format("Weather for city '%s' retrieved from the database", city));
            return findMostRecentData(weatherForCity).toString();
        } else {
            // weather must be retrieved from OpenWeatherMap
            try {
                Weather weather = new Weather();
                weather.setCity(city);
                weather.setWeather(connector.getWeather(city));
                weather.setDate(Date.from(Instant.now()));
                logger.info(String.format("Weather for '%s' retrieved form OpenWeatherMap", city));
                weatherRepository.save(weather);
                return weather.toString();
            }
            catch(HttpClientErrorException e) {
                logger.warn(String.format("City '%s' does not exist or has no weather data", city));
                return "No current weather data!";
            }

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
