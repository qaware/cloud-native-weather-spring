package de.qaware.springbootweather;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class SpringBootWeatherApplicationTests extends BaseIntegrationTest {

    @Rule
    public OutputCaptureRule output = new OutputCaptureRule();

    @Test
    void contextLoadsAndDatabaseIsOnline() {
        assertThat(postgresDB.isRunning()).isTrue();
    }

    @Test
    void shouldRetrieveWeatherRosenheimFromOpenWeatherMap() {
        ResponseEntity<String> response = trt.getForEntity("http://localhost:" + port + "/api/weather?city=Rosenheim", String.class);

        // Assertions:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("The weather in 'Rosenheim' is currently: ");
    }

    @Test
    void shouldRetrieveWeatherMunichFromOpenWeatherMap() {
        ResponseEntity<String> response = trt.getForEntity("http://localhost:" + port + "/api/weather?city=Munich", String.class);

        // Assertions:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("The weather in 'Munich' is currently: ");
    }

    @Test
    void shouldRetrieveWeatherForNoValidCity() {
        ResponseEntity<String> response = trt.getForEntity("http://localhost:" + port + "/api/weather?city=Test", String.class);

        // Assertions:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("No current weather data!");
    }
}
