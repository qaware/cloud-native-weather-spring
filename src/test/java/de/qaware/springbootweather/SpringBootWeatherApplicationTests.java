package de.qaware.springbootweather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootWeatherApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate trt;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldRetrieveWeatherRosenheim() {
        String output = trt.getForObject("http://localhost:" + port + "/api/weather?city=Rosenheim", String.class);

        // Assertions:
        assertThat(output).contains("The weather in 'Rosenheim' is currently: ");
    }

    @Test
    void shouldRetrieveWeatherMunich() {
        String output = trt.getForObject("http://localhost:" + port + "/api/weather?city=Munich", String.class);

        // Assertions:
        assertThat(output).contains("The weather in 'Munich' is currently: ");
    }
}
