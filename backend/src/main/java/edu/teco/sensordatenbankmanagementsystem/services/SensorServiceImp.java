package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.SensorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is an implementation of the {@link SensorService} interface catered towards us using the
 * TECO database
 */
@Service
@CommonsLog
public class SensorServiceImp implements SensorService {

  SensorRepository repository;
  DatastreamRepository datastreamRepository;

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
    return repository.getById(id);
  }

  public Datastream getDatastream(String sensor_id){
    return datastreamRepository.findDatastreamBySensorId(sensor_id).get();
  }

  /**
   * {@inheritDoc}
   */
  public Sensor getSensorMetaData(String id) {
    return repository.getById(id);
  }

  public List<Sensor> getAllSensors() {
    return new ArrayList<>();
    //    return repository.findAll();
  }

}
