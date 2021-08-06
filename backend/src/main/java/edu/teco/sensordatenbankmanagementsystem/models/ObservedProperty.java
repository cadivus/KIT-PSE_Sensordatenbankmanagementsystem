package edu.teco.sensordatenbankmanagementsystem.models;

import javax.persistence.Table;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Table(name = "\"OBS_PROPERTIES\"")
public class ObservedProperty {

    @Column(name = "\"ID\"")
    @Id
    String id;

    @Column(name = "\"NAME\"")
    String name;

    @Column(name = "\"DEFINITION\"")
    String definition;

    @Column(name = "\"DESCRIPTION\"")
    String description;

    @Column(name = "\"PROPERTIES\"")
    String properties;

}
