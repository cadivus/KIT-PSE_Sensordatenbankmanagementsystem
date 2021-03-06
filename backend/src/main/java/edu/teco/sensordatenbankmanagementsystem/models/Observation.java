package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvIgnore;
import lombok.Data;
import org.glassfish.jersey.internal.guava.Maps;
import org.springframework.data.domain.Sort;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

/**
 * This data class represents one recorded observation of a sensor.
 */
@Entity
@Data
@Table(name = "\"OBSERVATIONS\"")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Observation {


    @CsvIgnore
    @JsonIgnore
    @Transient
    String propertyType;
    @CsvIgnore
    @JsonIgnore
    @Transient
    public double value;
    @CsvIgnore
    @JsonIgnore
    @Transient
    public LocalDate date;

    @Column(name = "\"DATASTREAM_ID\"")
    private String datastreamId;

    @CsvIgnore
    @Column(name = "\"ID\"")
    @Id
    String id;

    @Column(name = "\"PHENOMENON_TIME_START\"")
    @JsonFormat(pattern = "dd/MM/yyyy@hh:mm:ss")
    LocalDateTime phenomenonStart;

    @Column(name = "\"PHENOMENON_TIME_END\"")
    @JsonFormat(pattern = "dd/MM/yyyy@hh:mm:ss")
    LocalDateTime phenomenonEnd;

    @Column(name = "\"RESULT_TIME\"")
    @JsonFormat(pattern = "dd/MM/yyyy@hh:mm:ss")
    LocalDateTime resultTime;

    @Column(name = "\"RESULT_TYPE\"")
    Integer type;

    @Column(name = "\"RESULT_NUMBER\"")
    Double resultNumber;

    @Column(name = "\"RESULT_STRING\"")
    String resultString;

    @CsvIgnore
    @Column(name = "\"RESULT_JSON\"")
    String resultJson;

    @CsvIgnore
    @Column(name = "\"RESULT_BOOLEAN\"")
    Boolean resultBool;

    @CsvIgnore
    @Column(name = "\"RESULT_QUALITY\"")
    String resultQuality;

    @CsvIgnore
    @Column(name = "\"VALID_TIME_START\"")
    LocalDateTime validTimeStart;

    @CsvIgnore
    @Column(name = "\"VALID_TIME_END\"")
    LocalDateTime validTimeEnd;

    @CsvIgnore
    @Column(name = "\"PARAMETERS\"")
    String params;

    @Column(name = "\"FEATURE_ID\"")
    String featureID;

    @CsvIgnore
    @Column(name = "\"MULTI_DATASTREAM_ID\"")
    String mDataStreamID;

    public Observation() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Observation)) return false;
        return id != null && id.equals(((Observation) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum Order {
        DATE {
            @Override
            public Sort toSort() {
                return Sort.by("phenomenonStart");
            }
        },
        VALUE {
            @Override
            public Sort toSort() {
                return Sort.by("resultNumber");
            }
        },
        RESULT {
            @Override
            public Sort toSort() {
                return Sort.by("resultTime");
            }
        };

        abstract public Sort toSort();

        private static final Map<String, Order> index = Maps.newHashMapWithExpectedSize(values().length);

        static {
            for (Order order : values()) {
                index.put(order.name(), order);
            }
        }

        public static Sort getSort(String name) {
            return Optional.ofNullable(index.get(name)).map(Order::toSort).orElse(null);
        }
    }

}
