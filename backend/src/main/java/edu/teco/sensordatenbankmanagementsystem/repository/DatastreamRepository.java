package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatastreamRepository extends JpaRepository<Datastream, String> {

    List<Datastream> findDatastreamsBySensorId(String SensorId);

    List<Datastream> findDatastreamsBySensorIdAndObsId(String SensorId, String obs_id);

  Optional<Datastream> findById(String id);

    Stream<Datastream> findDatastreamsByThing_IdInOrderByPhenomenonStartDesc(List<String> SensorId);
}
