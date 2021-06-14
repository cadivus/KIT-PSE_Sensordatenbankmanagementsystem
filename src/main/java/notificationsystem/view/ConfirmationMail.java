package notificationsystem.view;

import notificationsystem.controller.ConfirmCode;
import notificationsystem.controller.MailAddress;
import org.json.JSONArray;

import java.util.List;

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
