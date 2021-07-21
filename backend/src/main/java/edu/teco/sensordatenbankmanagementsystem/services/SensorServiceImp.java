package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.repository.SensorRepository;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is an implementation of the {@link SensorService} interface catered towards us using the TECO database
 */
@Service
@CommonsLog
public class SensorServiceImp implements SensorService {

    SensorRepository repository;

    @Autowired
    public SensorServiceImp( SensorRepository repository){
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     * Here the new Meta Data will include frequency and realiability calculations
     */
    public void createNewMetaData() {

    }

    /**
     * {@inheritDoc}
     */
    public Sensor getSensor(String id){
        return repository.getById(id);
    }

    public List<Sensor> getAllSensors() {
        return repository.findAll();
    }

}
