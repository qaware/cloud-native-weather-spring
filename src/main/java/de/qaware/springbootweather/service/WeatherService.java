package de.qaware.springbootweather.service;

import de.qaware.springbootweather.model.Weather;
import de.qaware.springbootweather.model.WeatherDto;
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

import java.util.Iterator;

@Component
public class WeatherService implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private ApplicationContext applicationContext;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private OpenWeatherConnector connector;

    public WeatherDto findWeatherByCity(String city) {
        Iterable<Weather> weatherForCity = weatherRepository.findWeatherByCity(city);
        if (weatherForCity.iterator().hasNext()) {
            // weather is stored in the database
            logger.info(String.format("Weather for city '%s' retrieved from the database", city));
            return mapToDto(findMostRecentData(weatherForCity));
        } else {
            // weather must be retrieved from OpenWeatherMap
            try {
                Weather weather = connector.getWeather(city);
                logger.info(String.format("Weather for '%s' retrieved form OpenWeatherMap", city));
                weatherRepository.save(weather);
                logger.info(String.format("Weather for '%s' saved in the database", city));
                return mapToDto(weather);
            }
            catch(HttpClientErrorException e) {
                logger.warn(String.format("City '%s' does not exist or has no weather data", city));
                WeatherDto result = new WeatherDto();
                result.setCity(city);
                return result;
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

    private WeatherDto mapToDto(Weather data) {
        WeatherDto result = new WeatherDto();
        result.setCity(data.getCity());
        result.setWeather(data.getWeather());
        result.setTemperature(data.getTemperature());
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
