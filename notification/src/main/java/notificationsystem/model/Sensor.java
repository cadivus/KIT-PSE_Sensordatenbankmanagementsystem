package notificationsystem.model;

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

    String encoding_type;

    String metadata;

    String properties;

   /* @Transient
    @OneToMany(cascade = CascadeType.ALL)
    private List<Observation> observations;

    public List<Observation> getObservations() {
        return observations;
    }*/


    /*@Override
    public String toString() {
        return id;
    }*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEncoding_type() {
        return encoding_type;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getProperties() {
        return properties;
    }
}
