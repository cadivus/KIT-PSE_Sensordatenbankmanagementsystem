package notificationsystem.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorTests {

    @Test
    void testLocationExtraction() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", "value1");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("key2", "value2");
        JSONArray jsonArray = new JSONArray();
        //TODO: Does put overwrite old entries?
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);

        Sensor sensor = new Sensor("test-id", "test-name", "test-desc", "test-prop", jsonArray);

        assertEquals("value1", sensor.getLocation());
    }
}
