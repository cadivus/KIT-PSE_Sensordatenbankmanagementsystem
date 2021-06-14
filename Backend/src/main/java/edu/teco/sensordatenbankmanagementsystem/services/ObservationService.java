package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import org.jooq.JSON;

import java.util.UUID;

public interface ObservationService {
    public UUID createNewDataStream(JSON information);

    public Observation getObservation(Long id);
}
