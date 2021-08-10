package notificationsystem.model;

public class ObservationStats {

    private String obsId;
    private String obsName;
    private double avg;
    private double med;
    private double stdv;
    private double min;

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
