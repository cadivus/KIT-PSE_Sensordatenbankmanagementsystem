package notificationsystem.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.mail.Message;
import javax.mail.MessagingException;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailIntegrationTests {

    @Autowired
    SubscriptionDAO subscriptionDAO;
    @Autowired
    SystemLoginDAO systemLoginDAO;
    @MockBean
    SubscriptionRepository subscriptionRepository;
    @MockBean
    SensorDAO sensorDAO;
    @MockBean
    SystemLoginRepository systemLoginRepository;
    @Autowired
    CheckerUtil checkerUtil;
    GreenMail greenMail;
    @Autowired
    Controller controller;

    @Before
    public void setup() {
    greenMail = new GreenMail(ServerSetup.ALL);
    greenMail.start();
    controller.setMailData("3025", "localhost");
    }

    @After
    public void cleanup() {
        greenMail.stop();
        controller.setMailData("587", "smtp.gmail.com");
    }

    @Test
    public void testSendAlert() throws MessagingException {
        String sensorId = "test-id";

        controller.sendAlert(sensorId);

        assertTrue(greenMail.waitForIncomingEmail(50000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Alert for sensor malfunction", messages[0].getSubject());
    }

    @Test
    public void testSendReport() throws JSONException, MessagingException {
        String mailAddress = "test";
        String sensorId = "saqn:t:7bd2cd3";
        Subscription subscription = new Subscription(mailAddress, sensorId, LocalDate.now().minusDays(8), 7, true);
        LinkedList<Subscription> subs = new LinkedList<>();
        subs.add(subscription);

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", "value1");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("key2", "value2");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        Sensor sensor = new Sensor("test-id", "test-name", "test-desc", "test-prop", jsonArray);
        sensor.setActiveRate(0.5);
        LinkedList<ObservationStats> stats = new LinkedList<>();
        ObservationStats stats1 = new ObservationStats("id1", "name1", 3, 4, 5, 2);
        ObservationStats stats2 = new ObservationStats("id2", "name2", 6, 7, 8, 1);
        stats.add(stats1);
        stats.add(stats2);
        sensor.setStats(stats);
        SystemLogin systemLogin = new SystemLogin();
        systemLogin.setUsername("sensornotificationsystemPSE@gmail.com");
        systemLogin.setPassword("cKqp4Wa83pLddBv");

        Mockito.when(subscriptionRepository.findAll()).thenReturn(subs);
        Mockito.when(sensorDAO.get(sensorId)).thenReturn(sensor);
        Mockito.when(systemLoginRepository.findById((long)1)).thenReturn(Optional.of(systemLogin));
        //Mockito.when(sensorDAO.setStats(sensor, LocalDate.now().minusDays(7)));

        checkerUtil.checkForReports();

        assertTrue(greenMail.waitForIncomingEmail(50000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Report for Sensorthings sensor: Crowdsensing Node (SDS011, 179552)", messages[0].getSubject());
    }

    @Configuration
    @Import({SubscriptionDAO.class, SystemLoginDAO.class, CheckerUtil.class, Controller.class})
    static class TestConfig {
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
        @Bean
        SensorDAO sensorDAO() {
            return mock(SensorDAO.class);
        }
        @Bean
        SystemLoginRepository systemLoginRepository() {
            return mock(SystemLoginRepository.class);
        }
        @Bean
        RestTemplate restTemplate() {
            return mock(RestTemplate.class);
        }
    }

}
