package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatastreamRepository extends JpaRepository<Datastream, String> {

    List<Datastream> findBySensor_id(String sensor_id);

    List<Datastream> findBySensor_idAndObs_Id(String sensor_id, String obs_id);

}
