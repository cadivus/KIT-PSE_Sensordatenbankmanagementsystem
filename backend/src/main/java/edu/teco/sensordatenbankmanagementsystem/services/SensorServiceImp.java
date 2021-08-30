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

  @Autowired
  public SensorServiceImp(SensorRepository repository) {
    this.repository = repository;
  }

  /**
   * {@inheritDoc}
   */
  public Sensor getSensor(String id) {
    return repository.findById(id).get();
  }




  public List<Sensor> getAllSensors() {
    return repository.findAll();
  }
}
