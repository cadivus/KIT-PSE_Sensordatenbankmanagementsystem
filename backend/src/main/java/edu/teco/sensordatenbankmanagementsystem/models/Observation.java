package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;

/**
 * This data class represents one recorded observation of a sensor.
 */
@Entity
@Data
@Table(name = "\"OBSERVATIONS\"")
public class Observation {

    @JsonIgnore
    @Transient
    String propertyType;
    @JsonIgnore
    @Transient
    public double value;
    @JsonIgnore
    @Transient
    public LocalDate date;

    @ManyToOne
    @JoinColumn(name = "\"DATASTREAM_ID\"")
    private Datastream datastream;

    @Column(name = "\"ID\"")
    @Id
    String id;

    @Column(name = "\"PHENOMENON_TIME_START\"")
    LocalDate phenomenonStart;

    @Column(name = "\"PHENOMENON_TIME_END\"")
    LocalDate phenomenonEnd;

    @Column(name = "\"RESULT_TIME\"")
    LocalDate resultTime;

    @Column(name = "\"RESULT_TYPE\"")
    int type;

    @Column(name = "\"RESULT_NUMBER\"")
    float resultNumber;

    @Column(name = "\"RESULT_STRING\"")
    String resultString;

    @Column(name = "\"RESULT_JSON\"")
    String resultJson;

    @Column(name = "\"RESULT_BOOLEAN\"")
    Boolean resultBool;

    @Column(name = "\"RESULT_QUALITY\"")
    String resultQuality;

    @Column(name = "\"VALID_TIME_START\"")
    LocalDate validTimeStart;

    @Column(name = "\"VALID_TIME_END\"")
    LocalDate validTimeEnd;

    @Column(name = "\"PARAMETERS\"")
    String params;

    @Column(name = "\"FEATURE_ID\"")
    String featureID;

    @Column(name = "\"MULTI_DATASTREAM_ID\"")
    String mDataStreamID;

    public Observation(){

    }

    public Observation(double value, LocalDate date) {
        this.value = value;
        this.date = date;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
