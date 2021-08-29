package notificationsystem.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
    @Autowired
    CheckerUtil checkerUtil;
    GreenMail greenMail;

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
        subscriptionDAO.save(subscription);

        checkerUtil.checkForReports();

        assertTrue(greenMail.waitForIncomingEmail(50000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Report for Sensorthings sensor: Crowdsensing Node (SDS011, 179552)", messages[0].getSubject());
    }

}
