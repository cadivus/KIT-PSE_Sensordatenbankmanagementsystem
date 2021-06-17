package notificationsystem.view;

import notificationsystem.controller.MailAddress;
import notificationsystem.model.Sensor;

/**
 * The MailBuilder class is responsible to build the different types of e-mails needed, alert, report and confirmation
 * mail, from the relevant information provided to it by the method-parameters. The class provides one method for
 * each of the e-mail type, each returning a finished mail of the relevant type.
 */
public class MailBuilder {

    public Alert buildAlert(MailAddress mailAddresses, Sensor sensor) {
        return null;
    }

    public Report buildReport(MailAddress mailAddress, Sensor sensor) {
        return null;
    }

    public ConfirmationMail buildConfirmationMail(MailAddress mailAddress) {
        return null;
    }
}
