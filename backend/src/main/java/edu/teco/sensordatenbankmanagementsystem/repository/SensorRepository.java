package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides abstraction to direct database queries towards {@link Thing} data
 */
@Repository
public interface SensorRepository extends JpaRepository<Thing, String> {

    Optional<Thing> findById(String id);



}
