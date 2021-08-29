package notificationsystem.controller;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import notificationsystem.model.Subscription;
import notificationsystem.model.SubscriptionDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.mail.Message;
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SubscriptionDAO subscriptionDAO;
    @Autowired
    private Controller controller;
    private GreenMail greenMail;

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
    public void testGetConfirmCode() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8082/getConfirmCode/test");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Result (confirmation code) is semi-random complicating exact testing
        assertNotNull(mvcResult);
        assertEquals(8, mvcResult.getResponse().getContentLength());
        assertTrue(greenMail.waitForIncomingEmail(50000, 1));
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("Log-in attempt", messages[0].getSubject());
    }

    @Test
    public void testPostSubscription() throws Exception {
        String mailAddress = "test";
        String sensorId = "test-id";
        long reportInterval = 7;
        boolean toggleAlert = true;

        //TODO: Header?
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8082/postSubscription?mailAddress=test&sensorID=test-id&reportInterval=7&toggleAlert=true");
        mockMvc.perform(requestBuilder);

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
        mockMvc.perform(requestBuilder);

        assertNull(subscriptionDAO.get(mailAddress, sensorId));
    }

    @Test
    public void testEmptyGetSubscriptions() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8082/getSubscriptions/");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals("", mvcResult.getResponse().getContentAsString());
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
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        //TODO: Fill in
        assertEquals("", mvcResult.getResponse().getContentAsString());

        subscriptionDAO.delete(subscription);
    }

}
