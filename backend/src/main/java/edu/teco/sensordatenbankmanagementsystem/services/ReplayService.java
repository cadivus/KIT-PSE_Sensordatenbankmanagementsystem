package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Requests;
import java.util.UUID;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ReplayService {

  /**
   * This will create a replay of one or more Sensors. It will work akin to the {@link #createNewDataStream(Requests)} but with
   * live data opposed to using already existing data
   *
   * @param information This should contain the Sensor Information for the replay
   * @return The UUID under which the Replay is to be reached
   */
  UUID createReplay(Requests information);

  /**
   * This will return a previously created Datastream from its specified UUID.
   * If there is no DataStream under the given UUID, none will be created
   *
   * @param id The UUID of the Datastream
   * @return An {@link org.springframework.web.servlet.mvc.method.annotation.SseEmitter}
   */
  SseEmitter getDataStream(UUID id);

  /**
   * This will delete the Datastream from the database and will make it send its closing message
   *
   * @param id The UUID of the Datastream
   */
  @Deprecated
  void destroyDataStream(UUID id);

  /**
   * This method creates a new SSEEmitter DataStream using the information provided in the parameter
   * The emitter will be put in an asynchronous executor and put into a Bidirectional Hashmap
   * for storage
   * @param information This should contain the specific information about the Datastream that is to be created.
   *                    At least sensor(s), speed and start date need to be in here
   * @return The UUID of the newly created Datastream
   */
  UUID createNewDataStream(Requests information);
}