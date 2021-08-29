package notificationsystem.model;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorDAOTests {

    @Autowired
    SensorDAO sensorDAO;

    /*@Test
    void testGetSensor() {
        //Was ist diese methode überhaupt??
    }*/

    @Test
    void testGetSensorById() {
        String sensorId = "saqn:t:7bd2cd3";

        Sensor sensor = sensorDAO.get(sensorId);

        assertEquals(sensorId, sensor.getId());
        assertEquals("Crowdsensing Node (SDS011, 179552)", sensor.getName());
        assertEquals("Low Cost Node Measuring Particulate Matter", sensor.getDescription());
        assertEquals("geo:49.012615,8.415800,nan", sensor.getLocation());
    }

    @Test
    void testGetAll() {
        //TODO: How to test?

        List<Sensor> sensors = sensorDAO.getAll();

        assertNotNull(sensors);
        assertFalse(sensors.isEmpty());
    }

    @Test
    void testSetStats() throws JSONException {
        //TODO: How to test?
        String sensorId = "saqn:t:7bd2cd3";
        Sensor sensor = sensorDAO.get(sensorId);

        sensorDAO.setStats(sensor, LocalDate.now().minusDays(7));

        assertFalse(sensor.getStats().isEmpty());
    }


}
