package edu.teco.sensordatenbankmanagementsystem.repository;

import edu.teco.sensordatenbankmanagementsystem.models.ObservedProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link ObservedPropertyRepository} queries {@link ObservedProperty} information from the database
 */
@Repository
public interface ObservedPropertyRepository extends JpaRepository<ObservedProperty, String> {

}
