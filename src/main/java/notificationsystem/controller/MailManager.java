package notificationsystem.controller;

import notificationsystem.model.Sensor;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.SubscriptionDAO;
import notificationsystem.view.ConfirmCode;
import notificationsystem.view.ConfirmationMail;
import notificationsystem.view.MailBuilder;
import notificationsystem.view.Report;

import java.util.List;
import java.util.UUID;

public class MailManager {

    private MailBuilder mailBuilder;
    private MailSender mailSender;

    public MailManager() {
        this.mailBuilder = new MailBuilder();
        this.mailSender = new MailSender();
    }

    public void alert(UUID sensorID) {
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        SensorDAO sensorDAO = new SensorDAO();
        Sensor sensor = sensorDAO.get(sensorID);
        List<MailAddress> subscribers = subscriptionDAO.getAllSubscribers(sensorID);
        //für jeden Listeneintrag mailBuilder.buildAlert() aufrufen und dann abschicken
    }

    public ConfirmCode confirmMail(MailAddress mailAddress) {
        ConfirmationMail confirmationMail = mailBuilder.buildConfirmationMail(mailAddress);
        mailSender.send(confirmationMail);
        return confirmationMail.getConfirmCode();
    }

    public void report(MailAddress mailAddress, UUID sensorID) {
        SensorDAO sensorDAO = new SensorDAO();
        Sensor sensor = sensorDAO.get(sensorID);
        Report report = mailBuilder.buildReport(mailAddress, sensor);
        mailSender.send(report);
    }

}
