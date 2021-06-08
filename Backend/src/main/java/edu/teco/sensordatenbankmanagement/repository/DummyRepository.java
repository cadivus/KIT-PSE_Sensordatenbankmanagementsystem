package edu.teco.sensordatenbankmanagement.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DummyRepository extends JpaRepository<Sensor, Long> {

    Optional<Sensor> findById(Long id);

}
