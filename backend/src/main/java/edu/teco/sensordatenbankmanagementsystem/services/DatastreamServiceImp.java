package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatastreamServiceImp implements DatastreamService {

  private final ThingService thingService;
  private final ThingRepository thingRepository;
  private final DatastreamRepository datastreamRepository;

  @Autowired
  public DatastreamServiceImp(
      ThingService thingService,
      ThingRepository thingRepository,
      DatastreamRepository datastreamRepository) {
    this.thingService = thingService;
    this.thingRepository = thingRepository;
    this.datastreamRepository = datastreamRepository;
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

  @Transactional
  public Stream<Datastream> getDatastreams(List<String> thingIds, LocalDateTime start, LocalDateTime end) {

    if (start == null)
      start = LocalDateTime.of(1900,1,1,0,0);
    if (end == null)
      end = LocalDateTime.now();
    LocalDateTime finalStart = start;
    LocalDateTime finalEnd = end;


    return datastreamRepository
        .findDatastreamsByThing_IdInOrderByPhenomenonStartDesc(thingIds).filter(e -> e.getPhenomenonStart() != null && e.getPhenomenonEnd() != null)
        .filter(e -> (e.getPhenomenonStart().isBefore(finalStart) && e.getPhenomenonEnd().isAfter(finalStart))|| (e.getPhenomenonEnd().isAfter(
            finalEnd) && e.getPhenomenonStart().isBefore(finalEnd)) || (e.getPhenomenonStart().isAfter(finalStart) && e.getPhenomenonEnd().isBefore(finalEnd)));



  }
}
