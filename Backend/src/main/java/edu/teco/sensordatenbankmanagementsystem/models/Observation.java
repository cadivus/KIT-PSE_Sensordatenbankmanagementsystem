package edu.teco.sensordatenbankmanagementsystem.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This data class represents one recorded observation of a sensor.
 */
@Entity
@Data
public class Observation {

    @Id
    @GeneratedValue
    long id;

    String propertyType;
    double value;

    @Override
    public String toString() {
        return String.format("");
    }
}
