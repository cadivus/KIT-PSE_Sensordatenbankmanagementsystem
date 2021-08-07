/*package edu.teco.sensordatenbankmanagementsystem.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.teco.sensordatenbankmanagementsystem.models.Thing;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;


public class JsonSerializer {

    private static Thing getSensorFromJson(File file) {
        Thing sensor = new Thing();
        try {
            ObjectMapper om = new ObjectMapper();
            sensor = om.readValue(file, Thing.class);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return sensor;
    }
    private static JSON convertSensorToJson(Thing sensor){
        ObjectMapper om = new ObjectMapper();
        return om.convertValue(sensor, JSON.class);
    }
}
*/