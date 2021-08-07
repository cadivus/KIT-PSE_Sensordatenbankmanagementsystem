package notificationsystem.view;

import org.json.JSONArray;

/**
 * Alert e-mails are used to notify subscribers of sensors of a malfunction of that sensor.
 * The Alert class contains the e-mail addresses of the sender and the receiver, as well as a subject and a message.
 */
public class Alert extends EMail {

    /**
     * Constructs a new alert e-mail.
     * @param receiverMail e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     * @param attachment any data attached to the e-mail.
     */
    public Alert(String receiverMail, String subject, String message, JSONArray attachment) {
        super(receiverMail, subject, message, attachment);
    }
}
