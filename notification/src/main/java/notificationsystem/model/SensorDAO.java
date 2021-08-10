package notificationsystem.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

/**
 * The SensorDAO class implements the DAO interface to handle database queries regarding sensors. For that end it
 * provides get and getAll methods designed to hide the actual database queries, offering a single
 * access point to all sensor related data and information.
 */
@Service
public class SensorDAO implements DAO<Sensor> {
    private final String getThingApi;
    private final String getAllSensorsApi;
    private final String getActiveRateApi;
    private final String getAllObsApi;
    private final String getStatsApi;
    private final RestTemplate restTemplate;

    @Autowired
    public SensorDAO(String backendUrl, RestTemplate restTemplate) {
        this.getThingApi = backendUrl + "/sensor/thing/";
        this.getAllSensorsApi = backendUrl + "/sensor/getAllSensors";
        this.getActiveRateApi = backendUrl + "/sensor/active_rate";
        this.getAllObsApi = backendUrl + "/observation/getAllObs";
        this.getStatsApi = backendUrl + "/stats";
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Sensor> get(Sensor sensor) {
        Sensor fetchedSensor = restTemplate.getForObject(getThingApi + sensor.getId(), Sensor.class);
        return Optional.of(fetchedSensor);
    }

    /**
     * Gets a sensor specified by its unique ID from the database.
     * @param sensorID ID of the sensor to be fetched.
     * @return The sensor with the given ID.
     */
    public Sensor get(String sensorID) {
        return restTemplate.getForObject(getThingApi + sensorID, Sensor.class);
    }

    @Override
    public List<Sensor> getAll() {
        Sensor[] sensors = restTemplate.getForObject(getAllSensorsApi, Sensor[].class);
        return Arrays.asList(sensors);
    }

    public void setStats(Sensor sensor, LocalDate timeframe) throws JSONException {

        //Get activeRate
        Double[] result = restTemplate.getForObject(getActiveRateApi, Double[].class, List.of(sensor.getId()), timeframe);
        sensor.setActiveRate(result[0]);

        //Get stats
        LinkedList<ObservationStats> stats = new LinkedList<>();
        LinkedList<String> obsIds = new LinkedList<>();
        LinkedList<String> obsNames = new LinkedList<>();
        JSONArray observationIds = restTemplate.getForObject(getAllObsApi, JSONArray.class);
        for (int i = 0; i < observationIds.length(); i++) {
            JSONObject entry = observationIds.getJSONObject(i);
            String obsId = entry.getString("id");
            String name = entry.getString("name");
            obsIds.add(obsId);
            obsNames.add(name);
        }

        //Get stats for each observation type
        LinkedList<ObservationStats> observationStats = new LinkedList<>();
        JSONArray allStats = restTemplate.getForObject(getStatsApi, JSONArray.class, List.of(sensor.getId()), obsIds, timeframe);
        for(int i  = 0; i < allStats.length(); i++) {
            JSONObject entry = allStats.getJSONObject(i);
            double avg = entry.getDouble("avg");
            double med = entry.getDouble("med");
            double stdv = entry.getDouble("stdv");
            double min = entry.getDouble("min");

            //TODO: obsIds, obsNames always as long as allStats?
            ObservationStats obsStat = new ObservationStats(obsIds.get(i), obsNames.get(i), avg, med, stdv, min);
            observationStats.add(obsStat);
        }
        sensor.setStats(observationStats);
    }



}
