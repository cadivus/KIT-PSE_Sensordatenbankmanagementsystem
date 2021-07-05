package notificationsystem.view;

import org.json.JSONArray;

import java.util.List;

/**
 * The abstract EMail class represents a normal e-mail. All other e-mail types inherit from it. It contains the basic
 * parts of an e-mail: sender, receiver, subject, message and attachment. The class furthermore provides getters and
 * setters for these attributes.
 */
public abstract class EMail {
    private String senderMail;
    private String receiverMail;
    private String subject;
    private String message;
    private String senderName;
    private JSONArray attachment;

    /**
     * Constructs a new e-mail.
     * @param senderMail e-mail address of the sender.
     * @param receiverMail e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     * @param attachment any data attached to the e-mail.
     */
    public EMail(String senderMail, String receiverMail, String subject, String message, JSONArray attachment, String senderName) {
        this.senderMail = senderMail;
        this.receiverMail = receiverMail;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
        this.senderName = senderName;
    }

    /**
     * Gets the sender e-mail address.
     * @return e-mail address of the sender.
     */
    public String getSenderMail() {
        return senderMail;
    }

    /**
     * Sets the sender e-mail address.
     * @param senderMail e-mail address of the sender.
     */
    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    /**
     * Gets the e-mail address the mail is sent to.
     * @return the e-mail address the mail is sent to.
     */
    public String getReceiverMails() {
        return receiverMail;
    }

    /**
     * Sets the e-mail addresses the mail is sent to.
     * @param receiverMail the e-mail address the mail is sent to.
     */
    public void setReceiverMails(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    /**
     * Gets the subject body of the e-mail.
     * @return subject of the e-mail.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject body of the e-mail.
     * @param subject subject of the e-mail.
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
     * Gets the attachment sent with the e-mail.
     * @return JSON data attached to the e-mail.
     */
    public JSONArray getAttachment() {
        return attachment;
    }

    /**
     * Sets the attachment sent with the e-mail.
     * @param attachment JSON data attached to the e-mail.
     */
    public void setAttachment(JSONArray attachment) {
        this.attachment = attachment;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
