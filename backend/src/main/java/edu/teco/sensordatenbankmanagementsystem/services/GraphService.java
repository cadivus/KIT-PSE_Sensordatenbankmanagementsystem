package edu.teco.sensordatenbankmanagementsystem.services;

import edu.teco.sensordatenbankmanagementsystem.util.interpolation.Interpolator;

import java.awt.*;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;

public interface GraphService {

    RenderedImage getGraphImageOfThing(String thingId, String obsId, LocalDateTime frameStart,
                                          LocalDateTime frameEnd,
                                       int maxInterPoints, Dimension imageDimension, int granularity,
                                       Interpolator<Double, Double> interpolator);

}
