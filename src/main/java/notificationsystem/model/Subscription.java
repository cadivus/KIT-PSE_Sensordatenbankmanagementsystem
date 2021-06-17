package notificationsystem.model;

import notificationsystem.controller.MailAddress;

import java.time.LocalDateTime;
import java.time.Period;

/**
 * The Subscription class stores data about a subscription of a single user to a single sensor. To allow for regular
 * reports to the subscriber, a timestamp of the date and time of the subscription is saved along with the time interval
 * at which the report mails are to be sent.
 * The class is mainly used to bundle this information into a single class, massively simplifying dealing with sensor-
 * subscriptions and reports.
 */
public class Subscription {

    private MailAddress subscriberAddress;
    private Sensor sensor;
    private LocalDateTime subTime;
    private Period reportInterval;

    public Subscription(MailAddress subscriberAddress, Sensor sensor, LocalDateTime subTime, Period reportInterval) {
        this.subscriberAddress = subscriberAddress;
        this.sensor = sensor;
        this.subTime = subTime;
        this.reportInterval = reportInterval;
    }

    public MailAddress getSubscriberAddress() {
        return subscriberAddress;
    }

    public void setSubscriberAddress(MailAddress subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getSubTime() {
        return subTime;
    }

    public void setSubTime(LocalDateTime subTime) {
        this.subTime = subTime;
    }

    public Period getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(Period reportInterval) {
        this.reportInterval = reportInterval;
    }
}
