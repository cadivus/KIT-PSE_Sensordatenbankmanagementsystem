package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.util.Optional;
import javax.xml.crypto.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatastreamRepository extends JpaRepository<Datastream, String> {

  Optional<Datastream> findById(String id);

  Optional<Datastream> findDatastreamBySensorId(String Sensor_id);
}
