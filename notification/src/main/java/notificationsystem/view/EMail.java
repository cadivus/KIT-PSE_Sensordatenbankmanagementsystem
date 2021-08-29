package notificationsystem.view;

/**
 * The abstract EMail class represents a normal e-mail. All other e-mail types inherit from it. It contains the basic
 * parts of an e-mail: sender, receiver, subject and message. The class furthermore provides getters and
 * setters for these attributes, if needed.
 */
public abstract class EMail {
    private final String senderMail;
    private final String receiverMail;
    private final String subject;
    private String message;
    private final String senderName;

    /**
     * Constructs a new e-mail.
     * @param receiverMail e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     */
    public EMail( String receiverMail, String subject, String message) {
        this.senderMail = "sensornotificationsystemPSE@gmail.com";
        this.receiverMail = receiverMail;
        this.subject = subject;
        this.message = message;
        this.senderName = "PSE";
    }

    /**
     * Gets the sender e-mail address.
     * @return e-mail address of the sender.
     */
    public String getSenderMail() {
        return senderMail;
    }

    /**
     * Gets the e-mail address the mail is sent to.
     * @return the e-mail address the mail is sent to.
     */
    public String getReceiverMails() {
        return receiverMail;
    }

    /**
     * Gets the subject body of the e-mail.
     * @return subject of the e-mail.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Gets the message sent in the e-mail.
     * @return message body of the e-mail.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message sent in the e-mail.
     * @param message message body of the e-mail.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the name of the sender of the e-mail.
     * @return senderName name of the sender.
     */
    public String getSenderName() {
        return senderName;
    }

}
