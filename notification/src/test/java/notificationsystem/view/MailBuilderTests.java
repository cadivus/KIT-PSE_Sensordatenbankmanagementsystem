package notificationsystem.view;

import notificationsystem.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailBuilderTests {

    @Autowired
    MailBuilder mailBuilder;
    @MockBean
    SubscriptionRepository subscriptionRepository;

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

    @Test
    public void testBuildAlert() throws JSONException {
        String mailAddress = "test";
        Sensor sensor = getTestSensor();

        Alert alert = mailBuilder.buildAlert(mailAddress, sensor);

        assertEquals(mailAddress, alert.getReceiverMail());
        assertEquals("Alert for sensor malfunction", alert.getSubject());

        String body = "The Sensorthings sensor: " + sensor.getName() + " with the ID: " + sensor.getId() +
                "at the location: " + sensor.getLocation() + " has malfunctioned and is currently not collecting data.";
        String message = body + "/n" + "This E-Mail was sent automatically by the E-Mail Notification System" +
                " of the 'Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System' Project.";
        assertEquals(message, alert.getMessage());
    }

    @Test
    public void testBuildReport() throws JSONException {
        String mailAddress = "test";
        Sensor sensor = getTestSensor();
        sensor.setActiveRate(0.5);
        LinkedList<ObservationStats> stats = new LinkedList<>();
        ObservationStats stats1 = new ObservationStats("id1", "name1", 3, 4, 5, 2);
        ObservationStats stats2 = new ObservationStats("id2", "name2", 6, 7, 8, 1);
        stats.add(stats1);
        stats.add(stats2);
        sensor.setStats(stats);

        Report report = mailBuilder.buildReport(mailAddress, sensor);

        assertEquals("test", report.getReceiverMail());
        assertEquals("Report for Sensorthings sensor: test-name. /n", report.getSubject());
        //assertEquals("", report.getMessage());
        //TODO: ??
        System.out.println(report.getMessage());
    }

    @Test
    public void testBuildConfirmationMail() {
        String mailAddress = "test";

        ConfirmationMail confirmationMail = mailBuilder.buildConfirmationMail(mailAddress);

        assertEquals("test", confirmationMail.getReceiverMail());
        assertEquals("Log-in attempt", confirmationMail.getSubject());
        assertNotNull(confirmationMail.getConfirmCode());

        String body = "A log-in to the 'Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System' was attempted with this E-Mail." +
                " Please enter the following code: " + confirmationMail.getConfirmCode()
                + " to confirm that this is your E-Mail address and complete the log-in.";
        String message = body + "/n" + "This E-Mail was sent automatically by the E-Mail Notification System" +
                " of the 'Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System' Project.";
        assertEquals(message, confirmationMail.getMessage());
    }

    @Configuration
    @Import(SubscriptionDAO.class)
    static class TestConfig {
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
        @Bean
        MailBuilder mailBuilder() {
            return new MailBuilder();
        }
    }
}
