package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.teco.sensordatenbankmanagementsystem.repository.ObservationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "`DATASTREAMS`")
public class Datastream {

    @Column(name = "\"ID\"")
    @Id
    String id;
    @Column(name = "\"NAME\"")
    String name;
    
    @Column(name = "\"DESCRIPTION\"")
    String description;
    
    @Column(name = "\"SENSOR_ID\"")
    String sensorId;

    @Column(name = "\"PHENOMENON_TIME_START\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    Date phenomenonStart;

    @Column(name = "\"PHENOMENON_TIME_END\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    Date phenomenonEnd;

    @Column(name = "\"RESULT_TIME_START\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    Date resultTimeStart;

    @Column(name = "\"RESULT_TIME_END\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    Date resultTimeEnd;

    @Column(name = "\"OBS_PROPERTY_ID\"")
    String obs_Id;

    @Column(name = "\"THING_ID\"")
    String thing_id;

    @Column(name = "\"UNIT_NAME\"")
    String unit;

    @OneToMany(mappedBy = "datastream",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Observation> observations;
    public void addComment(Observation observation) {
        observations.add(observation);
        observation.setDatastream(this);
    }

    public void removeComment(Observation observation) {
        observations.remove(observation);
        observation.setDatastream(null);
    }
}

