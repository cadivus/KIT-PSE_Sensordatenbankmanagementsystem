package notificationsystem.model;

import java.util.List;

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

    private String data;
    private String failureRate;
    private String location;
    private List<String> categories;

    public Sensor(String data, String failureRate, String location, List<String> categories, List<String> subscribers) {
        this.data = data;
        this.failureRate = failureRate;
        this.location = location;
        this.categories = categories;
    }

    public String getData() {
        return data;
    }

    public String getFailureRate() {
        return failureRate;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFailureRate(String failureRate) {
        this.failureRate = failureRate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
