package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import notificationsystem.model.Sensor;

/**
 * The MailBuilder class is responsible to build the different types of e-mails needed, alert, report and confirmation
 * mail, from the relevant information provided to it by the method-parameters. The class provides one method for
 * each of the e-mail type, each returning a finished mail of the relevant type.
 */
public class MailBuilder {

    /**
     * The buildAlert method builds an alert e-mail which is sent to subscribers of a sensor when that sensor
     * malfunctions.
     * @param mailAddress e-mail address the mail is sent to.
     * @param sensor the sensor that malfunctioned.
     * @return The finished alert e-mail for the subscriber with the given e-mail address about the failure
     * of the given sensor.
     */
    public Alert buildAlert(MailAddress mailAddress, Sensor sensor) {
        return null;
    }

    /**
     * The buildReport method builds a report e-mail which is periodically sent to subscribers of a sensor and
     * contains information about the sensor and the data that sensor collected in the last time-interval.
     * @param mailAddress e-mail address the mail is sent to.
     * @param sensor the sensor the report is about.
     * @return The finished report e-mail for the subscriber with the given e-mail address about the given sensor.
     */
    public Report buildReport(MailAddress mailAddress, Sensor sensor) {
        return null;
    }

    /**
     * The buildConfirmationMail method build a confirmation e-mail which is sent to an e-mail address when a
     * user tries to log-in with that e-mail on the project website.
     * The confirmation e-mail contains a confirmation code which the user has to enter on the website to validate
     * the e-mail address.
     * @param mailAddress e-mail address the mail is sent to.
     * @return The finished confirmation e-mail to the e-mail address the user is trying to log-in with.
     */
    public ConfirmationMail buildConfirmationMail(MailAddress mailAddress) {
        return null;
    }
}
