package notificationsystem.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.controller.Controller;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailIntegrationTests {

    @Autowired
    Controller controller;
    GreenMail greenMail;

    @BeforeAll
    private void setup() {
    greenMail = new GreenMail(ServerSetup.ALL);
    greenMail.start();
    }

    @AfterAll
    private void cleanup() {
        greenMail.stop();
    }

    @Test
    public void testSendAlert() throws MessagingException {
        String sensorId = "test-id";



        controller.sendAlert(sensorId);
        //TODO: Port problems expected
        assertTrue(greenMail.waitForIncomingEmail(5000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Alert for sensor malfunction", messages[0].getSubject());
    }

    //TODO: Backend compatibility
    @Test
    public void testSendReport() {
        String mailAddress = "test";
        String sensorId = "test-id";
    }

}
