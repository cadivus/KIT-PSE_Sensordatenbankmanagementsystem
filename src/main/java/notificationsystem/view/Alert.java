package notificationsystem.view;

import org.json.JSONArray;

import java.util.List;

/**
 * Alert e-mails are used to notify subscribers of sensors of a malfunction of that sensor.
 * The Alert class contains the e-mail addresses of the sender and the receiver, as well as a subject and a message.
 */
public class Alert extends EMail {

    /**
     * Constructs a new alert e-mail.
     * @param senderMail e-mail address of the sender.
     * @param receiverMails e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     * @param attachment any data attached to the e-mail.
     */
    public Alert(String senderMail, List<String> receiverMails, String subject, String message, JSONArray attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
