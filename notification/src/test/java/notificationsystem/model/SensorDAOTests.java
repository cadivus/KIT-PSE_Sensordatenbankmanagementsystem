package notificationsystem.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorDAOTests {

    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    SensorDAO sensorDAO;

    private Sensor getTestSensor() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", "value1");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("key2", "value2");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        return new Sensor("test-id", "test-name", "test-desc", "test-prop", jsonArray);
    }

    @Test
    public void testGetSensorById() throws JSONException {
        Mockito.when(restTemplate.getForObject("http://backend:8081/sensor/thing/test-id", Sensor.class))
                .thenReturn(getTestSensor());

        Sensor sensor = sensorDAO.get("test-id");

        assertEquals("test-id", sensor.getId());
        assertEquals("test-name", sensor.getName());
        assertEquals("test-desc", sensor.getDescription());
        assertEquals("value1", sensor.getLocation());
    }

    @Test
    public void testGetAll() throws JSONException {
        Sensor[] sensors = {getTestSensor()};
        Mockito.when(restTemplate.getForObject("http://backend:8081/sensor/getAllSensors", Sensor[].class))
              .thenReturn(sensors);

        List<Sensor> returnedSensors = sensorDAO.getAll();

        assertFalse(returnedSensors.isEmpty());
        assertEquals("test-id", returnedSensors.get(0).getId());
    }

    @Test
    public void testSetStats() throws JSONException {
        Sensor sensor = getTestSensor();
        Double[] activeRate = {2.0};
        LocalDate timeframe = LocalDate.now().minusDays(7);
        JSONArray observationIds = new JSONArray();
        JSONObject entry = new JSONObject();
        entry.put("id", "test-id-value");
        entry.put("name", "test-name-value");
        observationIds.put(entry);
        LinkedList<String> obsIds = new LinkedList<>();
        obsIds.add("test-id-value");
        JSONArray allStats = new JSONArray();
        JSONObject allStatsEntry = new JSONObject();
        allStatsEntry.put("avg", "3");
        allStatsEntry.put("med", "4");
        allStatsEntry.put("stdv", "5");
        allStatsEntry.put("min", "6");
        allStats.put(allStatsEntry);
        Mockito.when(restTemplate.getForObject("http://backend:8081/sensor/active_rate", Double[].class, List.of(sensor.getId()), timeframe))
                        .thenReturn(activeRate);
        Mockito.when(restTemplate.getForObject("http://backend:8081/observation/getAllObs", JSONArray.class))
                        .thenReturn(observationIds);
        Mockito.when(restTemplate.getForObject("http://backend:8081/stats", JSONArray.class, List.of(sensor.getId()), obsIds, timeframe))
                        .thenReturn(allStats);

        sensorDAO.setStats(sensor, timeframe);

        assertFalse(sensor.getStats().isEmpty());
        assertEquals(3, sensor.getStats().getFirst().getAvg(), 0.2);
        assertEquals(4, sensor.getStats().getFirst().getMed(),  0.2);
        assertEquals(5, sensor.getStats().getFirst().getStdv(),  0.2);
        assertEquals(6, sensor.getStats().getFirst().getMin(),  0.2);
    }

    @Configuration
    @Import({SubscriptionDAO.class, SensorDAO.class})
    static class TestConfig {
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
        @Bean
        RestTemplate restTemplate() {
            return mock(RestTemplate.class);
        }
        @Bean
        String backend() {
            return "http://backend:8081";
        }
    }

}
