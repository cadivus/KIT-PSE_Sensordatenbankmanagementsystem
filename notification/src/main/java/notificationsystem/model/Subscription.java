package notificationsystem.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

/**
 * The Subscription class stores data about a subscription of a single user to a single sensor. To allow for regular
 * reports to the subscriber, a timestamp of the date and time of the subscription is saved along with the time interval
 * at which the report mails are to be sent.
 * The class is mainly used to bundle this information into a single class, massively simplifying dealing with sensor-
 * subscriptions and reports.
 */
@Entity(name = "Subscription")
@Table(name = "Subscription")
public class Subscription {

    @Id
    @SequenceGenerator(
            name = "subscription_sequence",
            sequenceName = "subscription_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subscription_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "subscriber_address",
            nullable = false
    )
    private String subscriberAddress;

    @Column(
            name = "sensor",
            nullable = false
    )
    private UUID sensor;

    @Column(
            name = "sub_time",
            nullable = false
    )
    private LocalDate subTime;

    @Column(
            name = "report_interval",
            nullable = false
    )
    private long reportInterval;

    /**
     * Constructs a new subscription.
     * @param subscriberAddress e-mail address of the subscriber.
     * @param sensor sensor the user subscribes to.
     * @param subTime time at which the user subscribed to the sensor.
     * @param reportInterval time period at which report e-mails are sent.
     */
    public Subscription(String subscriberAddress, UUID sensor, LocalDate subTime, long reportInterval) {
        this.id = id;
        this.subscriberAddress = subscriberAddress;
        this.sensor = sensor;
        this.subTime = subTime;
        this.reportInterval = reportInterval;
    }

    public Subscription() {

    }

    /**
     * Gets the e-mail address of the subscriber.
     * @return e-mail address of the subscriber.
     */
    public String getSubscriberAddress() {
        return subscriberAddress;
    }

    /**
     * Sets the e-mail address of the subscriber.
     * @param subscriberAddress e-mail address of the subscriber.
     */
    public void setSubscriberAddress(String subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }

    /**
     * Gets the sensor the user is subscribed to.
     * @return the sensor the user is subscribed to.
     */
    public UUID getSensor() {
        return sensor;
    }

    /**
     * Sets the sensor the user is subscribed to.
     * @param sensor the sensor the user is subscribed to.
     */
    public void setSensor(UUID sensor) {
        this.sensor = sensor;
    }

    /**
     * Gets the time at which the user subscribed to the sensor.
     * @return time of the subscription.
     */
    public LocalDate getSubTime() {
        return subTime;
    }

    /**
     * Sets the time at which the user subscribed to the sensor.
     * @param subTime time of the subscription.
     */
    public void setSubTime(LocalDate subTime) {
        this.subTime = subTime;
    }

    /**
     * Gets the time period between reports.
     * @return the time period between two reports.
     */
    public long getReportInterval() {
        return reportInterval;
    }

    /**
     * Sets the time period between reports.
     * @param reportInterval the time period between two reports.
     */
    public void setReportInterval(long reportInterval) {
        this.reportInterval = reportInterval;
    }

    public long getId() {
        return id;
    }
}
