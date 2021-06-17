package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import org.json.JSONArray;

import java.util.List;

/**
 * Confirmation e-mails are sent to an e-mail address when a user tries to log-in with that address on the website.
 * They are used to verify that users identity and act as a log-in mechanism.
 * The ConfirmationMail class represents such an e-mail and contains the e-mail addresses of the sender and receiver,
 * as well as a subject and a message. Furthermore, the class contains a method that generates a confirmation code.
 * The confirmation code is later sent to both the user trying to log-in and the website. This then allows the
 * website to validate the identity of the user.
 */
public class ConfirmationMail extends EMail {

    private ConfirmCode confirmCode;

    public ConfirmationMail(MailAddress senderMail, List<MailAddress> receiverMails, String subject, String message, JSONArray attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
        this.confirmCode = generateConfirmCode();
    }

    private ConfirmCode generateConfirmCode() {
        return null;
    }

    public ConfirmCode getConfirmCode() {
        return confirmCode;
    }
}
