package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import org.json.JSONArray;

import java.util.List;

public class EMail {
    private MailAddress senderMail;
    private List<MailAddress> receiverMails;
    private String subject;
    private String message;
    private JSONArray attachment;

    public EMail(MailAddress senderMail, List<MailAddress> receiverMails, String subject, String message, JSONArray attachment) {
        this.senderMail = senderMail;
        this.receiverMails = receiverMails;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
    }

    public MailAddress getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(MailAddress senderMail) {
        this.senderMail = senderMail;
    }

    public List<MailAddress> getReceiverMails() {
        return receiverMails;
    }

    public void setReceiverMails(List<MailAddress> receiverMails) {
        this.receiverMails = receiverMails;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONArray getAttachment() {
        return attachment;
    }

    public void setAttachment(JSONArray attachment) {
        this.attachment = attachment;
    }
}
