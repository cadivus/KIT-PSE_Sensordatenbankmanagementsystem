package notificationsystem.model;

import java.util.List;

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
