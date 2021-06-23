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

/**
 * The {@code SensorServiceImp} is an implementation of the {@code SensorService} interface catered towards us using the TECO database
 */
@Service
@CommonsLog
public class SensorServiceImp implements SensorService {

    SensorController sensorController;
    SensorRepository repository;

    @Autowired
    public SensorServiceImp(SensorController sensorController, SensorRepository repository){
        this.sensorController = sensorController;
        this.repository = repository;
    }

    public Sensor getSensor(long id){
        return repository.getById(id);
    }

    public Sensor getSensorMetaData(long id){
        return repository.getById(id);
    }


}
