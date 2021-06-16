package notificationsystem.controller;

import notificationsystem.model.SensorDAO;
import notificationsystem.view.ConfirmCode;
import notificationsystem.view.MailBuilder;

import java.util.UUID;

public class MailManager {

    private MailBuilder mailBuilder;
    private MailSender mailSender;

    //public MailManager(MailBuilder mailBuilder, MailSender mailSender) {
      //  this.mailBuilder = mailBuilder;
        //this.mailSender = mailSender;
    //}

    public void alert(UUID sensorID) {
        SensorDAO sensorDAO;

    }

    public ConfirmCode confirmMail(MailAddress mailAddress) {
        return null;
    }

    public void report(MailAddress mailAddress, UUID sensorID) {
        SensorDAO sensorDAO;
    }

}
