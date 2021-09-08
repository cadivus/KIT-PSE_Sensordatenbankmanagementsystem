package edu.teco.sensordatenbankmanagementsystem.controllers;

import edu.teco.sensordatenbankmanagementsystem.exceptions.ImageCantBeGeneratedException;
import edu.teco.sensordatenbankmanagementsystem.exceptions.UnknownInterpolationMethodException;
import edu.teco.sensordatenbankmanagementsystem.services.GraphService;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.ClampedCubicSplineInterpolator;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.NewtonInterpolator;
import java.awt.Dimension;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("graph")
public class GraphController {

  private DateTimeFormatter DATE_FORMAT;
  @Value("${globals.date_format}")
  private void setDATE_FORMAT(String pattern, String b){
    DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
  }

  private final GraphService graphService;

  @Autowired
  public GraphController(
          GraphService graphService) {
    this.graphService = graphService;
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
  @GetMapping(value = "")
  public ResponseEntity<byte[]> getGraphOfThing(
      @RequestParam(name="id")String id,
      @RequestParam(name="obsId")String obsId,
      @RequestParam(name = "frameStart", required = false) String frameStart,
      @RequestParam(name = "frameEnd", required = false) String frameEnd,
      @RequestParam(name = "maxInterpolationPoints", defaultValue = "25") int maxInterPoints,
      @RequestParam(name = "imageSize", defaultValue = "400x225") String imageSize,
      @RequestParam(name = "renderGranularity", defaultValue = "1") int granularity,
      @RequestParam(name = "interpolationMethod", defaultValue = "spline") String interpolationMethod
  ){
    String[]iwh = imageSize.split("x");
    Dimension idim = new Dimension(Integer.parseInt(iwh[0]), Integer.parseInt(iwh[1]));
    RenderedImage graphImage = graphService.getGraphImageOfThing(
        id,
        obsId,
        frameStart == null ? null : LocalDate.parse(frameStart, DATE_FORMAT).atStartOfDay(),
        Optional.ofNullable(frameEnd)
            .map(s->LocalDate.parse(frameEnd, DATE_FORMAT)
                    .atStartOfDay())
            .orElse(null),
        maxInterPoints,
        idim,
        granularity,
        switch (interpolationMethod){
          case "lagrange" -> LagrangeInterpolator.getInstance();
          case "newton" -> NewtonInterpolator.getInstance();
          case "spline" -> ClampedCubicSplineInterpolator.getInstance();
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

}
