package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.repository.Database;
import edu.teco.sensordatenbankmanagementsystem.controllers.ObservationController;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class ObservationService {

    Observation observation;
    ObservationController observationController;
    Database database;

    @Autowired
    public ObservationService(ObservationController observationController, Database Database){
        this.observationController = observationController;
        this.database = Database;
    }


    public void createNewDataStream(int id) {

    }
    public Observation getObservation(Long id) {
        Sensor sensor = database.getById(id);
        return sensor.getObservations().get(0);
    }
}
