package edu.teco.sensordatenbankmanagementsystem.models;

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

    String test;
    String name;
}
