package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagement.repository.DummyRepository;
import edu.teco.sensordatenbankmanagementsystem.controllers.SensorController;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class SensorService {
    Sensor sensor;
    SensorController sensorController;
    DummyRepository repository;

    @Autowired
    public SensorService(SensorController sensorController, DummyRepository repository){
        this.sensorController = sensorController;
        this.repository = repository;
    }

    public void createNewMetaData() {
        sensor = new Sensor();
    }


}
