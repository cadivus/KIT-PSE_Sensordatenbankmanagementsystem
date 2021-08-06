package notificationsystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * The SensorDAO class implements the DAO interface to handle database queries regarding sensors. For that end it
 * provides get, save, delete and getAll methods designed to hide the actual database queries, offering a single
 * access point to all sensor related data and information.
 */
@Service
public class SensorDAO implements DAO<Sensor> {
    private final String backendUrl;
    private final String getSensorApi;
    private final String getAllSensorsApi;
    private final RestTemplate restTemplate;

    @Autowired
    public SensorDAO(String backendUrl, RestTemplate restTemplate) {
        this.backendUrl = backendUrl;
        this.getSensorApi = backendUrl + "/sensor/getSensor/";
        this.getAllSensorsApi = backendUrl + "/sensor/getAllSensors";

        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Sensor> get(Sensor sensor) {
        Sensor fetchedSensor = restTemplate.getForObject(getSensorApi + sensor.getId(), Sensor.class);
        return Optional.of(fetchedSensor);
    }

    /**
     * Gets a sensor specified by its unique ID from the database.
     * @param sensorID ID of the sensor to be fetched.
     * @return The sensor with the given ID.
     */
    public Sensor get(String sensorID) {
        return restTemplate.getForObject(getSensorApi + sensorID, Sensor.class);
    }

    @Override
    public List<Sensor> getAll() {
        Sensor[] sensors = restTemplate.getForObject(getAllSensorsApi, Sensor[].class);
        return Arrays.asList(sensors);
    }

}
