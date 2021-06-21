package edu.teco.sensordatenbankmanagementsystem.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.teco.sensordatenbankmanagementsystem.models.Sensor;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;


public class JsonSerializer {

    private static Sensor getSensorFromJson(File file) {
        Sensor sensor = new Sensor();
        try {
            ObjectMapper om = new ObjectMapper();
            sensor = om.readValue(file, Sensor.class);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return sensor;
    }
    private static JSON convertSensorToJson(Sensor sensor){
        ObjectMapper om = new ObjectMapper();
        return om.convertValue(sensor, JSON.class);
    }
}
