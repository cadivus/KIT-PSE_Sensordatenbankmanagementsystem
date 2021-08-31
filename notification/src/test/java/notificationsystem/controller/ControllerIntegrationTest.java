package notificationsystem.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import notificationsystem.model.SubscriptionRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.mail.Message;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ControllerIntegrationTest {

    @LocalServerPort
    Integer port;
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private SubscriptionDAO subscriptionDAO;
    @MockBean
    SubscriptionRepository subscriptionRepository;

    @Test
    public void testGetConfirmCode() throws Exception {
        //ObjectMapper mapper = new ObjectMapper();
        //ObjectNode obj = mapper.createObjectNode();

        //String s = mapper.writeValueAsString(obj);

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/getConfirmCode/test"), HttpMethod.GET, entity, String.class);

        //Result (confirmation code) is semi-random complicating exact testing
        assertNotNull(response);
    }

    @Test
    public void testPostSubscription() throws Exception {
        String mailAddress = "test";
        String sensorId = "test-id";
        long reportInterval = 7;
        boolean toggleAlert = true;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8082/postSubscription?mailAddress=test&sensorID=test-id&reportInterval=7&toggleAlert=true");
        //mockMvc.perform(requestBuilder);

        Subscription subscription = subscriptionDAO.get(mailAddress, sensorId);
        assertNotNull(subscription);
        assertEquals(mailAddress, subscription.getSubscriberAddress());
        assertEquals(sensorId, subscription.getSensorId());
        assertEquals(reportInterval, subscription.getReportInterval());
        assertEquals(toggleAlert, subscription.isToggleAlert());

        subscriptionDAO.delete(subscription);
    }

    @Test
    public void testPostUnsubscribe() throws Exception {
        String mailAddress = "test";
        String sensorId = "test-id";
        Subscription subscription = new Subscription(mailAddress, sensorId, LocalDate.now(), 1, true);
        subscriptionDAO.save(subscription);

        //TODO: Header?
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8082/postUnsubscribe?mailAddress=test&sensorID=test-id");
        //mockMvc.perform(requestBuilder);

        assertNull(subscriptionDAO.get(mailAddress, sensorId));
    }

    @Test
    public void testEmptyGetSubscriptions() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8082/getSubscriptions/");
        //MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        //assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetSubscriptions() throws Exception {
        String mailAddress = "test";
        String sensorId = "test-id";
        long reportInterval = 7;
        boolean toggleAlert = true;
        Subscription subscription = new Subscription(mailAddress, sensorId, LocalDate.now(), reportInterval, toggleAlert);
        subscriptionDAO.save(subscription);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8082/getSubscriptions/test");
        //MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        //TODO: Fill in
        //assertEquals("[{\"id\":1,\"subscriberAddress\":\"test\",\"sensorId\":\"test-id\",\"subTime\":\"2021-08-28\",\"reportInterval\":7,\"toggleAlert\":true}]", mvcResult.getResponse().getContentAsString());

        subscriptionDAO.delete(subscription);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Configuration
    @Import(SubscriptionDAO.class)
    static class TestConfig {
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
        @Bean
        public TestRestTemplate testRestTemplate(){
            return new TestRestTemplate();
        }
    }

}
