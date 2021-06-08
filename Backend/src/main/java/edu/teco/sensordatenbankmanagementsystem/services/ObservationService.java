package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagement.repository.SensorRepository;
import edu.teco.sensordatenbankmanagementsystem.controllers.ObservationController;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@CommonsLog
public class ObservationService {

    ObservationController observationController;
    ObservationRepository repository;

    @Autowired
    public ObservationService(ObservationController observationController, ObservationRepository repository){
        this.observationController = observationController;
        this.repository = repository;
    }


    public void createNewDataStream(int id) {

    }

    public Observation getObservation(Long id) {
        return repository.findById(id).get();
    }
}
