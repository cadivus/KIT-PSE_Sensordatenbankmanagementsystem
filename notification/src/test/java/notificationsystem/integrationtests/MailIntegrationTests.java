package notificationsystem.integrationtests;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.controller.CheckerUtil;
import notificationsystem.controller.Controller;
import notificationsystem.controller.MailSender;
import notificationsystem.model.*;
import notificationsystem.view.MailBuilder;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import javax.ws.rs.core.Link;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailIntegrationTests {

    @Autowired
    SubscriptionDAO subscriptionDAO;
    @MockBean
    SubscriptionRepository subscriptionRepository;
    @MockBean
    SensorDAO sensorDAO;
    @MockBean
    RestTemplate restTemplate;
    @MockBean
    MailSender mailSender;

    private Sensor getTestSensor() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", "value1");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("key2", "value2");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        return new Sensor("test-id", "test-name", "test-desc", "test-prop", jsonArray);
    }

    private Subscription getTestSubscription() {
        return new Subscription("test", "test-id", LocalDate.now(), 7, true);
    }


    @Test
    public void testSendAlert() throws Exception {

        Controller controller = new Controller(subscriptionDAO, restTemplate, mailSender, sensorDAO);
        CheckerUtil checkerUtil = new CheckerUtil(controller, subscriptionDAO, sensorDAO, restTemplate);

        String sensorId = "test-id";
        LinkedList<Subscription> subscribers = new LinkedList<>();
        subscribers.add(getTestSubscription());
        LinkedList<Sensor> sensors = new LinkedList<>();
        sensors.add(getTestSensor());
        LinkedList<String> sensorIds = new LinkedList<>();
        sensorIds.add(getTestSensor().getId());
        Integer[] response = {-1};

        Mockito.when(sensorDAO.getAll()).thenReturn(sensors);
        Mockito.when(restTemplate.getForObject("http://backend:8081/active", Integer[].class, sensorIds, 3))
                        .thenReturn(response);
        Mockito.when(sensorDAO.get(sensorId)).thenReturn(getTestSensor());
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subscribers);

        checkerUtil.checkForSensorFailure();

        verify(restTemplate).getForObject("http://backend:8081/active", Integer[].class, sensorIds, 3);
        verify(mailSender).login();
        verify(subscriptionRepository, times(2)).findAll();
    }

    @Test
    public void testSendNoAlert() throws Exception {

        Controller controller = new Controller(subscriptionDAO, restTemplate, mailSender, sensorDAO);
        CheckerUtil checkerUtil = new CheckerUtil(controller, subscriptionDAO, sensorDAO, restTemplate);

        LinkedList<Sensor> sensors = new LinkedList<>();
        sensors.add(getTestSensor());
        LinkedList<String> sensorIds = new LinkedList<>();
        sensorIds.add(getTestSensor().getId());
        Integer[] response = {1};

        Mockito.when(sensorDAO.getAll()).thenReturn(sensors);
        Mockito.when(restTemplate.getForObject("http://backend:8081/active", Integer[].class, sensorIds, 3))
                .thenReturn(response);

        checkerUtil.checkForSensorFailure();

        verify(restTemplate).getForObject("http://backend:8081/active", Integer[].class, sensorIds, 3);
        verify(mailSender).login();
    }

    @Test
    public void testSendReport() throws Exception {
        Subscription subscription = getTestSubscription();
        subscription.setSubTime(LocalDate.now().minusDays(10));
        LinkedList<Subscription> subs = new LinkedList<>();
        subs.add(subscription);

        Sensor sensor = getTestSensor();
        String sensorId = sensor.getId();
        sensor.setActiveRate(0.5);
        LinkedList<ObservationStats> stats = new LinkedList<>();
        ObservationStats stats1 = new ObservationStats("id1", "name1", 3, 4, 5, 2);
        ObservationStats stats2 = new ObservationStats("id2", "name2", 6, 7, 8, 1);
        stats.add(stats1);
        stats.add(stats2);
        sensor.setStats(stats);

        Controller controller = new Controller(subscriptionDAO, restTemplate, mailSender, sensorDAO);
        CheckerUtil checkerUtil = new CheckerUtil(controller, subscriptionDAO, sensorDAO, restTemplate);


        Mockito.when(subscriptionRepository.findAll()).thenReturn(subs);
        Mockito.when(sensorDAO.get(sensorId)).thenReturn(sensor);

        checkerUtil.checkForReports();

        verify(sensorDAO).setStats(sensor, LocalDate.now().minusDays(subscription.getReportInterval()));
    }

    @Configuration
    @Import({SubscriptionDAO.class})
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
        RestTemplate restTemplate() {
            return mock(RestTemplate.class);
        }
        @Bean
        MailSender mailSender() {
            return mock(MailSender.class);
        }
    }

}
