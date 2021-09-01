package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Provides abstraction to direct database queries towards {@link Observation} data
 */
@Repository
public interface ObservationRepository extends JpaRepository<Observation, String> {

  Optional<Observation> findById(String id);

  Stream<Observation> findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartAsc(
          String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart,
          @Param("PhenomenonEnd") LocalDateTime phenomenonEnd);
  Stream<Observation> findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartAsc(
          String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart,
          @Param("PhenomenonEnd") LocalDateTime phenomenonEnd, Pageable pageable);
  Stream<Observation> findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(
      String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart,
      @Param("PhenomenonEnd") LocalDateTime phenomenonEnd, Pageable pageable);
  Stream<Observation> findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBefore(
      String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart,
      @Param("PhenomenonEnd") LocalDateTime phenomenonEnd, Pageable pageable);
  Stream<Observation> findObservationsByDatastreamIdAndPhenomenonStartAfterAndPhenomenonEndBeforeOrderByPhenomenonStartDesc(
      String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart,
      @Param("PhenomenonEnd") LocalDateTime phenomenonEnd);

  Stream<Observation> findObservationsByDatastreamIdAndPhenomenonStartAfter(
      String dataStream, @Param("PhenomenonStart") LocalDateTime phenomenonStart);

  Stream<Observation> findObservationsByDatastreamId(String dataStream, Pageable pageable);

  Stream<Observation> findObservationsByDatastreamId(String dataStream);


  @Query(value="select * from \"OBSERVATIONS\" where \"DATASTREAM_ID\" in :datastreams order by :sort", nativeQuery = true)
  Stream<Observation> findObservationsInDatastreams(List<Datastream> datastreams, String sort, Pageable pageable);

  long countAllByDatastreamId(String id);

}
