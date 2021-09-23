package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * {@link SensorRepository} queries {@link Sensor} information from the database
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {

    Optional<Sensor> findById(String id);



}
