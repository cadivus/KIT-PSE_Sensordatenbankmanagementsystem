package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.exceptions.CantInterpolateWithNoSamplesException;
import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.NewtonInterpolator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@DataJpaTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
class GraphServiceImpTest {

    @Autowired
    GraphServiceImp graphServiceImp;

    @MockBean
    private ObservationService observationService;
    @MockBean
    private ObservationRepository observationRepository;
    @MockBean
    private DatastreamRepository datastreamRepository;

    @Test
    void getGraphImageOfThingNoInterpolationPoints() {
        assertThrows(CantInterpolateWithNoSamplesException.class, () -> {
            graphServiceImp.getGraphImageOfThing(
                    "a",
                    "b",
//                    "saqn:t:00afbb4",
//                    "obsId=saqn:op:hur",
                    null, null,
                    0,
                    new Dimension(69, 69),
                    3,
                    LagrangeInterpolator.getInstance()
            );
        });
    }

    @Test
    void getGraphImageOfInvalidThing() {
        assertThrows(CantInterpolateWithNoSamplesException.class, () -> {
            graphServiceImp.getGraphImageOfThing(
                    "xxxxxxx",
                    "ddddddd",
                    null, null,
                    10,
                    new Dimension(69, 69),
                    7,
                    LagrangeInterpolator.getInstance()
            );
        });
    }

    @Test
    void getGraphImageOfThing() {
        Dimension dim = new Dimension(69, 69);
        RenderedImage graphImage = graphServiceImp.getGraphImageOfThing(
                "a",
                "b",
//                    "saqn:t:00afbb4",
//                    "obsId=saqn:op:hur",
                null, null,
                5,
                dim,
                3,
                LagrangeInterpolator.getInstance()
        );

        assertEquals(dim.getWidth(), graphImage.getWidth());
        assertEquals(dim.getHeight(), graphImage.getHeight());
    }

    @Configuration
    @Import(GraphServiceImp.class)
    static class TestConfig {
        @Autowired
        EntityManager entityManager;

        int intervalDayWidth = 50;
        int obsN = 5;
        Datastream mr = new Datastream();
        List<Observation> obs = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        {
            mr.setId("c");
            mr.setObsId("b");
            mr.setPhenomenonStart(now.minusDays(intervalDayWidth));
            mr.setPhenomenonEnd(now);

            for(int i=0; i<obsN; i++){
                Observation toAdd = new Observation();
                toAdd.setResultTime(now.minusDays(intervalDayWidth).plusDays((long) i * intervalDayWidth / obsN));
                toAdd.setResultNumber((double)i*i);
                obs.add(toAdd);
            }
        }

        @Bean
        ObservationService observationService() {
            ObservationService observationService = mock(ObservationService.class);
            return observationService;
        }

        @Bean
        ObservationRepository observationRepository() {
            ObservationRepository observationRepository = mock(ObservationRepository.class);
            Mockito.when(observationRepository.findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(
                    eq(mr.getId()),
                    any(), any(),
                    any()
            )).thenReturn(obs.stream());
            return observationRepository;
        }

        @Bean
        DatastreamRepository datastreamRepository() {
            DatastreamRepository datastreamRepository = mock(DatastreamRepository.class);
            Mockito.when(datastreamRepository.findDatastreamsByThing_IdAndObsIdIn("a", List.of(mr.getObsId()))).thenReturn(List.of(mr));
            return datastreamRepository;
        }

    }
}