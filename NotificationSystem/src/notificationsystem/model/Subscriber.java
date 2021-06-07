package notificationsystem.model;

public class Subscriber {

    private String subscribedSensors;
    private String mailAddress;

    public Subscriber(String subscribedSensors, String mailAddress) {
        this.subscribedSensors = subscribedSensors;
        this.mailAddress = mailAddress;
    }

    public String getSubscribedSensors() {
        return subscribedSensors;
    }

    public void setSubscribedSensors(String subscribedSensors) {
        this.subscribedSensors = subscribedSensors;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
