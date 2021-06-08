package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagement.repository.DummyRepository;
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
    DummyRepository repository;

    @Autowired
    public ObservationService(ObservationController observationController, DummyRepository repository){
        this.observationController = observationController;
        this.repository = repository;
    }


    public void createNewDataStream(int id) {

    }
    public Observation getObservation(Long id) {
        Sensor sensor = repository.getById(id);
        return sensor.getObservations().get(0);
    }
}
