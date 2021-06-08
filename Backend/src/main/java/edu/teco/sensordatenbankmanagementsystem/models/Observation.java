package edu.teco.sensordatenbankmanagementsystem.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Observation {

    @Id
    @GeneratedValue
    long id;

    @Override
    public String toString() {
        return String.format("");
    }
}
