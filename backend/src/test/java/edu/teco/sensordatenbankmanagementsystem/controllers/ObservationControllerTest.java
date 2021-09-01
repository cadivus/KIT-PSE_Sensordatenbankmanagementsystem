package edu.teco.sensordatenbankmanagementsystem.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"edu.teco.sensordatenbamkmanagementsystem"})
class ObservationControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate; // available with Spring Web MVC

  @LocalServerPort
  private Integer port;


  HttpHeaders headers = new HttpHeaders();

  @Test
  void getAllObservedProperties() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/getAllObs"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:op:"));
    }
  }

  @Test
  void getObservationsBySensorIdValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/observations/saqn:t:43ae704/?frameStart=2019-01-01&frameEnd=2021-01-01"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("30e29778-0cf8-11ea-83d0-9311961f"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort("/observation/observations/saqn:t:43ae704/?limit=10000&sort=date-asc"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("30e29778-0cf8-11ea-83d0-9311961f"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort("/observation/observations/saqn:t:43ae704/?frameStart=2019-01-01&frameEnd=2021-01-01&obsIds=xyz"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("[]"));
    }
  }
  @Test
  void getObservationsBySensorIdInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);


    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/observations/saqn:t:43ae704/?frameStart=2019-01-01&frameEnd=2021-01-01&sort=xyz"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed") | response.getBody().contains("Unknown error"));
    } else fail();
    response = testRestTemplate.exchange(
            createURLWithPort("/observation/observations/xyz/?frameStart=2019-01-01&frameEnd=2021-01-01"), HttpMethod.GET, entity,
            String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else {

    }
    response = testRestTemplate.exchange(
        createURLWithPort("/observation/observations/saqn:t:43ae704/?frameStart=2019-0111-01&frameEnd=2021-01-01&obsIds=xyz"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed") | response.getBody().contains("'2019-0111-01'"));
    }

  }

  @Test
  void exportToCSVValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/observation/Export/saqn:t:43ae704/2019-12-29:01-00-00/2020-01-01:01-00-00"),
        HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).startsWith(
          "DATASTREAMID,FEATUREID,PHENOMENONEND,PHENOMENONSTART,RESULTNUMBER,RESULTSTRING,RESULTTIME,TYPE"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort(
            "/observation/Export/saqn:t:43ae704/2020-01-26:01-00-00"),
        HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).startsWith(
          "DATASTREAMID,FEATUREID,PHENOMENONEND,PHENOMENONSTART,RESULTNUMBER,RESULTSTRING,RESULTTIME,TYPE"));
    }
  }

  @Test
  void exportToCSVInvalid() {

  }
  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}