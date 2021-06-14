package notificationsystem.controller;

import notificationsystem.view.MailBuilder;

import java.util.UUID;

public class MailManager {

    private MailBuilder mailBuilder;
    private MailSender mailSender;

    public void alert(UUID sensorID) {

    }

    public ConfirmCode confirmMail(MailAddress mailAddress) {
        return null;
    }

    public void report(MailAddress mailAddress, UUID sensorID) {

    }

    public void addSubscription() {

    }

    public void deleteSubscription() {

    }
}
