package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link ThingRepository} queries {@link Thing} information from the database
 */
@Repository
public interface ThingRepository extends JpaRepository<Thing, String> {

  Optional<Thing> findAllByDatastreams_Id(String id);
  Optional<Thing> findById(String id);

  List<Thing> getAllBy();
}
