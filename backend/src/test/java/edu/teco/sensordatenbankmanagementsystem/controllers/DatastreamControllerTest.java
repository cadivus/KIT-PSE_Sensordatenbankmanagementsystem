package edu.teco.sensordatenbankmanagementsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
class DatastreamControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate; // available with Spring Web MVC

  @LocalServerPort
  private Integer port;


  HttpHeaders headers = new HttpHeaders();

  //@Autowired
  //private DatastreamRepository datastreamRepository;

  @Test
  void getDatastreamsWithCorrectThing() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/datastream/listDatastreams/?id=saqn:t:43ae704"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else {
      assertTrue(response.getBody().contains("saqn:ds:d98d0a2"));
    }
  }

  @Test
  void getDatastreamWithWrongThing() throws Exception {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/datastream/listDatastreams/?id=xyz"), HttpMethod.GET, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else {
      assertTrue(response.getBody().contains("Failed to find the requested element"));
    }
  }

  @Test
  void exportToCsvValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/datastream/export/?id=saqn:ds:d98d0a2&limit=10000"), HttpMethod.GET,
        entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else {
      assertTrue(response.getBody().startsWith(
          "DATASTREAMID,FEATUREID,PHENOMENONEND,PHENOMENONSTART,RESULTNUMBER,RESULTSTRING,RESULTTIME,TYPE"));
    }
  }

  @Test
  void exportToCsvInvalid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/datastream/export/?id=xyz&limit=10000"), HttpMethod.GET,
        entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else {
      assertTrue(response.getBody() == null);
    }
  }

  @Test
  void testExportToCsvValid() {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/datastream/export/?id=saqn:ds:d98d0a2&start=2019-12-29:01-00-00&end=2020-01-01:01-00-00"), HttpMethod.GET,
        entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else {
      assertTrue(response.getBody().startsWith(
          "DATASTREAMID,FEATUREID,PHENOMENONEND,PHENOMENONSTART,RESULTNUMBER,RESULTSTRING,RESULTTIME,TYPE"));
    }
  }

  @Test
  void exportDatastream() {
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}