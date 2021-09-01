package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.exceptions.CantInterpolateWithNoSamplesException;
import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservedPropertyRepository;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@DataJpaTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
class GraphServiceImpTest {


    static int intervalDayWidth = 50;
    static int obsN = 5;
    static Datastream mr = new Datastream();
    static List<Observation> obs = new ArrayList<>();
    static LocalDateTime now = LocalDateTime.now();

    @BeforeAll
    public static void setup()
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

    @Autowired
    GraphService graphServiceImp;

    @Autowired
    @SpyBean
    private ObservationServiceImp observationService;
    @MockBean
    private ObservationRepository observationRepository;
    @MockBean
    private DatastreamRepository datastreamRepository;
    @MockBean
    private ObservedPropertyRepository observedPropertyRepository;

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
        Mockito.when(datastreamRepository.findDatastreamsByThing_IdAndObsIdIn("a", List.of(mr.getObsId()))).thenReturn(List.of(mr));
        Mockito.when(observationRepository.findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(
                eq(mr.getId()),
                any(), any(),
                any()
        )).thenAnswer(a->obs.stream());
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

        @Bean
        ObservationRepository observationRepository() {
            ObservationRepository observationRepository = mock(ObservationRepository.class);
            return observationRepository;
        }

        @Bean
        DatastreamRepository datastreamRepository() {
            DatastreamRepository datastreamRepository = mock(DatastreamRepository.class);
            return datastreamRepository;
        }

        @Bean
        ObservedPropertyRepository observedPropertyRepository() {
            ObservedPropertyRepository observedPropertyRepository = mock(ObservedPropertyRepository.class);
            return observedPropertyRepository;
        }

    }
}