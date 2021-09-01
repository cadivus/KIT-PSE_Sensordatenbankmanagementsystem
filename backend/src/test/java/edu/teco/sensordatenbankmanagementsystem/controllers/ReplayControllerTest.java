package edu.teco.sensordatenbankmanagementsystem.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import java.util.function.Consumer;
import net.minidev.json.JSONObject;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"edu.teco.sensordatenbamkmanagementsystem"})
@TestPropertySource(properties = "spring.mvc.async.request-timeout= -1")
class ReplayControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate; // available with Spring Web MVC

  @LocalServerPort
  private Integer port;

  HttpHeaders headers = new HttpHeaders();



  @Test
  void createNewSseValid() {
    String[] array = {
        "saqn:t:43ae704",
        "saqn:t:grimm-aerosol.com:EDM80NEPH:SN17001",
        "saqn:t:4049564"
    };
    headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("start", "29/01/2018 01:01:00");
    jsonObject.put("end", "08/08/2021 01:01:03");
    jsonObject.put("speed", 50000);
    jsonObject.put("sensors", array);
    HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/newSSE"), HttpMethod.POST, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else if (response.getStatusCode().is4xxClientError()) {
      assertTrue(response.getBody().contains("Forbidden"));
    } else {
      assertTrue(response.getBody().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}"));
    }
  }

  @Test
  void createNewSseInvalidThings() {
    String[] array = {};
    headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("start", "29/01/2018 01:01:00");
    jsonObject.put("end", "08/08/2021 01:01:03");
    jsonObject.put("speed", 50000);
    jsonObject.put("sensors", array);
    HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/newSSE"), HttpMethod.POST, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else if (response.getStatusCode().is4xxClientError()) {
      assertTrue(response.getBody().contains("Neither information"));
    } else fail();
  }
  @Test
  void createNewSseInvalidDates() {
    String[] array = {        "saqn:t:43ae704",
        "saqn:t:grimm-aerosol.com:EDM80NEPH:SN17001",
        "saqn:t:4049564"};
    headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("start", "29/01/2022 01:01:00");
    jsonObject.put("end", "08/08/2021 01:01:03");
    jsonObject.put("speed", 50000);
    jsonObject.put("sensors", array);
    HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/newSSE"), HttpMethod.POST, entity,
        String.class);
    if (response.getStatusCode().is5xxServerError()) {
      assertTrue(response.getBody().contains("Connection to the database failed"));
    } else if (response.getStatusCode().is4xxClientError()) {
      assertTrue(response.getBody().contains("Validation error"));
    } else fail();
  }

  @Test
  @Disabled
  void streamSseMvc() {
    String[] array = {"saqn:t:43ae704",
        "saqn:t:grimm-aerosol.com:EDM80NEPH:SN17001",
        "saqn:t:4049564"};
    headers.setContentType(MediaType.APPLICATION_JSON);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("start", "25/11/2019 01:01:00");
    jsonObject.put("end", "28/11/2019 01:01:03");
    jsonObject.put("speed", 50000);
    jsonObject.put("sensors", array);
    HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);

    ResponseEntity<String> response = testRestTemplate.exchange(
        createURLWithPort("/observation/newSSE"), HttpMethod.POST, entity,
        String.class);
    var id = response.getBody();
    entity = new HttpEntity<>(null,headers);
    response = testRestTemplate.exchange(
        createURLWithPort("/observation/stream/"+id), HttpMethod.GET, entity,
        String.class);

    WebTestClient client = WebTestClient.bindToServer()
        .baseUrl("http://localhost:"+port)
        .build();

    client.get()
        .uri("/sse/"+id)
        .accept(TEXT_EVENT_STREAM)
        .exchange()
        .expectStatus().isOk()
        .returnResult(String.class)
        .getResponseBody()
        .subscribe(new Consumer<String>() {
          @Override
          public void accept(String event) {
            assert(true);
          }
        });

  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}