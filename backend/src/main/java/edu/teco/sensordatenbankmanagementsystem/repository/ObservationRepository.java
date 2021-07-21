package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Provides abstraction to direct database queries towards {@link Observation} data
 */
@Repository
public interface ObservationRepository extends JpaRepository<Observation, String> {

  Optional<Observation> findById(String id);

  Stream<Observation> findObservationsByDatastreamAndPhenomenonStartAfterAndPhenomenonEndBefore(
      String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart,
      @Param("PhenomenonEnd") LocalDateTime phenomenonEnd);

}
