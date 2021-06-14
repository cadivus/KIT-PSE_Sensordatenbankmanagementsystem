package edu.teco.sensordatenbankmanagementsystem.services;

public interface ObservationService {
    public void createNewDataStream(int id);

    public Observation getObservation(Long id);
}
