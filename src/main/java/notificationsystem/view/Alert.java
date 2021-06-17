package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import org.json.JSONArray;

import java.util.List;

/**
 * Alert e-mails are used to notify subscribers of sensors of a malfunction of that sensor.
 * The Alert class contains the e-mail addresses of the sender and the receiver, as well as a subject and a message.
 */
public class Alert extends EMail {
    public Alert(MailAddress senderMail, List<MailAddress> receiverMails, String subject, String message, JSONArray attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
