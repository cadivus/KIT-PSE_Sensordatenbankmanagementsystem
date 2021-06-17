package notificationsystem.controller;

import notificationsystem.view.EMail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * The MailSender is the final link in the chain of getting an e-mail to a user. It is responsible for sending these
 * e-mails to the recipients. The e-mails are sent via a normal gmail account. For this end, the class contains methods
 * for a login procedure and the sending of the finished e-mails.
 */
public class MailSender {

    private Session session;

    private void login(String smptServer, String smptport, String username, String password) {
        Properties properties = new Properties();

        properties.put("mail.smpt.auth", "true");
        properties.put("mail.smpt.socketFactory.port", "587");
        properties.put("mail.smpt.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smpt.host", "smpt.gmail.com");
        properties.put("mail.smpt.port", "587");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return super.getPasswordAuthentication();
            }
        };

        this.session = Session.getDefaultInstance(properties, authenticator);

    }

    public void send(EMail mail) {

    }
}
