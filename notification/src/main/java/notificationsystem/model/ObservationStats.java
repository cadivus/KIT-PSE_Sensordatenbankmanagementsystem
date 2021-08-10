package notificationsystem.model;

public class ObservationStats {

    private final String obsId;
    private final String obsName;
    private final double avg;
    private final double med;
    private final double stdv;
    private final double min;

    public ObservationStats(String obsId, String obsName, double avg, double med, double stdv, double min) {
        this.obsId = obsId;
        this.obsName = obsName;
        this.avg = avg;
        this.med = med;
        this.stdv = stdv;
        this.min = min;
    }

    public String getObsId() {
        return obsId;
    }

    public double getAvg() {
        return avg;
    }

    public double getMed() {
        return med;
    }

    public double getStdv() {
        return stdv;
    }

    public double getMin() {
        return min;
    }

    public String getObsName() {
        return obsName;
    }
}
