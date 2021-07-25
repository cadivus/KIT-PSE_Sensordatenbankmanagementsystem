package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.ObjectNotFoundException;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.services.ThingService;
import javax.persistence.EntityNotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The SensorController is the entry point for http requests for {@link Sensor}s.
 * Methods of this class map to different requests about Sensors
 */
@RestController
@RequestMapping("/sensor")
@CommonsLog
public class SensorController {
    public final ThingRepository thingRepository;
    public final ThingService thingService;
    public final SensorService sensorService;
    /**
     * Instantiates a new Sensor controller.
     *
     * @param thingRepository
     * @param thingService
     * @param sensorService the {@link SensorService} which handles the underlying business logic
     */
    @Autowired
    public SensorController(
        ThingRepository thingRepository,
        ThingService thingService,
        SensorService sensorService) {
        this.thingRepository = thingRepository;
        this.thingService = thingService;
        this.sensorService = sensorService;
    }
    /**
     * Maps a get request for getting all sensors' metadata.
     *
     * @return list of all sensors present in the Database used
     */
    @GetMapping("/getAllSensors")
    public List<Sensor> getAllSensors() {
        return sensorService.getAllSensors();
    }
    /**
     * Maps a get request for getting a sensors metadata by UUID.
     *
     * @param id UUID of sensor to get
     * @return sensor with given UUID, if present
     */
    @GetMapping("/Sensor/{id}")
    public Sensor getSensor(@PathVariable String id) {

        try {
            return sensorService.getSensor(id);
        } catch (EntityNotFoundException ex) {
            throw new ObjectNotFoundException();
        }
    }

    @GetMapping("/datastream/{sensorid}")
    public Datastream getDatastrean(@PathVariable String sensorid){
        return sensorService.getDatastream(sensorid);
    }
    /**
     * This will get a single Thing, which is a single sensor, from the Database
     *
     * @param id the ID of the sensor. It tends to look like this: 'saqn:s:xxxxxxx' which x being a
     *           digit or a number
     * @return The Sensor model that contains the information
     */
    @GetMapping(value = {"/thing/{id}"})
    public Thing getThings(@PathVariable String id) {
        return thingService.getThing(id);
    }

    /**
     * This will returned the list of sensors sorted by their coordinates. Either by distance from a
     * specified point or just by their distance to 0,0. If used the point has to be specified using
     * both lat and lon. It is not possible to use only one
     *
     * @param lon The longitude of a center point which should be used. It is optional
     * @param lat The latitude of a center point which should be used. It is optional.
     * @return A list of 'Things'
     */
    @GetMapping(value = {"/allThings/{lon}/{lat}/{el}", "/allThings", "/allThings/{lon}/{lat}"})
    public List<Thing> getThings(@PathVariable(required = false) String lon,
        @PathVariable(required = false) String lat, @PathVariable(required = false) String el) {

        //These are the coordinates of the city center of Augsburg, as this program focuses on Augsburg
        lon = (lon == null) ? "10.8978 " : lon;
        lat = lat == null ? "48.3705" : lat;
        el = el == null ? "400" : el;
        return thingService.getListOfClosestSensors(Double.parseDouble(lon), Double.parseDouble(lat),
            Double.parseDouble(el));
    }

}
