package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


import javax.persistence.*;

@Entity
@Data
@Table(name = "\"THINGS\"")
public class Thing {

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

