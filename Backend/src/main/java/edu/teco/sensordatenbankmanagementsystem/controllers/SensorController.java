package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.SensorNotFoundException;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.services.SensorServiceImp;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The {@code SensorController} is the entry point for http requests for {@code Sensor}s.
 * Methods of this class map to different requests about {@code Sensor}s.
 */
@RestController
@CommonsLog
public class SensorController {


    SensorServiceImp sensorService;

    /**
     * Instantiates a new Sensor controller.
     *
     * @param sensorService the {@code SensorService} which handles the underlying business logic
     *                           The Autowired Annotation automatically injects a Spring bean
     */
    @Autowired
    public SensorController(SensorServiceImp sensorService) {
        this.sensorService = sensorService;
    }

    /**
     * Maps a get request for getting all sensors' metadata.
     *
     * @return list of all sensors
     */
    @GetMapping("")
    public List<Sensor> getAllSensors() {
        return new ArrayList<Sensor>();
    }

    /**
     * Maps a get request for getting a sensor's metadata by UUID.
     *
     * @param id UUID of sensor to get
     * @return sensor with given UUID, if present
     */
    @GetMapping("/{id}")
    public Sensor getSensor(@PathVariable UUID id) {
        if (false) throw new SensorNotFoundException();

        return new Sensor();
    }

}