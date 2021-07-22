package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.xml.crypto.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DatastreamRepository extends JpaRepository<Datastream, String> {

  Optional<Datastream> findById(String id);


  Stream<Datastream> findDatastreamsBySensorIdOrderByPhenomenonStartDesc(String Sensor_id);
}
