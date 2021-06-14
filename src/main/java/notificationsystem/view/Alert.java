package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import org.json.JSONArray;

import java.util.List;

public class Alert extends EMail {
    public Alert(MailAddress senderMail, List<MailAddress> receiverMails, String subject, String message, JSONArray attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
