package notificationsystem.controller;

import notificationsystem.controller.Controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailIntegrationTests {

    @Autowired
    Controller controller;

    @Test
    public void testSendAlert() {
        String sensorId = "test-id";

    }

    //TODO: Backend compatibility
    @Test
    public void testSendReport() {
        String mailAddress = "test";
        String sensorId = "test-id";
    }

}
