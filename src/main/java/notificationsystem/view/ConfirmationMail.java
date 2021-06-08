package notificationsystem.view;

public class ConfirmationMail extends EMail {

    public ConfirmationMail(String senderMail, String receiverMails, String subject, String message, String sensorData, String sensorMetadata) {
        super(senderMail, receiverMails, subject, message, sensorData, sensorMetadata);
    }

    private String generateConfirmCode() {
        return null;
    }
}
