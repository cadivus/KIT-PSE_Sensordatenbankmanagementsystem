package notificationsystem.model;

import java.util.List;

public class Subscriber {

    private List<Subscription> subscribedSensors;
    private String mailAddress;

    public Subscriber(List<Subscription> subscribedSensors, String mailAddress) {
        this.subscribedSensors = subscribedSensors;
        this.mailAddress = mailAddress;
    }

    public List<Subscription> getSubscribedSensors() {
        return subscribedSensors;
    }

    public void setSubscribedSensors(List<Subscription> subscribedSensors) {
        this.subscribedSensors = subscribedSensors;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
