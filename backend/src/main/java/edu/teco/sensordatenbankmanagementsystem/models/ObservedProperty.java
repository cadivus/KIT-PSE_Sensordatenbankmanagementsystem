package edu.teco.sensordatenbankmanagementsystem.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ObservedProperty {

    @Column(name = "\"ID\"")
    @Id
    String id;

}
