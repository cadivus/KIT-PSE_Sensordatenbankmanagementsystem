package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "\"THINGS\"")
@ToString(exclude = {"locations", "datastreams"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Thing {

  @Column(name = "\"ID\"")
  @Id
  String id;
  @Column(name = "\"NAME\"")
  String name;
  @Column(name = "\"DESCRIPTION\"")
  String description;
  @Column(name = "\"PROPERTIES\"")
  String properties;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "\"THINGS_LOCATIONS\"",
      joinColumns = @JoinColumn(name = "\"THING_ID\"", referencedColumnName = "\"ID\""),
      inverseJoinColumns = @JoinColumn(name = "\"LOCATION_ID\"", referencedColumnName = "\"ID\""))
  List<Location> locations;

  @JsonIgnore
  @OneToMany(mappedBy = "thing", fetch = FetchType.EAGER)
  List<Datastream> datastreams;

  @Override
  public boolean equals(Object a) {
    boolean result = false;
    if (a == null || !a.getClass().equals(Thing.class))
      result = false;
    else if (((Thing) a).getId() == this.id && ((Thing) a).getName() == this.name && ((Thing) a).getProperties() == this.properties && ((Thing) a).getDescription() == this.description)
      result = true;
    return result;
  }

}
