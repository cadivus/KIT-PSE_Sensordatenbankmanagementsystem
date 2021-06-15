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

@RestController
@CommonsLog
public class SensorController {
    SensorServiceImp sensorService;


    @Autowired
    public SensorController(SensorServiceImp sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("")
    public List<Sensor> getAllSensors() {
        return new ArrayList<Sensor>();
    }

    @GetMapping("/{id}")
    public Sensor getSensor(@PathVariable int id) {
        if (false) throw new SensorNotFoundException();

        return new Sensor();
    }

}
