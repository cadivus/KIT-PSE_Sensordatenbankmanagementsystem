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

    List<Datastream> findDatastreamsByThingId(String thingId);

    List<Datastream> findDatastreamsByThingIdAndObsIdIn(String thingId, List<String> obsIds);

  Optional<Datastream> findById(String id);

    Stream<Datastream> findDatastreamsBySensorIdOrderByPhenomenonStartDesc(String SensorId);
}
