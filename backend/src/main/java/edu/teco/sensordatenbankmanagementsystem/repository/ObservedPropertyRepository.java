package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Data interactions with the {@link edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty}
 */
@Repository
public interface ObservedPropertyRepository extends JpaRepository<ObservedProperty, String> {

}
