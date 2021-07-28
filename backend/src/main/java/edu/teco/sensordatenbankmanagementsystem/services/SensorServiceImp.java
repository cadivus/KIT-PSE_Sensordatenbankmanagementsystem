package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.repository.SensorRepository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * This is an implementation of the {@link SensorService} interface catered towards us using the
 * TECO database
 */
@Service
@CommonsLog
@Transactional
public class SensorServiceImp implements SensorService {

  SensorRepository repository;
  DatastreamRepository datastreamRepository;
  private final EntityManager entityManager;
  private final ObservationRepository observationRepository;

  @Autowired
  public SensorServiceImp(SensorRepository repository, DatastreamRepository datastreamRepository,
      EntityManager entityManager,
      ObservationRepository observationRepository) {
    this.repository = repository;
    this.datastreamRepository = datastreamRepository;
    this.entityManager = entityManager;
    this.observationRepository = observationRepository;
  }

  /**
   * {@inheritDoc} Here the new Meta Data will include frequency and realiability calculations
   */
  public void createNewMetaData() {

  }

  /**
   * {@inheritDoc}
   */
  public Sensor getSensor(String id) {
    return repository.getById(id);
  }

  @Transactional
  public Stream<Datastream> getDatastreams(List<String> sensorIds, LocalDateTime start, LocalDateTime end) {

    if (start == null)
      start = LocalDateTime.of(1900,1,1,0,0);
    if (end == null)
      end = LocalDateTime.now();
    LocalDateTime finalStart = start;
    LocalDateTime finalEnd = end;


      return datastreamRepository
          .findDatastreamsBySensorIdInOrderByPhenomenonStartDesc(sensorIds).filter(e -> e.getPhenomenonStart() != null && e.getPhenomenonEnd() != null)
          .filter(e -> (e.getPhenomenonStart().isBefore(finalStart) && e.getPhenomenonEnd().isAfter(finalStart))|| (e.getPhenomenonEnd().isAfter(
              finalEnd) && e.getPhenomenonStart().isBefore(finalEnd)) || (e.getPhenomenonStart().isAfter(finalStart) && e.getPhenomenonEnd().isBefore(finalEnd)));



  }

  /**
   * {@inheritDoc}
   */
  public Sensor getSensorMetaData(String id) {
    return repository.getById(id);
  }

  //TODO
  public List<Datastream> getDatastreams(String senor_id) {
    return null;
  }

  public List<Sensor> getAllSensors() {
    return repository.findAll(PageRequest.of(0,5)).toList();
  }

}
