package edu.teco.sensordatenbankmanagementsystem.services;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//TODO This might possibly not be needed
@Service
public class ThingService {
    private final ThingRepository thingRepository;

    @Autowired
  public ThingService(
      ThingRepository thingRepository) {
    this.thingRepository = thingRepository;
  }

  @Transactional
  public Thing getThing(String id){
      return thingRepository.findById(id).get();
  }
}
