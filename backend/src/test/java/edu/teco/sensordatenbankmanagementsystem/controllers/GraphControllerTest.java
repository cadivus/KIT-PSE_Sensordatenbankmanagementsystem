package edu.teco.sensordatenbankmanagementsystem.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static testutil.TestUtils.createURLWithPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"edu.teco.sensordatenbamkmanagementsystem"})
class GraphControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate; // available with Spring Web MVC

    @LocalServerPort
    private Integer port;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    void getGraphOfThing() {
        int width = 500, height = 300;
        HttpEntity<byte[]> entity = new HttpEntity<>(null, headers);
        ResponseEntity<byte[]> response = testRestTemplate.exchange(
                createURLWithPort(port, "/graph?id=saqn:t:00afbb4&obsId=saqn:op:hur&imageSize="
                        + width + "x"
                        + height
                ), HttpMethod.GET,
                entity,
                byte[].class);
        if (!response.getStatusCode().is5xxServerError()) {
            assertEquals(response.getBody().length, width * height);
        }
    }
}