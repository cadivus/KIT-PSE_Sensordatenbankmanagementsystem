package edu.teco.sensordatenbankmanagementsystem.services;

import static edu.teco.sensordatenbankmanagementsystem.util.Meth.round;

import edu.teco.sensordatenbankmanagementsystem.exceptions.CantInterpolateWithNoSamplesException;
import edu.teco.sensordatenbankmanagementsystem.models.Datastream;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.repository.DatastreamRepository;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.Interpolator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GraphServiceImp implements GraphService {

  private final ObservationService observationService;
  private final DatastreamRepository datastreamRepository;

  @Autowired
  GraphServiceImp(ObservationService observationService, DatastreamRepository datastreamRepository) {

    this.observationService = observationService;
    this.datastreamRepository = datastreamRepository;
  }

  private ZoneId ZONE_ID ;
  @Value("${globals.zone_id}")
  private void setDATE_FORMAT(String zoneId){
    ZONE_ID = switch (zoneId){
      case"default" -> ZoneId.systemDefault();
      default -> ZoneId.of(zoneId);
    };
  }

  @Override
  public RenderedImage getGraphImageOfThing(String thingId, String obsId, LocalDateTime frameStart, LocalDateTime frameEnd,
      int maxInterPoints, Dimension imageDimension, int granularity,
      Interpolator<Double, Double> interpolator) {

    if(maxInterPoints == 0) {
      throw new CantInterpolateWithNoSamplesException();
    }

    //the spring framework sort for the x-axis of the interpolation
    final Sort xSort = Observation.Order.DATE.toSort();

    if(frameStart == null || frameEnd == null) {
      Datastream datastream =
              datastreamRepository.findDatastreamsByThing_IdAndObsIdIn(thingId, List.of(obsId)).
                      stream().findFirst().orElseThrow(CantInterpolateWithNoSamplesException::new);
      //gets the earliest value as frame start if not specified
      if (frameStart == null) {
        frameStart = datastream.getPhenomenonStart();
      }
      //gets the latest value as frame end if not specified
      if (frameEnd == null) {
        frameEnd = datastream.getPhenomenonEnd();
      }
    }
    LocalDateTime finalFrameStart = frameStart;

    BufferedImage r = new BufferedImage(imageDimension.width, imageDimension.height, BufferedImage.TYPE_INT_ARGB);

    //calculates various stats required for drawing and normalizing the x axis
    double unnormalizedIntervalStart = frameStart.atZone(ZONE_ID).toEpochSecond();
    double unnormalizedIntervalEnd = frameEnd.atZone(ZONE_ID).toEpochSecond();

    //attempt at normalizing massive x values generating by epoch seconds for smaller interpolation error
    double normalizingShift = (unnormalizedIntervalEnd + unnormalizedIntervalStart) / 2;
    double normalizingScale = (unnormalizedIntervalEnd - unnormalizedIntervalStart) / 2;
//    double normalizingShift = 0;
//    double normalizingScale = 1;
    Function<Double, Double> normalizer = a -> (a - normalizingShift) / normalizingScale;
    double intervalStart = normalizer.apply(unnormalizedIntervalStart);
    double intervalEnd = normalizer.apply(unnormalizedIntervalEnd);

    Function<Observation, Double> unnormalizedXExtractor = o->(double)o.getResultTime().atZone(ZONE_ID).toEpochSecond();
    Function<Observation, Double> xExtractor =
            o->normalizer.apply(unnormalizedXExtractor.apply(o));
    Function<Observation, Double> fExtractor = Observation::getResultNumber;

    BiFunction<Double, List<Observation>, Observation> sampleGetter = (Double a, List<Observation> o) -> observationService.getObservationsByThingId(
            thingId,
            o.size() + 1,
            xSort.descending(),
            List.of(obsId),
            finalFrameStart,
            LocalDateTime.ofEpochSecond((long)(double)a, 0, ZonedDateTime.now(ZONE_ID).getOffset())
    ).stream().filter(oo->!o.contains(oo)).findFirst().orElse(null);

    List<Observation> sample = getApproximateTschebyscheffSamplingPoints(
        sampleGetter,
            maxInterPoints,
        unnormalizedIntervalStart, unnormalizedIntervalEnd
    );

    //if there are no samples, return empty image? perhaps an exception could be more fitting
    if(sample.isEmpty()) {
      throw new CantInterpolateWithNoSamplesException();
    }

    //copies sample points to file (debug code)
//    StringBuilder c = new StringBuilder();
//    for(Observation o : sample) {
//      c.append(xExtractor.apply(o)).append("\t").append(fExtractor.apply(o)).append("\n");
//    }
//    try (BufferedWriter w = new BufferedWriter(new FileWriter("sample_points.txt"))) {
//      w.write(c.toString());
//    }catch (Exception e){}

    //that is technically not how things work, but make a rough guess that the function is smooth enough that the
    // minima & maxima of the sample are lower and upper bounds of the interpolation function as well
    Comparator<Observation> fComp = Comparator.comparing(fExtractor);
    double min = sample.stream().min(fComp).get().getResultNumber();
    double max = sample.stream().max(fComp).get().getResultNumber();

    //this would give more space to the lower and upper bounds to hopefully compensate for unsmoothness
//    double mmdiff = max - min;
//    min -= mmdiff;
//    max += mmdiff;

    sample.sort(Comparator.comparing(xExtractor));
    Function<Double, Double> interpolFunc = interpolator.interpolate(sample, xExtractor, fExtractor);


    //various stats about the graph (debug code)
//    int alertus = 0;
//    double lastX = 0;
//    for(Observation g : sample){
//      double x = xExtractor.apply(g), y = interpolFunc.apply(x), knotdiff = y - fExtractor.apply(g);
//      System.out.printf("x: %s, y: %s, diff: %s, distance to last knot: %s\n", x, y, knotdiff, x - lastX);
//      double interX = (x+lastX) / 2; double interY = interpolFunc.apply(interX);
//      System.out.printf("extra| x: %s, y: %s\n", x, y);
//      if(knotdiff != 0) {
//        alertus += 1;
//      }
//      lastX = x;
//    }
//    System.out.printf("%s knots out of %s done goofed!\n", alertus, sample.size());
//
//    System.out.printf("interpolator: %s\n", interpolFunc);
//    System.out.printf("function: %s\n", ((LagrangeInterpolator.LagrangePolynomial)interpolFunc).toMathString());
//    System.out.println(sample.stream().map(Observation::getResultNumber).collect(Collectors.toList()));
//    System.out.println(sample.stream().map(o->(double)o.getResultTime().atZone(ZONE_ID).toEpochSecond()).collect(Collectors.toList()));
//    System.out.printf("lowx: %s, highx: %s\n", intervalStart, intervalEnd);
//    System.out.printf("min: %s, max: %s, interPoints: %s\n", min, max, interPoints);



    final double borderScale = .1;

    //sample.sort(Comparator.comparing(fExtractor));
    Graphics2D g = (Graphics2D)r.getGraphics();
    g.setColor(Color.black);
    drawGraph(
        g,
        round(borderScale * imageDimension.width), 0,
        round((1 - borderScale) * imageDimension.width), round((1 - borderScale) * imageDimension.height),
        intervalStart, intervalEnd, min, max,
        sample, interpolFunc,
        granularity,
        xExtractor, fExtractor
    );

    return r;
  }

  private <T> void drawGraph(
      Graphics2D g,
      int x, int y, int w, int h,
      double xIntStart, double xIntEnd, double yIntStart, double yIntEnd,
      List<T> sample, Function<Double, Double> interpolFunc, int granularity,
      Function<T, Double> xExtractor, Function<T, Double> yExtractor
  ){
    int imageScale = Math.max(w, h);
    //size of emphasis circle around base data points (relative to image scale)
    final double emphasisCircleScale = .009;
    final int emphasisCircleRad = round(emphasisCircleScale * imageScale);
    //thickness of interpolation lines (relative to image scale)
    final double lineThiccScale = .004;
    g.setStroke(new BasicStroke((float)(lineThiccScale * imageScale)));

    double xIntLen = xIntEnd - xIntStart;
    double yIntLen = yIntEnd - yIntStart;
    double wPxPerOne = w / xIntLen;
    double hPxPerOne = h / yIntLen;

//    System.out.printf("x: %s, y: %s, w: %s, h: %s\n",x,y,w,h);
//    System.out.printf("xIS: %s, xIE: %s, yIS: %s, yIE: %s\n",xIntStart,xIntEnd,yIntStart,yIntEnd);
//    System.out.printf("xIL: %s, yIL: %s, wPxPerOne: %s, hPxPerOne: %s\n",xIntLen,yIntLen,wPxPerOne,hPxPerOne);
//    System.out.printf("circle rad: %s, line thicc: %s\n", emphasisCircleRad, lineThiccScale * imageScale);

    for(int i = 0; i <= sample.size(); i ++) {
      int lowXPx = x + round(i == 0 ? 0 : wPxPerOne * (xExtractor.apply(sample.get(i - 1)) - xIntStart));
      int highXPx = x + round(i == sample.size() ? w : wPxPerOne * (xExtractor.apply(sample.get(i)) - xIntStart));

      //draws lines to approximate the function every #granularity pixels
      int nextXPx = lowXPx;
      int prevYPx = y + h - round(hPxPerOne * (interpolFunc.apply(xIntStart + (lowXPx - x) / wPxPerOne) - yIntStart));
      while(nextXPx != highXPx){
        nextXPx += granularity;
        if(nextXPx > highXPx) nextXPx = highXPx;
        int nextYPx =
            y + h - round(hPxPerOne * (interpolFunc.apply(xIntStart + (nextXPx - x) / wPxPerOne) - yIntStart));
//        double xx = xIntStart + (nextXPx - x) / wPxPerOne;
//        System.out.printf("x: %s, f(x): %s, f(x)->px: %s\n", xx,
//                interpolFunc.apply(xx), nextYPx);
        g.drawLine(nextXPx - granularity, prevYPx, nextXPx, nextYPx);
        prevYPx = nextYPx;
      }
    }

    //draws circles to accentuate the sample data points
    for(T t : sample) {
      int xPx = x + round(wPxPerOne * (xExtractor.apply(t) - xIntStart));
      int yPx = y + h - round(hPxPerOne * (yExtractor.apply(t) - yIntStart));
      g.drawOval(xPx - emphasisCircleRad / 2, yPx - emphasisCircleRad / 2, emphasisCircleRad, emphasisCircleRad);
    }
  }

  private <T> List<T> getApproximateTschebyscheffSamplingPoints(
          BiFunction<Double, List<T>, T> sampleGetterWithExcluded,
          int maxInterPoints,
          double intervalStart, double intervalEnd)
  {
    List<T> r = new ArrayList<>();

    for(int i=0; i<maxInterPoints; i++){
      //calculates the i-th Tschebyscheff sampling point transformed onto the given interval
      double samplingPoint =
          intervalStart + (Math.cos((double)(2 * i + 1) / (2 * maxInterPoints) * Math.PI) + 1) / 2 * (intervalEnd - intervalStart);
      T toAdd = sampleGetterWithExcluded.apply(samplingPoint, r);
      if(toAdd != null){
        r.add(toAdd);
      }
    }

    return r;
  }

}
