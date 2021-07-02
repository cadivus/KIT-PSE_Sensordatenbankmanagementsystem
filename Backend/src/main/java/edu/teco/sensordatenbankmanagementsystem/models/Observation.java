package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

/**
 * This data class represents one recorded observation of a sensor.
 */
@Entity
@Data
public class Observation {

    @Id
    @GeneratedValue
    long id;

    @JsonIgnore
    String propertyType;
    @JsonIgnore
    public double value;
    @JsonIgnore
    public LocalDate date;

    public Observation(){

    }
    public Observation(LocalDate date, double value) {
    }

    @Override
    public String toString() {
        return String.format("");
    }
}
