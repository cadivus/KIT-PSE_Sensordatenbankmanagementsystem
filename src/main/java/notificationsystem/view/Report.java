package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import org.json.JSONArray;

import java.util.List;

public class Report extends EMail {
    public Report(MailAddress senderMail, List<MailAddress> receiverMails, String subject, String message, JSONArray attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
