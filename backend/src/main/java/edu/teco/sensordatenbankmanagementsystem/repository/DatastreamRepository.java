package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * {@link DatastreamRepository} queries {@link Datastream} information from the database
 */
@Repository
public interface DatastreamRepository extends JpaRepository<Datastream, String> {

    List<Datastream> findDatastreamsByThing_Id(String thingId);

    List<Datastream> findDatastreamsByThing_IdAndObsIdIn(String thingId, List<String> obsIds);

    @NotNull
    Optional<Datastream> findById(String id);

    Stream<Datastream> findDatastreamsByThing_IdInOrderByPhenomenonStartDesc(List<String> SensorId);
}
