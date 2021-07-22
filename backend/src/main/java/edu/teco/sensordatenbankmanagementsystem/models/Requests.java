package edu.teco.sensordatenbankmanagementsystem.models;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Requests {

    @Id
    @GeneratedValue
    long id;

    String start;
    String end;
    String speed;

    @ElementCollection
    List<String> sensors;
}
