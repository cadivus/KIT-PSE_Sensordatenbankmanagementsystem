package notificationsystem.view;

import org.json.JSONArray;

import java.util.List;

/**
 * Report e-mails are sent to subscribers of a sensor at regular intervals dependant on the time of subscription and
 * user adjustments. The reports contain the data collected by the sensor over the last time period as well as
 * potentially interesting metadata such as failure rate over the last timer period.
 * The Report class represents such a report e-mail. It contains the e-mail addresses of sender and receiver, as well
 * as a subject and a message. Lastly the e-mail includes an attachment with the data collected by the sensor.
 */
public class Report extends EMail {

    /**
     * Constructs a new report e-mail.
     * @param senderMail e-mail address of the sender.
     * @param receiverMail e-mail address the mail is sent to.
     * @param subject subject of the e-mail.
     * @param message message body of the e-mail.
     * @param attachment any data attached to the e-mail.
     */
    public Report(String senderMail, String receiverMail, String subject, String message, JSONArray attachment, String sendername) {
        super(senderMail, receiverMail, subject, message, attachment, sendername);
    }
}
