package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;

public interface ObservationService {
    public void createNewDataStream(int id);

    public Observation getObservation(Long id);
}
