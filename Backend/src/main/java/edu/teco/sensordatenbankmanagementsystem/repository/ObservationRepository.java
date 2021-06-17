package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides abstraction to direct database queries towards {@code Observation} data
 */
@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    Optional<Observation> findById(Long id);

}
