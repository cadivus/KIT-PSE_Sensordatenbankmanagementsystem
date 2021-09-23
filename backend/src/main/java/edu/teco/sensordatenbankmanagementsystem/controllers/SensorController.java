package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.ImageCantBeGeneratedException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.ObjectNotFoundException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.UnknownInterpolationMethodException;
import edu.teco.sensordatenbankmanagementsystem.models.*;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import edu.teco.sensordatenbankmanagementsystem.services.SensorServiceImp;
import edu.teco.sensordatenbankmanagementsystem.services.ThingService;

import edu.teco.sensordatenbankmanagementsystem.services.ThingServiceImp;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;

import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.NewtonInterpolator;
import javax.transaction.Transactional;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * {@link SensorController} is the entry point for http requests for {@link Sensor}s. Methods
 * of this class map to different requests about {@link Sensor}s
 */
@RestController
@RequestMapping("/sensor")
@CommonsLog
public class SensorController {

    private DateTimeFormatter DATE_FORMAT;
    @Value("${globals.date_format}")
    private void setDATE_FORMAT(String pattern, String b){
        DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
    }

    public final ThingService thingService;
    public final SensorService sensorService;
    /**
     * Instantiates a new Sensor controller.
     *
     * @param thingService
     * @param sensorService the {@link SensorService} which handles the underlying business logic
     */
    @Autowired
    public SensorController(
        ThingServiceImp thingService,
        SensorServiceImp sensorService) {
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

    @GetMapping("getAllThings")
    public List<Thing> getAllThings(){
        return thingService.getAllThings();
    }

    @GetMapping("getThingsObsIds")
    public List<List<String>> getThingsObsIds(
            @RequestParam("ids")List<String> ids
    ){
        return thingService.getThingsObsIds(ids);
    }

    /**
     * Maps a get request for getting a sensors metadata by UUID.
     *
     * @param id UUID of sensor to get
     * @return sensor with given UUID, if present
     */
    @GetMapping("/sensor/{id}")
    public Sensor getSensor(@PathVariable String id) {

        try {
            return sensorService.getSensor(id);
        } catch (EntityNotFoundException | NullPointerException ex) {
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Gets whether things are active based on their most recent data transmission
     * @param ids list of thing_ids to check
     * @param days amount of days to count activity as recent
     * @return list of booleans in the same order
     */
    @GetMapping("active")
    public List<Integer> getWhetherThingsActive(
            @RequestParam(name="ids")List<String> ids,
            @RequestParam(name="days", defaultValue = "10")int days
            ) {
        return thingService.getWhetherThingsActive(ids, days);
    }

    /**
     * Gets active rate of things, calculated as amount of data transmissions / days
     * @param ids list of thing_ids to check
     * @param frameStart start of time frame to calculate active rate
     * @param frameEnd end of time frame to calculate active rate
     * @return list of active rates in the same order
     */
    @GetMapping("active_rate")
    public List<Double> getActiveRateOfThings(
            @RequestParam(name="ids")List<String> ids,
            @RequestParam(name = "frameStart", defaultValue = "2010-01-01") String frameStart,
            @RequestParam(name = "frameEnd", required = false) String frameEnd
    ){
        return thingService.getActiveRateOfThings(
                ids,
                LocalDate.parse(frameStart, DATE_FORMAT).atStartOfDay(),
                Optional.ofNullable(frameEnd)
                        .map(s->LocalDate.parse(frameEnd, DATE_FORMAT))
                        .orElseGet(LocalDate::now)
                        .atStartOfDay()
        );
    }

    /**
     * Calculates statistics about the given things and returns them in order
     *
     * @param ids list of thing_ids to get stats from
     * @param obsIds list of observed properties to get stats from
     * @param frameStart start of time frame
     * @param frameEnd end of time frame
     * @return list of stats in order
     */
    @GetMapping("stats")
    public List<ObservationStats> getObservationStatsOfThings(
            @RequestParam(name="ids")List<String> ids,
            @RequestParam(name="obsIds")List<String> obsIds,
            @RequestParam(name = "frameStart", defaultValue = "0001-01-01") String frameStart,
            @RequestParam(name = "frameEnd", required = false) String frameEnd
    ){
        return thingService.getObservationStatsOfThings(
                ids,
                obsIds,
                LocalDate.parse(frameStart, DATE_FORMAT).atStartOfDay(),
                Optional.ofNullable(frameEnd)
                        .map(s->LocalDate.parse(frameEnd, DATE_FORMAT))
                        .orElseGet(LocalDate::now)
                        .atStartOfDay());
    }


    /**
     * This will get a single Thing, which is a single sensor, from the Database
     *
     * @param id the ID of the sensor. It tends to look like this: 'saqn:s:xxxxxxx' which x being a
     *           digit or a number
     * @return The Sensor model that contains the information
     */
    @GetMapping(value = {"/thing/{id}"})
    public Thing getThing(@PathVariable String id) {
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
    @GetMapping(value = {"/allThings"})
    public List<Thing> getThing(@RequestParam(value = "lon",required = false) String lon,
        @RequestParam(value = "lat",required = false) String lat, @RequestParam(value = "el",required = false) String el) {

        //These are the coordinates of the city center of Augsburg, as this program focuses on Augsburg
        lon = (lon == null) ? "10.8978 " : lon;
        lat = lat == null ? "48.3705" : lat;
        el = el == null ? "400" : el;
        return thingService.getListOfClosestSensors(Double.parseDouble(lon), Double.parseDouble(lat),
            Double.parseDouble(el));
    }

}
