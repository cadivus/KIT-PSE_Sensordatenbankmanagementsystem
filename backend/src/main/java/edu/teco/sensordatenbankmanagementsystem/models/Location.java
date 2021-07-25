package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "\"LOCATIONS\"")
@ToString(exclude = "things")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Location {

  @Column(name = "\"ID\"")
  @Id
  String id;

  @JsonIgnore
  @ManyToMany(mappedBy = "locations")
  private List<Thing> things;

  @Column(name = "\"NAME\"")
  String name;
  @Column(name = "\"DESCRIPTION\"")
  String description;
  @Column(name = "\"ENCODING_TYPE\"")
  String encodingType;

  @Column(name = "\"LOCATION\"")
  String location;

  @Column(name = "\"GEOM\"")
  String geom;
  @Column(name = "\"GEN_FOI_ID\"")
  String genFoiId;
  @Column(name = "\"PROPERTIES\"")
  String properties;


}
