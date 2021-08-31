package notificationsystem.controller;

import notificationsystem.view.EMail;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * The MailSender is the final link in the chain of getting an e-mail to a user. It is responsible for sending these
 * e-mails to the recipients. The e-mails are sent via a normal gmail account. For this end, the class contains methods
 * for a login procedure and the sending of the finished e-mails.
 */
@Component
public class MailSender {

    private final static String SEND_STATE_ERROR = "Has to be logged in to send e-mails.";

    private Session session;

    /**
     *
     */
    public MailSender() {
    }

    /**
     * Log-in procedure needed for authentication before an e-mail can be sent. Also instantiates the session with
     * the smtp-server.
     * @param username username used for authentication purposes.
     * @param password password used for authentication purposes.
     */
    public void login(String username, String password) {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        this.session = Session.getDefaultInstance(properties, authenticator);

    }

    /**
     * Used to send the finished e-mails.
     * @param mail to be sent to its receiver.
     */
    public void send(EMail mail) throws MessagingException, IllegalStateException, UnsupportedEncodingException {
        if (session == null) {
            throw new IllegalStateException(SEND_STATE_ERROR);
        }

        MimeMessage message = new MimeMessage(session);
        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");

        message.setFrom(new InternetAddress(mail.getSenderMail(), mail.getSenderName()));
        message.setReplyTo(InternetAddress.parse(mail.getSenderMail(), false));
        message.setSubject(mail.getSubject(), "UTF-8");
        message.setText(mail.getMessage(), "UTF-8");
        message.setSentDate(new Date());

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getReceiverMail(), false));

        Transport.send(message);
    }
}
