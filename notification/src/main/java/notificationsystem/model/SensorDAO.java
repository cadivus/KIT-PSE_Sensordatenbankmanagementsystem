package notificationsystem.model;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * The SensorDAO class implements the DAO interface to handle database queries regarding sensors. For that end it
 * provides get, save, delete and getAll methods designed to hide the actual database queries, offering a single
 * access point to all sensor related data and information.
 */
public class SensorDAO implements DAO<Sensor> {
    //TODO: Add correct api addresses
    private static final String GET_SENSOR_API = "http://localhost:8080/sensor/getSensor/{id}";
    private static final String GET_ALL_SENSORS_API = "GET http://localhost:8080/sensor/getAllSensors";
    static RestTemplate restTemplate;

    public SensorDAO() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Optional<Sensor> get(Sensor sensor) {
        Sensor fetchedSensor = restTemplate.getForObject(GET_SENSOR_API, Sensor.class, sensor);
        Optional<Sensor> result = Optional.of(fetchedSensor);
        return result;
    }

    /**
     * Gets a sensor specified by its unique ID from the database.
     * @param sensorID ID of the sensor to be fetched.
     * @return The sensor with the given ID.
     */
    public Sensor get(UUID sensorID) {
        Map<String, UUID> param = new HashMap<>();
        param.put("id", sensorID);

        return restTemplate.getForObject(GET_SENSOR_API, Sensor.class, param);
    }

    @Override
    public List<Sensor> getAll() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<String> result = restTemplate.exchange(GET_ALL_SENSORS_API, HttpMethod.GET, entity, String.class);
        String allSensors = result.getBody();
        //TODO: Convert to List of sensors; Change fetch method if necessary
        return null;
    }

}
