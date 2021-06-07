package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class SensorController {

    @Autowired
    public SensorController() {

    }

    @GetMapping("/test")
    public List<Sensor> getAllData() {
        return new ArrayList<Sensor>();
    }

    public Sensor getSensor(int id) {
        return new Sensor();
    }

}
