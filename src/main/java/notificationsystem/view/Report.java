package notificationsystem.view;

public class Report extends EMail {
    public Report(String senderMail, String receiverMails, String subject, String message, String attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
