package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDateTime;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "\"DATASTREAMS\"")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
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
    LocalDateTime phenomenonStart;

    @Column(name = "\"PHENOMENON_TIME_END\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    LocalDateTime phenomenonEnd;

    @Column(name = "\"RESULT_TIME_START\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    LocalDateTime resultTimeStart;

    @Column(name = "\"RESULT_TIME_END\"")
    @JsonFormat(pattern="dd/MM/yyyy@hh:mm:ss")
    LocalDateTime resultTimeEnd;

    @Column(name = "\"OBS_PROPERTY_ID\"")
    String obsId;

    @JoinColumn(name = "\"THING_ID\"")
    @ManyToOne
    Thing thing;

    @Column(name = "\"UNIT_NAME\"")
    String unit;



}

