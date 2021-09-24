package notificationsystem.controller;

import notificationsystem.view.Alert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailSenderTest {

    @Autowired
    private MailSender mailSender;

    @Test
    public void testLogin() {
        Alert alert = new Alert("test", "test", "test");

        mailSender.login();

        //Fails when no session active
        try {
            mailSender.send(alert);
        } catch(IllegalStateException exception) {
            fail();
        } catch (MessagingException | UnsupportedEncodingException unsupportedEncodingException) {
            //Failed to send, not to log-in.
        }
    }

    @Test
    public void testSend() {
        Alert alert = new Alert("sensornotificationsystemPSE@gmail.com", "test", "test");
        mailSender.login();

        try {
            mailSender.send(alert);
        } catch (IllegalStateException | MessagingException | UnsupportedEncodingException exception) {
            fail();
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        MailSender mailSender() {
            return new MailSender();
        }
    }

}
