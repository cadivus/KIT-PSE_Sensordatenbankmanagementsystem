package edu.teco.sensordatenbankmanagementsystem.util;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class GlobalConstants {

    private GlobalConstants(){}

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final ZoneId ZONE_ID = ZoneId.systemDefault();

}
