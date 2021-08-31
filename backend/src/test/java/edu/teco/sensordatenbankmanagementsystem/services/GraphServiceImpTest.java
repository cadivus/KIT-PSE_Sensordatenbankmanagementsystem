package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.exceptions.CantInterpolateWithNoSamplesException;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DataJpaTest
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
class GraphServiceImpTest {

  @Autowired
  GraphServiceImp graphServiceImp;

  @MockBean
  private ObservationService observationService;
  @MockBean
  private DatastreamRepository datastreamRepository;

  @Test
  void getGraphImageOfThingNoInterpolationPoints() {
    assertThrows(CantInterpolateWithNoSamplesException.class, () -> {
      graphServiceImp.getGraphImageOfThing(
              "saqn:t:00afbb4",
              "obsId=saqn:op:hur",
              null, null,
              0,
              new Dimension(69, 69),
              3,
              LagrangeInterpolator.getInstance()
      );
    });
  }

  @Configuration
  @Import(GraphServiceImp.class)
  static class TestConfig {
    @Autowired
    EntityManager entityManager;

    @Bean
    ObservationService observationService() {
      return mock(ObservationService.class);
    }
    @Bean
    DatastreamRepository datastreamRepository() {
      return mock(DatastreamRepository.class);
    }

  }
}