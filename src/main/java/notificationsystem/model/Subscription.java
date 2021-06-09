package notificationsystem.model;

public class Subscription {

    private String subscriberAddress;
    private Sensor sensor;
    private String subTime;
    private String reportInterval;

    public Subscription(String subscriberAddress, Sensor sensor, String subTime, String reportInterval) {
        this.subscriberAddress = subscriberAddress;
        this.sensor = sensor;
        this.subTime = subTime;
        this.reportInterval = reportInterval;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public String getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(String reportInterval) {
        this.reportInterval = reportInterval;
    }

    public String getSubscriberAddress() {
        return subscriberAddress;
    }

    public void setSubscriberAddress(String subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }
}
