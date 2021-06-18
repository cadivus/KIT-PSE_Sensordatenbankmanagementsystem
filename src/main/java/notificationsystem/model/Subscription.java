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

    /**
     * Constructs a new subscription.
     * @param subscriberAddress e-mail address of the subscriber.
     * @param sensor sensor the user subscribes to.
     * @param subTime time at which the user subscribed to the sensor.
     * @param reportInterval time period at which report e-mails are sent.
     */
    public Subscription(MailAddress subscriberAddress, Sensor sensor, LocalDateTime subTime, Period reportInterval) {
        this.subscriberAddress = subscriberAddress;
        this.sensor = sensor;
        this.subTime = subTime;
        this.reportInterval = reportInterval;
    }

    /**
     * Gets the e-mail address of the subscriber.
     * @return e-mail address of the subscriber.
     */
    public MailAddress getSubscriberAddress() {
        return subscriberAddress;
    }

    /**
     * Sets the e-mail address of the subscriber.
     * @param subscriberAddress e-mail address of the subscriber.
     */
    public void setSubscriberAddress(MailAddress subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }

    /**
     * Gets the sensor the user is subscribed to.
     * @return the sensor the user is subscribed to.
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * Sets the sensor the user is subscribed to.
     * @param sensor the sensor the user is subscribed to.
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * Gets the time at which the user subscribed to the sensor.
     * @return time of the subscription.
     */
    public LocalDateTime getSubTime() {
        return subTime;
    }

    /**
     * Sets the time at which the user subscribed to the sensor.
     * @param subTime time of the subscription.
     */
    public void setSubTime(LocalDateTime subTime) {
        this.subTime = subTime;
    }

    /**
     * Gets the time period between reports.
     * @return the time period between two reports.
     */
    public Period getReportInterval() {
        return reportInterval;
    }

    /**
     * Sets the time period between reports.
     * @param reportInterval the time period between two reports.
     */
    public void setReportInterval(Period reportInterval) {
        this.reportInterval = reportInterval;
    }
}
