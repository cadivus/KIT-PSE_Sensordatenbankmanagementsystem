package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.exceptions.CantInterpolateWithNoSamplesException;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.repository.SensorRepository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import javax.transaction.Transactional;

import edu.teco.sensordatenbankmanagementsystem.util.interpolation.Interpolator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static edu.teco.sensordatenbankmanagementsystem.util.Meth.round;

/**
 * This is an implementation of the {@link SensorService} interface catered towards us using the
 * TECO database
 */
@Service
@CommonsLog
@Transactional
public class SensorServiceImp implements SensorService {

  private ZoneId ZONE_ID ;
  @Value("${globals.zone_id}")
  private void setDATE_FORMAT(String zoneId){
    ZONE_ID = switch (zoneId){
      case"default" -> ZoneId.systemDefault();
      default -> ZoneId.of(zoneId);
    };
  }

  private final SensorRepository repository;
  private final DatastreamRepository datastreamRepository;

  @Autowired
  public SensorServiceImp(SensorRepository repository, DatastreamRepository datastreamRepository) {
    this.repository = repository;
    this.datastreamRepository = datastreamRepository;
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
    return repository.findById(id).get();
  }

  @Transactional
  public Stream<Datastream> getDatastreams(List<String> thingIds, LocalDateTime start, LocalDateTime end) {

    if (start == null)
      start = LocalDateTime.of(1900,1,1,0,0);
    if (end == null)
      end = LocalDateTime.now();
    LocalDateTime finalStart = start;
    LocalDateTime finalEnd = end;


      return datastreamRepository
          .findDatastreamsByThing_IdInOrderByPhenomenonStartDesc(thingIds).filter(e -> e.getPhenomenonStart() != null && e.getPhenomenonEnd() != null)
          .filter(e -> (e.getPhenomenonStart().isBefore(finalStart) && e.getPhenomenonEnd().isAfter(finalStart))|| (e.getPhenomenonEnd().isAfter(
              finalEnd) && e.getPhenomenonStart().isBefore(finalEnd)) || (e.getPhenomenonStart().isAfter(finalStart) && e.getPhenomenonEnd().isBefore(finalEnd)));



  }

  /**
   * {@inheritDoc}
   */
  public Sensor getSensorMetaData(String id) {
    return repository.getById(id);
  }

  @Override
  public Datastream getDatastream(String sensor_id) {
    return datastreamRepository.findById(sensor_id).get();
  }


  public List<Sensor> getAllSensors() {
    return repository.findAll();
  }
}
