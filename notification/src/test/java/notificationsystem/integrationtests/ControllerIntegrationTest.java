package notificationsystem.integrationtests;


import com.icegreen.greenmail.user.UserImpl;
import notificationsystem.controller.Controller;
import notificationsystem.controller.MailSender;
import notificationsystem.model.*;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private SubscriptionDAO subscriptionDAO;
    @MockBean
    SubscriptionRepository subscriptionRepository;
    @MockBean
    SensorDAO sensorDAO;
    @MockBean
    MailSender mailSender;
    @MockBean
    RestTemplate restTemplate;
    @Autowired
    Controller controller;

    @Test
    public void testGetConfirmCode() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders.get("http://localhost:8082/getConfirmCode/test")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(user("frontend").password("frontend").roles("ADMIN")))
                .andExpect(status().isOk());

        String code = controller.getHashMap().get("test");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8082/login/" + code + "&test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("frontend").password("frontend").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
    }

    @Test
    public void testPostSubscription() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders.post("http://localhost:8082/postSubscription?mailAddress=test&sensorID=test-id&reportInterval=7&toggleAlert=true")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(user("frontend").password("frontend").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostUnsubscribe() throws Exception {
        String mailAddress = "test";
        String sensorId = "test-id";
        Subscription subscription = new Subscription(mailAddress, sensorId, LocalDate.now(), 1, true);
        LinkedList<Subscription> subs = new LinkedList<>();
        subs.add(subscription);

        Mockito.when(subscriptionRepository.findAll()).thenReturn(subs);

        mockMvc.perform( MockMvcRequestBuilders.post("http://localhost:8082/postUnsubscribe?mailAddress=test&sensorID=test-id")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(user("frontend").password("frontend").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(subscriptionRepository).delete(subscription);
    }

    @Test
    public void testGetSubscriptions() throws Exception {
        String mailAddress = "test";
        String sensorId = "test-id";
        long reportInterval = 7;
        boolean toggleAlert = true;
        Subscription subscription = new Subscription(mailAddress, sensorId, LocalDate.now(), reportInterval, toggleAlert);
        LinkedList<Subscription> subs = new LinkedList<>();
        subs.add(subscription);

        Mockito.when(subscriptionRepository.findAll()).thenReturn(subs);

        mockMvc.perform( MockMvcRequestBuilders.get("http://localhost:8082/getSubscriptions/test")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(user("frontend").password("frontend").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    /*private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }*/

    @Configuration
    static class TestConfig {
        @Bean
        public TestRestTemplate testRestTemplate(){
            return new TestRestTemplate();
        }
        @Bean
        Controller controller() throws Exception {
            return new Controller(subscriptionDAO(), restTemplate(), mailSender(), sensorDAO());
        }
        @Bean
        SubscriptionDAO subscriptionDAO() {
            return new SubscriptionDAO(subscriptionRepository());
        }
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
        @Bean
        RestTemplate restTemplate() {
            return mock(RestTemplate.class);
        }
        @Bean
        MailSender mailSender() {
            return mock(MailSender.class);
        }
        @Bean
        SensorDAO sensorDAO() {
            return mock(SensorDAO.class);
        }
    }

}
