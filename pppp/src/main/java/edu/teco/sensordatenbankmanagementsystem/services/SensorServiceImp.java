package edu.teco.sensordatenbankmanagementsystem.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import edu.teco.sensordatenbankmanagementsystem.repository.SensorRepository;
import edu.teco.sensordatenbankmanagementsystem.controllers.SensorController;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import lombok.extern.apachecommons.CommonsLog;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public Sensor getSensor(UUID id){
        return repository.getById(1L);
    }

    /**
     * {@inheritDoc}
     */
    public Sensor getSensorMetaData(UUID id){
        return repository.getById(1L);
    }

    public List<Sensor> getAllSensors() {
        return new ArrayList<>();
    //    return repository.findAll();
    }

}
