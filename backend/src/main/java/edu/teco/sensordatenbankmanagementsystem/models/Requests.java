package edu.teco.sensordatenbankmanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Requests {
  @Id
  @GeneratedValue
  long id;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime start;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime end;
  int speed;

  @ElementCollection
  List<String> sensors;
}
