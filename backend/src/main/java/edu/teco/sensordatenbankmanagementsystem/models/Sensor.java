package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.jooq.JSONB;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

/**
 * This data class represents information about a sensor: its associated metadata, and observation data.
 */
@Entity
@Data
@Table(name = "\"SENSORS\"")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Sensor {


    @Id
    @Column(name = "\"ID\"")
    String id;

    @Column(name = "\"NAME\"")
    String name;

    @Column(name = "\"DESCRIPTION\"")
    String description;

    @Column(name = "\"ENCODING_TYPE\"")
    String encoding_type;

    @Column(name = "\"METADATA\"")
    String metadata;

    @Column(name = "\"PROPERTIES\"")
    String properties;

    @Transient
    @OneToMany(cascade = CascadeType.ALL)
    private List<Observation> observations;

    public List<Observation> getObservations() {
        return observations;
    }


}
