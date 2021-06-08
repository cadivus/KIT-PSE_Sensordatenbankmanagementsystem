package edu.teco.sensordatenbankmanagementsystem.models;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Sensor {

    @Id
    @GeneratedValue
    int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Observation> observations;

}
