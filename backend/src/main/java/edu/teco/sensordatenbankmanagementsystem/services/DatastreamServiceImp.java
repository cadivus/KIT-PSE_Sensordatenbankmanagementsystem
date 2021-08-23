package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatastreamServiceImp implements DatastreamService {

  private final ThingService thingService;
  private final ThingRepository thingRepository;

  @Autowired
  public DatastreamServiceImp(
      ThingService thingService,
      ThingRepository thingRepository) {
    this.thingService = thingService;
    this.thingRepository = thingRepository;
  }


  /**
   * {@inheritDoc}
   */
  public List<Datastream> getDatastreamsByThing(String id) {
    return thingService.getThing(id).getDatastreams();
  }

  /**
   * {@inheritDoc}
   */
  public Datastream getDatastream(String id) {

    // return datastreamRepository.getById(id);

    Optional<Thing> thing = thingRepository.findAllByDatastreams_Id(id);

    if (thing.isPresent()) {
      for (Datastream ds :
          thingRepository.findAllByDatastreams_Id(id).get().getDatastreams()) {
        if (ds.getId().equals(id)) {
          return ds;
        }

      }
    }
    return null;
  }

}
