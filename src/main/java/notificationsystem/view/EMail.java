package notificationsystem.view;

public class EMail {
    private String senderMail;
    private String receiverMails;
    private String subject;
    private String message;
    private String attachment;

    public EMail(String senderMail, String receiverMails, String subject, String message, String attachment) {
        this.senderMail = senderMail;
        this.receiverMails = receiverMails;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public String getReceiverMails() {
        return receiverMails;
    }

    public String getSubject() { return subject; }

    public String getMessage() {
        return message;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public void setReceiverMails(String receiverMails) {
        this.receiverMails = receiverMails;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
