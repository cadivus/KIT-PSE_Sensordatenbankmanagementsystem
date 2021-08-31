package notificationsystem.model;

/**
 * The ObservationStats class represents and stores calculated stats about an observation (eg. humidity).
 */
public class ObservationStats {

    private final String obsId;
    private final String obsName;
    private final double avg;
    private final double med;
    private final double stdv;
    private final double min;

    /**
     * Constructs a new instance of the ObservationStats class.
     * @param obsId Id of the observation type.
     * @param obsName Name of the observation type.
     * @param avg average value recorded.
     * @param med median value recorded.
     * @param stdv standard deviation of the recorded data.
     * @param min minimum of the values recorded.
     */
    public ObservationStats(String obsId, String obsName, double avg, double med, double stdv, double min) {
        this.obsId = obsId;
        this.obsName = obsName;
        this.avg = avg;
        this.med = med;
        this.stdv = stdv;
        this.min = min;
    }

    /**
     * Gets the id of the observation type.
     * @return id of the observation type.
     */
    public String getObsId() {
        return obsId;
    }

    /**
     * Gets the average of the recorded data.
     * @return average of the recorded data.
     */
    public double getAvg() {
        return avg;
    }

    /**
     * Gets the median of the recorded data.
     * @return median of the recorded data.
     */
    public double getMed() {
        return med;
    }

    /**
     * Gets the standard deviation of the recorded data.
     * @return standard deviation of the recorded data.
     */
    public double getStdv() {
        return stdv;
    }

    /**
     * Gets the minimum value of the recorded data.
     * @return minimum value of the recorded data.
     */
    public double getMin() {
        return min;
    }

    /**
     * Gets the name of the observation type.
     * @return name of the observation type.
     */
    public String getObsName() {
        return obsName;
    }
}
