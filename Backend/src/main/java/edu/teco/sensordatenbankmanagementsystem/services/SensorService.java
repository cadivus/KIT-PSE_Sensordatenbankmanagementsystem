package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.models.Sensor;

public interface SensorService {
    public void createNewMetaData();

    public Sensor getSensor(long id);

    public Sensor getSensorMetaData(long id);


}
