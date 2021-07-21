package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.SensorNotFoundException;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import edu.teco.sensordatenbankmanagementsystem.services.SensorService;
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
 * The SensorController is the entry point for http requests for {@link Sensor}s.
 * Methods of this class map to different requests about Sensors
 */
@RestController
@RequestMapping("/sensor")
@CommonsLog
public class SensorController {
    SensorService sensorService;
    /**
     * Instantiates a new Sensor controller.
     *
     * @param sensorService the {@link SensorService} which handles the underlying business logic
     *                           The Autowired Annotation automatically injects a Spring bean
     */
    @Autowired
    public SensorController(SensorService sensorService) {
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
        if (false) throw new SensorNotFoundException();

        return sensorService.getSensor(id);
    }

}
