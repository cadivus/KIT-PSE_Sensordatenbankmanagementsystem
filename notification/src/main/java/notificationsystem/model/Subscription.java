package notificationsystem.model;

import javax.persistence.*;
import java.time.LocalDate;

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

    private final static int ALLOCATION_SIZE = 1;

    @Id
    @SequenceGenerator(
            name = "subscription_sequence",
            sequenceName = "subscription_sequence",
            allocationSize = ALLOCATION_SIZE

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
    private String sensorId;

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

    @Column(
            name = "toggle_alert",
            nullable = false
    )
    private boolean toggleAlert;

    /**
     * Constructs a new subscription.
     * @param subscriberAddress e-mail address of the subscriber.
     * @param sensorId sensor the user subscribes to.
     * @param subTime time at which the user subscribed to the sensor.
     * @param reportInterval time period at which report e-mails are sent.
     */
    public Subscription(String subscriberAddress, String sensorId, LocalDate subTime, long reportInterval, boolean toggleAlert) {
        this.subscriberAddress = subscriberAddress;
        this.sensorId = sensorId;
        this.subTime = subTime;
        this.reportInterval = reportInterval;
        this.toggleAlert = toggleAlert;
    }

    /**
     * Constructs new Subscription.
     */
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
     * Gets the sensor the user is subscribed to.
     * @return the sensor the user is subscribed to.
     */
    public String getSensorId() {
        return sensorId;
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
     * Gets id of the subscription.
     * @return id of the subscription.
     */
    public long getId() {
        return id;
    }

    /**
     * Checks if toggleAlert is true or false
     * @return status of toggleAlert
     */
    public boolean isToggleAlert() {
        return toggleAlert;
    }
}
