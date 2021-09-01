package notificationsystem.model;

import lombok.extern.apachecommons.CommonsLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

/**
 * The SensorDAO class implements the DAO interface to handle database queries regarding sensors (things). For that end it
 * provides get and getAll methods designed to hide the actual database queries, offering a single
 * access point to all sensor related data and information.
 */
@CommonsLog
@Service
public class SensorDAO {
    private final String getThingApi;
    private final String getAllSensorsApi;
    private final String getActiveRateApi;
    private final String getAllObsApi;
    private final String getStatsApi;
    private final RestTemplate restTemplate;
    private final static int FIRST_ACTIVE_RATE = 0;
    private final static int DEFAULT_ACTIVE_RATE = 0;
    private final static String KEY_ID = "id";
    private final static String KEY_NAME = "name";
    private final static String KEY_AVG = "avg";
    private final static String KEY_MED = "med";
    private final static String KEY_STDV = "stdv";
    private final static String KEY_MIN = "min";
    private final static String ERROR_OBSIDS = "Found no observation ids.";
    private final static String ERROR_ALLSTATS = "Found no stats.";

    /**
     * Constructs a new SensorDAO instance.
     * @param backendUrl dynamic url of the systems backend.
     * @param restTemplate resttemplate used to access API-endpoints.
     */
    @Autowired
    public SensorDAO(String backendUrl, RestTemplate restTemplate) {
        this.getThingApi = backendUrl + "/sensor/thing/";
        this.getAllSensorsApi = backendUrl + "/sensor/getAllSensors";
        this.getActiveRateApi = backendUrl + "/sensor/active_rate";
        this.getAllObsApi = backendUrl + "/observation/getAllObs";
        this.getStatsApi = backendUrl + "/stats";
        this.restTemplate = restTemplate;
    }

    /**
     * Gets a sensor specified by its unique ID from the database.
     * @param sensorID ID of the sensor to be fetched.
     * @return The sensor with the given ID.
     */
    public Sensor get(String sensorID) {
        return restTemplate.getForObject(getThingApi + sensorID, Sensor.class);
    }

    /**
     * Gets all sensors.
     * @return List of all sensors.
     */
    public List<Sensor> getAll() {
        Sensor[] sensors = restTemplate.getForObject(getAllSensorsApi, Sensor[].class);
        if (sensors != null) {
            return Arrays.asList(sensors);
        } else {
            return new LinkedList<>();
        }
    }

    /**
     * Sets statistical stats for a sensor after getting them from the systems backend.
     * @param sensor sensor whose stats are to be set.
     * @param timeframe timeframe from which the stats are calculated.
     * @throws JSONException when failing to format the information from the backend.
     */
    public void setStats(Sensor sensor, LocalDate timeframe) throws JSONException {

        //Get activeRate
        Double[] result = restTemplate.getForObject(getActiveRateApi, Double[].class, List.of(sensor.getId()), timeframe);
        if (result != null) {
            sensor.setActiveRate(result[FIRST_ACTIVE_RATE]);
        } else {
            sensor.setActiveRate(DEFAULT_ACTIVE_RATE);
        }

        //Get stats
        LinkedList<String> obsIds = new LinkedList<>();
        LinkedList<String> obsNames = new LinkedList<>();
        JSONArray observationIds = restTemplate.getForObject(getAllObsApi, JSONArray.class);
        if (observationIds != null) {
            for (int i = 0; i < observationIds.length(); i++) {
                JSONObject entry = observationIds.getJSONObject(i);
                String obsId = entry.getString(KEY_ID);
                String name = entry.getString(KEY_NAME);
                obsIds.add(obsId);
                obsNames.add(name);
            }
        } else {
            log.info(ERROR_OBSIDS);
        }

        //Get stats for each observation type
        LinkedList<ObservationStats> observationStats = new LinkedList<>();
        JSONArray allStats = restTemplate.getForObject(getStatsApi, JSONArray.class, List.of(sensor.getId()), obsIds, timeframe);
        if (allStats != null) {
            for(int i  = 0; i < allStats.length(); i++) {
                JSONObject entry = allStats.getJSONObject(i);
                double avg = entry.getDouble(KEY_AVG);
                double med = entry.getDouble(KEY_MED);
                double stdv = entry.getDouble(KEY_STDV);
                double min = entry.getDouble(KEY_MIN);

                ObservationStats obsStat = new ObservationStats(obsIds.get(i), obsNames.get(i), avg, med, stdv, min);
                observationStats.add(obsStat);
            }
        } else {
            log.info(ERROR_ALLSTATS);
        }
        sensor.setStats(observationStats);
    }



}
