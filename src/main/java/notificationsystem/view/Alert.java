package notificationsystem.view;

public class Alert extends EMail {
    public Alert(String senderMail, String receiverMails, String subject, String message, String attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
