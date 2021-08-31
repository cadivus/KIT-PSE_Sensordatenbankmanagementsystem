package edu.teco.sensordatenbankmanagementsystem.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
class DatastreamServiceImpTest {

  @Autowired
  DatastreamServiceImp datastreamServiceImp;
  @MockBean
  ThingServiceImp thingServiceImp;
  @MockBean
  ThingRepository thingRepository;
  @MockBean
  DatastreamRepository datastreamRepository;

  @Test
  void getDatastreamsByThingValid() {
    Mockito.when(thingServiceImp.getThing("saqn:t:grimm-aerosol.com:edm80opc:sn19001"))
        .thenReturn(new Thing());
    assertNull(
        datastreamServiceImp.getDatastreamsByThing("saqn:t:grimm-aerosol.com:edm80opc:sn19001"));
  }


  @Test
  void getDatastream() {
    Thing t = new Thing();
    Datastream ds = new Datastream();
    ds.setId("");
    t.setDatastreams(List.of(ds));
    Mockito.when(thingRepository.findAllByDatastreams_Id("ds")).thenReturn(
        java.util.Optional.of(t));
    assertNull(datastreamServiceImp.getDatastream("ds"));

  }

  @Configuration
  @Import(DatastreamServiceImp.class)
  static
  class testConfig {

    @Bean
    ThingServiceImp thingServiceImp() {
      return mock(ThingServiceImp.class);
    }

    @Bean
    ThingRepository thingRepository() {
      return mock(ThingRepository.class);
    }

    @Bean
    DatastreamRepository datastreamRepository() {
      return mock(DatastreamRepository.class);
    }
  }
}