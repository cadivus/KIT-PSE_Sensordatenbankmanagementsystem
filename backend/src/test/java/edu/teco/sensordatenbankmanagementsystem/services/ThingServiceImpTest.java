package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThingServiceImpTest {

  @SpyBean
  ThingServiceImp thingServiceImp;
  @MockBean
  ThingRepository thingRepository;

  @Test
  public void testThingService() {
    assert new ArrayList<>().equals(thingServiceImp.getListOfClosestSensors(0,0, 0));
  }


}
