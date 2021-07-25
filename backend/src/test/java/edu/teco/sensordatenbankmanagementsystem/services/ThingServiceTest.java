package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import edu.teco.sensordatenbankmanagementsystem.services.ThingService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThingServiceTest {

  @SpyBean
  ThingService thingService;
  @MockBean
  ThingRepository thingRepository;

  @Test
  public void testThingService() {
    assert new ArrayList<>().equals(thingService.getListOfClosestSensors(0,0, 0));
  }


}
