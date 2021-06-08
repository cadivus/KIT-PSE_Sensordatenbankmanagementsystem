package notificationsystem.view;

public class EMail {
    private String senderMail;
    private String receiverMails;
    private String subject;
    private String message;
    private String sensorData;
    private String sensorMetadata;

    public EMail(String senderMail, String receiverMails, String subject, String message, String sensorData, String sensorMetadata) {
        this.senderMail = senderMail;
        this.receiverMails = receiverMails;
        this.subject = subject;
        this.message = message;
        this.sensorData = sensorData;
        this.sensorMetadata = sensorMetadata;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public String getReceiverMails() {
        return receiverMails;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getSensorData() {
        return sensorData;
    }

    public String getSensorMetadata() {
        return sensorMetadata;
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

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }

    public void setSensorMetadata(String sensorMetadata) {
        this.sensorMetadata = sensorMetadata;
    }
}
