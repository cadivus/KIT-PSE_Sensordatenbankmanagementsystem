package notificationsystem.view;

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

    private String confirmCode;

    /**
     * Constructs a new confirmation e-mail.
     * @param receiverMail e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     * @param attachment any data attached to the e-mail.
     */
    public ConfirmationMail(String receiverMail, String subject, String message, JSONArray attachment) {
        super(receiverMail, subject, message, attachment);
        this.confirmCode = generateConfirmCode();
    }

    /**
     * The generateConfirmCode method is used to generate a random confirmation code which can be sent to a user and
     * the project website to the validate the users identity.
     * @return confirmation code used for log-in.
     */
    private String generateConfirmCode() {
        return null;
    }

    /**
     * Gets the confirmation code sent in the e-mail to validate a users e-mail.
     * @return the confirmation code sent in the e-mail.
     */
    public String getConfirmCode() {
        return confirmCode;
    }
}
