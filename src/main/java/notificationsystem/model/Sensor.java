package notificationsystem.model;

public class Sensor {

    private String data;
    private double failureRate;
    private String location;
    private String categories;

    public Sensor(String data, double failureRate, String location, String categories) {
        this.data = data;
        this.failureRate = failureRate;
        this.location = location;
        this.categories = categories;
    }

    public String getData() {
        return data;
    }

    public double getFailureRate() {
        return failureRate;
    }

    public String getLocation() {
        return location;
    }

    public String getCategories() {
        return categories;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFailureRate(double failureRate) {
        this.failureRate = failureRate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
