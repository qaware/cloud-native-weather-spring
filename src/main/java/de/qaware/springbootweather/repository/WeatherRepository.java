package de.qaware.springbootweather.repository;

import de.qaware.springbootweather.model.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, Integer> {

    Weather findWeatherById(Integer Id);

    Iterable<Weather> findWeatherByCity(String city);

    void deleteWeatherById(Integer id);

}
