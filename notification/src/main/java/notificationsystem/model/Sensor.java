package notificationsystem.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

/**
 * The Sensor class represents the air-quality sensors stationed in Augsburg (Called 'Things' in the Sensorthings database).
 * Each sensor collects data, has a failure rate, a location, and is part of one, none or multiple categories.
 * The Sensor class also provides access to these data points.
 * In the E-Mail-Notification System, this class is mainly used organize and transport data collected by and about
 * sensors. This information is then needed for report- and alert-mails.
 * The Sensor class does not hold information about the subscribers to a sensor, as the subscriptions are handled in
 * a distinct Subscription class.
 */
public class Sensor {

    private final static int COORDINATES_IN_LOCATION = 0;
    private final static String COORDINATES_KEY = "id";

    String id;
    String name;
    String description;
    String properties;
    String location;
    double activeRate;
    LinkedList<ObservationStats> stats;

    /**
     * Constructs a new instance of the sensor class.
     * @param id id of the sensor.
     * @param name name of the sensor.
     * @param description a short description of the sensor.
     * @param properties a short description of some properties of the sensor.
     * @param location a JSONArray containing information about the location of the sensor.
     * @throws JSONException throws this exception if the conversion from JSONArray to JSONObject of the parameter
     * location fails.
     */
    public Sensor(String id, String name, String description, String properties, JSONArray location) throws JSONException {
        this.id = id;
        this.name = name;
        this.description = description;
        this.properties = properties;
        this.location = location.getJSONObject(COORDINATES_IN_LOCATION).getString(COORDINATES_KEY);
    }

    public Sensor() {}

    /**
     * Gets unique id of the sensor.
     * @return id of the sensor.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the sensor.
     * @return name of the sensor.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of a sensor.
     * @return description of a sensor.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the coordinates of the sensor.
     * @return coordinates of the sensor.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the average times a sensor is active a day.
     * @return active rate of the sensor.
     */
    public double getActiveRate() {
        return activeRate;
    }

    /**
     * Gets a list of stats about the data observed by the sensor.
     * @return List of stats about the observed data.
     */
    public LinkedList<ObservationStats> getStats() {
        return stats;
    }

    /**
     * Sets the id of a sensor.
     * @param id new id of the sensor.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the active rate of a sensor.
     * @param activeRate new active rate of the sensor.
     */
    public void setActiveRate(double activeRate) {
        this.activeRate = activeRate;
    }

    /**
     * Sets a new list of stats for the sensor.
     * @param stats new list of stats.
     */
    public void setStats(LinkedList<ObservationStats> stats) {
        this.stats = stats;
    }
}
