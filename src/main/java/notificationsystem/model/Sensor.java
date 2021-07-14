package notificationsystem.model;

import java.util.List;
import java.util.UUID;

/**
 * The Sensor class represents the air-quality sensors stationed in Augsburg. Each sensor collects data, has a
 * failure rate, a location, and is part of one, none or multiple categories. The Sensor class also provides access to
 * these data points.
 * In the E-Mail-Notification System, this class is mainly used organize and transport data collected by and about
 * sensors. This information is then needed for report- and alert-mails.
 * The Sensor class does not hold information about the subscribers to a sensor, as the subscriptions are handled in
 * a distinct Subscription class.
 */
public class Sensor {

    private UUID id;
    private String data;
    private String failureRate;
    private String location;
    private List<String> categories;

    /**
     * Constructs a new sensor.
     * @param data data the sensor collected.
     * @param failureRate percentage of time the sensor was inactive.
     * @param location gps-coordinates of the sensor location.
     * @param categories list of categories describing the sensor and its properties.
     */
    public Sensor(UUID id, String data, String failureRate, String location, List<String> categories) {
        this.id = id;
        this.data = data;
        this.failureRate = failureRate;
        this.location = location;
        this.categories = categories;
    }

    /**
     * Gets the data a sensor collected.
     * @return String containing the collected data.
     */
    public String getData() {
        return data;
    }

    /**
     * Gets the failure rate of the sensor.
     * @return String representing the failure rate of the sensor in percent.
     */
    public String getFailureRate() {
        return failureRate;
    }

    /**
     * Gets the location of the sensor.
     * @return Location of the sensor as a gps-coordinate.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets a list of categories describing the sensor.
     * @return List of Strings with the category names.
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Sets the data collected by the sensor.
     * @param data data collected by the sensor.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Sets the failure rate of a sensor.
     * @param failureRate String representing the failure rate of the sensor in percent.
     */
    public void setFailureRate(String failureRate) {
        this.failureRate = failureRate;
    }

    /**
     * Sets the location of a sensor.
     * @param location Location of the sensor as a gps-coordinate.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the categories of the sensor.
     * @param categories List of Strings with the category names.
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
