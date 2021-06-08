package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
@Data
public class Observation {

    @Id
    @GeneratedValue
    long id;

    private Map<String, String> properties;

    @JsonAnyGetter
    public Map<String, String> getProperties(){
        return properties;
    }

    @Override
    public String toString() {
        return String.format("");
    }
}
