package notificationsystem.view;

import notificationsystem.controller.MailAddress;
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
    public Report(MailAddress senderMail, List<MailAddress> receiverMails, String subject, String message, JSONArray attachment) {
        super(senderMail, receiverMails, subject, message, attachment);
    }
}
