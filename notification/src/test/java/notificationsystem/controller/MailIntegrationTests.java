package notificationsystem.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.controller.Controller;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailIntegrationTests {

    @Autowired
    Controller controller;
    @Autowired
    SubscriptionDAO subscriptionDAO;
    GreenMail greenMail;

    @Before
    private void setup() {
    greenMail = new GreenMail(ServerSetup.ALL);
    greenMail.start();
    //TODO: Set port to 3025, host to localhost
    }

    @After
    private void cleanup() {
        greenMail.stop();
    }

    @Test
    public void testSendAlert() throws MessagingException {
        String sensorId = "test-id";

        controller.sendAlert(sensorId);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Alert for sensor malfunction", messages[0].getSubject());
    }

    //TODO: Echten Sensor aus dem backend
    @Test
    public void testSendReport() throws JSONException, MessagingException {
        String mailAddress = "test";
        String sensorId = "test-id";
        Subscription subscription = new Subscription(mailAddress, sensorId, LocalDate.now(), 7, true);
        subscriptionDAO.save(subscription);

        controller.sendReport(mailAddress, sensorId);

        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Report for Sensorthings sensor: ", messages[0].getSubject());
    }

}
