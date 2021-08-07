package notificationsystem.view;

import notificationsystem.model.Sensor;

/**
 * The MailBuilder class is responsible to build the different types of e-mails needed, alert, report and confirmation
 * mail, from the relevant information provided to it by the method-parameters. The class provides one method for
 * each of the e-mail type, each returning a finished mail of the relevant type.
 */
public class MailBuilder {

    private final static String SUBJECT_ALERT = "Alert for sensor malfunction";
    private final static String SUBJECT_CONFIRM = "Log-in attempt";
    private final static String MAIL_SIGNING = "This E-Mail was sent automatically by the E-Mail Notification System" +
            " of the 'Sensor Ultra-lightweight Supervision: Active Meteorological Observation General Use System' Project.";

    /**
     * The buildAlert method builds an alert e-mail which is sent to subscribers of a sensor when that sensor
     * malfunctions.
     * @param mailAddress e-mail address the mail is sent to.
     * @param sensor the sensor that malfunctioned.
     * @return The finished alert e-mail for the subscriber with the given e-mail address about the failure
     * of the given sensor.
     */
    public Alert buildAlert(String mailAddress, Sensor sensor) {
        //TODO: Add location
        String body = "The Sensorthings sensor: " + sensor.getName() + " with the ID: " + sensor.getId() +
                " has malfunctioned and is currently not collecting data.";
        String message = body + "/n" + MAIL_SIGNING;

        return new Alert(mailAddress, SUBJECT_ALERT, message, null);
    }

    /**
     * The buildReport method builds a report e-mail which is periodically sent to subscribers of a sensor and
     * contains information about the sensor and the data that sensor collected in the last time-interval.
     * @param mailAddress e-mail address the mail is sent to.
     * @param sensor the sensor the report is about.
     * @return The finished report e-mail for the subscriber with the given e-mail address about the given sensor.
     */
    public Report buildReport(String mailAddress, Sensor sensor, double activeRate) {
        String subject = "Report for Sensorthings sensor: " + sensor.getName();
        String opener = "The following is the regular report for the the Sensorthings sensor: " + sensor.getId()
                + "you are subscribed to.";
        String body = " The sensor was active "+ activeRate * 100 + " percent of the time since the last report.";
        String message = opener + "/n" + body + "/n" + MAIL_SIGNING;
        return new Report(mailAddress, subject, message, null);
    }

    /**
     * The buildConfirmationMail method build a confirmation e-mail which is sent to an e-mail address when a
     * user tries to log-in with that e-mail on the project website.
     * The confirmation e-mail contains a confirmation code which the user has to enter on the website to validate
     * the e-mail address.
     * @param mailAddress e-mail address the mail is sent to.
     * @return The finished confirmation e-mail to the e-mail address the user is trying to log-in with.
     */
    public ConfirmationMail buildConfirmationMail(String mailAddress) {
        ConfirmationMail confirmationMail = new ConfirmationMail(mailAddress, SUBJECT_CONFIRM, null, null);
        String message = "A log-in to ... was attempted with this E-Mail. Please enter the code:" + confirmationMail.getConfirmCode()
                + " to confirm that this is your E-Mail address and complete the log-in.";
        confirmationMail.setMessage(message);
        return confirmationMail;
    }
}
