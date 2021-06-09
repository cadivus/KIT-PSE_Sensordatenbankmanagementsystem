package notificationsystem.view;

public class ConfirmationMail extends EMail {

    public ConfirmationMail(String senderMail, String receiverMails, String subject, String message, String attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }

    private String generateConfirmCode() {
        return null;
    }
}
