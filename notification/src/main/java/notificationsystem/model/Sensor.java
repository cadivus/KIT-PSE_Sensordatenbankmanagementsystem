package notificationsystem.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

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

    String id;
    String name;
    String description;
    String properties;
    String location;
    double activeRate;
    LinkedList<ObservationStats> stats;

    public Sensor(String id, String name, String description, String properties, JSONArray location) throws JSONException {
        this.id = id;
        this.name = name;
        this.description = description;
        this.properties = properties;
        this.location = location.getJSONObject(0).getString("id");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProperties() {
        return properties;
    }

    public String getLocation() {
        return location;
    }

    public double getActiveRate() {
        return activeRate;
    }

    public LinkedList<ObservationStats> getStats() {
        return stats;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActiveRate(double activeRate) {
        this.activeRate = activeRate;
    }

    public void setStats(LinkedList<ObservationStats> stats) {
        this.stats = stats;
    }
}
