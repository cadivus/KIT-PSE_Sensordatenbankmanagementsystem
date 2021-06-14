package notificationsystem.model;

import notificationsystem.controller.MailAddress;

import java.util.List;

public class Subscriber {

    private List<Subscription> subscribedSensors;
    private MailAddress mailAddress;

    public Subscriber(List<Subscription> subscribedSensors, MailAddress mailAddress) {
        this.subscribedSensors = subscribedSensors;
        this.mailAddress = mailAddress;
    }

    public List<Subscription> getSubscribedSensors() {
        return subscribedSensors;
    }

    public void setSubscribedSensors(List<Subscription> subscribedSensors) {
        this.subscribedSensors = subscribedSensors;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(MailAddress mailAddress) {
        this.mailAddress = mailAddress;
    }
}
