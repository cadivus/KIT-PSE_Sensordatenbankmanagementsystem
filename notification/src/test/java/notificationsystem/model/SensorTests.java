package notificationsystem.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
//@DataJpaTest
@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class SensorTests {

    @Test
    public void testLocationExtraction() throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", "value1");
        JSONObject jsonObject2 = new JSONObject();
        jsonObject1.put("key2", "value2");
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);

        Sensor sensor = new Sensor("test-id", "test-name", "test-desc", "test-prop", jsonArray);

        assertEquals("value1", sensor.getLocation());
    }

    @Configuration
    @Import(SubscriptionDAO.class)
    static class TestConfig {
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
    }

}
