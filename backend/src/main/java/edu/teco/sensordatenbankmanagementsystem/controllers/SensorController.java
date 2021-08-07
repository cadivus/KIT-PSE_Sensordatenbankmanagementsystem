package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.ImageCantBeGeneratedException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.ObjectNotFoundException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.UnknownInterpolationMethodException;
import edu.teco.sensordatenbankmanagementsystem.models.ObservationStats;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import edu.teco.sensordatenbankmanagementsystem.repository.ThingRepository;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.services.ThingService;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;

import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.NewtonInterpolator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
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
 * The SensorController is the entry point for http requests for {@link Thing}s.
 * Methods of this class map to different requests about Sensors
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
     * Instantiates a new Thing controller.
     *
     * @param thingService
     * @param sensorService the {@link SensorService} which handles the underlying business logic
     */
    @Autowired
    public SensorController(
        ThingService thingService,
        SensorService sensorService) {
        this.thingService = thingService;
        this.sensorService = sensorService;
    }
    /**
     * Maps a get request for getting all sensors' metadata.
     *
     * @return list of all sensors present in the Database used
     */
    @GetMapping("/getAllSensors")
    public List<Thing> getAllSensors() {
        return sensorService.getAllSensors();
    }

    @GetMapping("getAllThings")
    public List<Thing> getAllThings(){
        return thingService.getAllThings();
    }

    /**
     * Generates and returns an image of a graph interpolating the data in the specified time frame
     *
     * @param id of thing
     * @param obsId of observed property
     * @param frameStart start of time frame
     * @param frameEnd end of time frame
     * @param maxInterPoints maximum number of interpolation points to use (don't go too crazy)
     * @param imageSize size of the image to return
     * @param granularity visual granularity of the rendered graph
     * @return image of graph
     */
    @GetMapping(value = "graph")
    public ResponseEntity<byte[]> getGraphOfThing(
            @RequestParam(name="id")String id,
            @RequestParam(name="obsId")String obsId,
            @RequestParam(name = "frameStart", defaultValue = "0001-01-01") String frameStart,
            @RequestParam(name = "frameEnd", required = false) String frameEnd,
            @RequestParam(name = "maxInterpolationPoints", defaultValue = "100") int maxInterPoints,
            @RequestParam(name = "imageSize", defaultValue = "400x225") String imageSize,
            @RequestParam(name = "renderGranularity", defaultValue = "1") int granularity,
            @RequestParam(name = "interpolationMethod", defaultValue = "lagrange") String interpolationMethod
    ){
        String[]iwh = imageSize.split("x");
        Dimension idim = new Dimension(Integer.parseInt(iwh[0]), Integer.parseInt(iwh[1]));
        RenderedImage graphImage = sensorService.getGraphImageOfThing(
                id,
                obsId,
                LocalDate.parse(frameStart, DATE_FORMAT).atStartOfDay(),
                Optional.ofNullable(frameEnd)
                        .map(s->LocalDate.parse(frameEnd, DATE_FORMAT))
                        .orElseGet(LocalDate::now)
                        .atStartOfDay(),
                maxInterPoints,
                idim,
                granularity,
                switch (interpolationMethod){
                    case "lagrange" -> LagrangeInterpolator.getInstance();
                    case "newton" -> NewtonInterpolator.getInstance();
                    default -> throw new UnknownInterpolationMethodException(interpolationMethod);
                }
        );
        ByteArrayOutputStream graphStream = new ByteArrayOutputStream();
        try{
            //there appears to be (at least locally) a problem where ImageIO only finds a writer for "png"-format
            //otherwise "jpg" would be preferred here
            ImageIO.write(graphImage, "png", graphStream);
        } catch(IOException io) {
            throw new ImageCantBeGeneratedException();
        }
        byte[] graphImageAsArray = graphStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(graphImageAsArray, headers, HttpStatus.CREATED);
    }

    /**
     * Maps a get request for getting a sensors metadata by UUID.
     *
     * @param id UUID of sensor to get
     * @return sensor with given UUID, if present
     */
    @GetMapping("/sensor/{id}")
    public Thing getSensor(@PathVariable String id) {

        try {
            return sensorService.getSensor(id);
        } catch (EntityNotFoundException ex) {
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
            @RequestParam(name = "frameStart", defaultValue = "0001-01-01") String frameStart,
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

    @GetMapping("/datastream/{sensorId}")
    public Datastream getDatastrean(@PathVariable String sensorId){
        return sensorService.getDatastream(sensorId);
    }
    /**
     * This will get a single Thing, which is a single sensor, from the Database
     *
     * @param id the ID of the sensor. It tends to look like this: 'saqn:s:xxxxxxx' which x being a
     *           digit or a number
     * @return The Thing model that contains the information
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
