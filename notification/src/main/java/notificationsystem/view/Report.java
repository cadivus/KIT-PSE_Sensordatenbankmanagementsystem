package notificationsystem.view;

/**
 * Report e-mails are sent to subscribers of a sensor at regular intervals dependent on the time of subscription and
 * user adjustments. The reports interesting metadata calculated over the last timer period.
 * The Report class represents such a report e-mail. It contains the e-mail addresses of sender and receiver, as well
 * as a subject and a message.
 */
public class Report extends EMail {

    /**
     * Constructs a new report e-mail.
     * @param receiverMail e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     */
    public Report(String receiverMail, String subject, String message) {
        super(receiverMail, subject, message);
    }
}
