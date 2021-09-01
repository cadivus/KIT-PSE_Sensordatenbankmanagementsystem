package edu.teco.sensordatenbankmanagementsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.Disabled;
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
class SensorControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate; // available with Spring Web MVC

  @LocalServerPort
  private Integer port;


  HttpHeaders headers = new HttpHeaders();

  @Test
  void getAllSensors() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/sensor/getAllSensors"), HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:s"));
    }
  }

  @Test
  void getAllThings() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/sensor/getAllThings"), HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:t"));
    }
  }

  @Test
  void getSensorValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/sensor/sensor/saqn:s:9682e37"), HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:s:9682e37"));
    }
  }

  @Test
  void getSensorInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/sensor/sensor/xyz"), HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody())
          .contains("Failed to find the requested element"));
    }
  }

  @Test
  void getWhetherThingsActiveValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&days=10000"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertEquals("[1]", response.getBody());
    }
    response = testRestTemplate.exchange(
        createURLWithPort("/sensor/active/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&days=0"),
        HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertEquals("[0]", response.getBody());
    }
  }

  @Test
  void getWhetherThingsActiveInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&days=-1"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertEquals("[0]", response.getBody());
    }

    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active/?ids=xyz&days=10"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertEquals("[-1]", response.getBody());
    }
  }

  @Test
  void getActiveRateOfThingsValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active_rate/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).matches("\\[-?\\d+(\\.\\d+)?]"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active_rate/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&frameStart=2020-12-30"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).matches("\\[-?\\d+(\\.\\d+)?]"));
    }
  }

  void getActiveRateOfThingsInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active_rate/?ids=xyz"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertEquals("[0.0]", response.getBody());
    }

    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active_rate/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&frameStart=2020-12-35"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("could not be parsed"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/active_rate/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&frameStart=2020-12-30&frameEnd=2019-01-01"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertEquals("[-0.0]", response.getBody());
    }
  }

  @Test
  void getObservationStatsOfThingsValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/stats/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&frameStart=2020-12-30&obsIds=saqn:o:PM10"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:o:PM10"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/stats/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&obsIds=saqn:o:PM10"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:o:PM10"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/stats/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&frameStart=2017-12-30&frameEnd=2021-08-30&obsIds=saqn:o:PM10"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:o:PM10"));
    }
  }

  @Disabled("This method does not have correct error handling yet")
  void getObservationStatsOfThingsInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/stats/?ids=saqn:t:grimm-aerosol.com:edm80opc:sn19001&frameStart=2020-12-30&obsIds=xyz"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("xyz"));
    }
    response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/stats/?ids=xyz&frameStart=2020-12-30&obsIds=xyz"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("saqn:o:PM10"));
    }
  }

  @Test
  void getThingValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/thing/saqn:t:grimm-aerosol.com:edm80opc:sn19001"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody())
          .contains("saqn:t:grimm-aerosol.com:edm80opc:sn19001"));
    }
  }

  @Test
  void getThingInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/thing/xyz"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(
          Objects.requireNonNull(response.getBody()).contains("Connection to the database failed"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody())
          .contains("Failed to find the requested element"));
    }
  }

  @Test
  void testGetThings() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort(
            "/sensor/allThings"),
        HttpMethod.GET, entity,
        String.class);

    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("Connection"));
    } else {
      assertTrue(Objects.requireNonNull(response.getBody()).contains("\"id\"") && response.getBody()
          .contains("saqn:t"));
    }
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}