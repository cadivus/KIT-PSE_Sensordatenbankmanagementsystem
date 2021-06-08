package edu.teco.sensordatenbankmanagementsystem.models;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Sensor {

    @Id
    @GeneratedValue
    long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Observation> observations;

    public List<Observation> getObservations() {
        return observations;
    }
}
