package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.repository.Database;
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
    Database database;

    @Autowired
    public SensorService(SensorController sensorController, Database database){
        this.sensorController = sensorController;
        this.database = database;
    }

    public void createNewMetaData() {
        sensor = new Sensor();
    }


}
