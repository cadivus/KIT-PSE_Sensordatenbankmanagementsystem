package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides abstraction to direct database queries
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Optional<Sensor> findById(Long id);



}
