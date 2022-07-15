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
    void shouldRetrieveCorrectWeatherRosenheim() {
        String output = trt.getForObject("http://localhost:" + port + "/api/weather?city=Rosenheim", String.class);

        // Assertions:
        assertThat(output).contains("The weather in 'Rosenheim' is currently: ");
    }

    @Test
    void shouldRetrieveCorrectWeatherMunich() {
        String output = trt.getForObject("http://localhost:" + port + "/api/weather?city=Munich", String.class);

        // Assertions:
        assertThat(output).contains("The weather in 'Munich' is currently: ");
    }

    @Test
    void shouldAddToDataBaseSuccessfully() {
        // Add data to the database
        String id = trt.postForObject("http://localhost:" + port + "/api/add?city=Test&weather=TestWeather", null, String.class);

        // Retrieve data
        String output2 = trt.getForObject("http://localhost:" + port + "/api/weather?city=Test", String.class);

        // Assertions:
        assertThat(output2).contains("The weather in 'Test' is currently: 'TestWeather'. Last update:");

        // Delete created data:
        deleteEntity(id);
    }

    @Test
    void shouldDeleteFromDataBaseSuccessfully() {
        // Add data to the database
        String id = trt.postForObject("http://localhost:" + port + "/api/add?city=Test&weather=TestWeather", null, String.class);

        // Delete created data:
        String output = trt.postForObject("http://localhost:" + port + "/api/delete?id=" + id, null, String.class);

        // Assertions:
        assertThat(output).isEqualTo("Deleted successfully");
    }

    @Test
    void shouldAddAndDeleteFromDataBaseCorrectly() {
        // Add data to the database
        String id = trt.postForObject("http://localhost:" + port + "/api/add?city=DeletionTest&weather=TestWeather", null, String.class);

        // Retrieve data
        String output = trt.getForObject("http://localhost:" + port + "/api/weather?city=DeletionTest", String.class);

        // Delete created data:
        String output2 = trt.postForObject("http://localhost:" + port + "/api/delete?id=" + id, null, String.class);

        // Retrieve again
        String output3 = trt.getForObject("http://localhost:" + port + "/api/weather?city=DeletionTest", String.class);

        // Assertions:
        assertThat(output).contains("The weather in 'DeletionTest' is currently: ");
        assertThat(output2).isEqualTo("Deleted successfully");
        assertThat(output3).doesNotContain("The weather in 'DeletionTest' is currently: ");
    }

    @Test
    void shouldUseLatestValueFromDataBase() {
        // Add data to the database
        String id1 = trt.postForObject("http://localhost:" + port + "/api/add?city=Test&weather=TestWeather1", null, String.class);
        String id2 = trt.postForObject("http://localhost:" + port + "/api/add?city=Test&weather=TestWeather2", null, String.class);

        // Retrieve data
        String output = trt.getForObject("http://localhost:" + port + "/api/weather?city=Test", String.class);

        // Assertions:
        assertThat(output).contains("The weather in 'Test' is currently: 'TestWeather2'. Last update:");

        // Delete created data:
        deleteEntity(id1);
        deleteEntity(id2);
    }

    @Test
    void shouldListWeatherFromDataBaseSuccessfully() {
        // prepare data
        String id1 = trt.postForObject("http://localhost:" + port + "/api/add?city=Test1&weather=Rainy", null, String.class);
        String id2 = trt.postForObject("http://localhost:" + port + "/api/add?city=Test2&weather=Hot", null, String.class);
        String id3 = trt.postForObject("http://localhost:" + port + "/api/add?city=Test3&weather=Very Hot", null, String.class);

        // list data
        String output = trt.getForObject("http://localhost:" + port + "/api/list", String.class);

        // Assertions:
        assertThat(output).contains("\"city\":\"Test1\",\"weather\":\"Rainy\"");
        assertThat(output).contains("\"city\":\"Test2\",\"weather\":\"Hot\"");
        assertThat(output).contains("\"city\":\"Test3\",\"weather\":\"Very Hot\"");

        // Delete created data:
        deleteEntity(id1);
        deleteEntity(id2);
        deleteEntity(id3);
    }

    void deleteEntity(String id) {
        trt.postForObject("http://localhost:" + port + "/api/delete?id=" + id, null, String.class);
    }
}
