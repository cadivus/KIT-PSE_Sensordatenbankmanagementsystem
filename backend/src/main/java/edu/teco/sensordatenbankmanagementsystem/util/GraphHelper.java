package edu.teco.sensordatenbankmanagementsystem.util;

import static edu.teco.sensordatenbankmanagementsystem.util.Meth.round;

import edu.teco.sensordatenbankmanagementsystem.exceptions.CantInterpolateWithNoSamplesException;
import edu.teco.sensordatenbankmanagementsystem.models.Observation;
import edu.teco.sensordatenbankmanagementsystem.services.ObservationService;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.Interpolator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class GraphHelper {

  private final ObservationService observationService;

  GraphHelper(ObservationService observationService) {

    this.observationService = observationService;
  }

  private ZoneId ZONE_ID ;
  @Value("${globals.zone_id}")
  private void setDATE_FORMAT(String zoneId){
    ZONE_ID = switch (zoneId){
      case"default" -> ZoneId.systemDefault();
      default -> ZoneId.of(zoneId);
    };
  }


  public RenderedImage getGraphImageOfThing(String id, String obsId, LocalDateTime frameStart, LocalDateTime frameEnd,
      int maxInterPoints, Dimension imageDimension, int granularity,
      Interpolator<Double, Double> interpolator) {
    if(maxInterPoints == 0) {
      throw new CantInterpolateWithNoSamplesException();
    }

    BufferedImage r = new BufferedImage(imageDimension.width, imageDimension.height, BufferedImage.TYPE_INT_ARGB);

    List<Observation> all = observationService.getObservationsByThingId(
        id,
        Integer.MAX_VALUE,
        Sort.unsorted(),
        List.of(obsId),
        frameStart, frameEnd
    );

    int interPoints = Math.min(maxInterPoints, all.size() / 2);

    double intervalStart = frameStart.atZone(ZONE_ID).toEpochSecond();
    double intervalEnd = frameEnd.atZone(ZONE_ID).toEpochSecond();

    //attempt at normalizing massive x values generating by epoch seconds for smaller interpolation error
    double normalizingShift = (intervalEnd + intervalStart) / 2;
    double normalizingScale = (intervalEnd - intervalStart) / 2;
//    double normalizingShift = 0;
//    double normalizingScale = 1;
    Function<Double, Double> normalizer = a -> (a - normalizingShift) / normalizingScale;
    intervalStart = normalizer.apply(intervalStart);
    intervalEnd = normalizer.apply(intervalEnd);

    Function<Observation, Double> xExtractor =
        o->normalizer.apply((double)o.getResultTime().atZone(ZONE_ID).toEpochSecond());
    Function<Observation, Double> fExtractor = Observation::getResultNumber;

    List<Observation> sample = getApproximateTschebyscheffSamplingPoints(
        all,
        xExtractor,
        interPoints,
        intervalStart, intervalEnd
    );
    //assert that sample is of correct size
    if(sample.size() != interPoints) {
      throw new RuntimeException("sample list returned is not the correct size");
    }

    //that is technically not how things work, but make a rough guess that the function is smooth enough that the
    // minima & maxima of the sample are lower and upper bounds of the interpolation function as well
    Comparator<Observation> fComp = Comparator.comparing(fExtractor);
    double min = sample.stream().min(fComp).get().getResultNumber();
    double max = sample.stream().max(fComp).get().getResultNumber();
//    double mmdiff = max - min;
//    min -= mmdiff;
//    max += mmdiff;

    sample.sort(Comparator.comparing(xExtractor));
    Function<Double, Double> interpolFunc = interpolator.interpolate(sample, xExtractor, fExtractor);

    int alertus = 0;
    double lastX = 0;
    for(Observation g : sample){
      double x = xExtractor.apply(g), y = interpolFunc.apply(x), knotdiff = y - g.getResultNumber();
      System.out.printf("x: %s, y: %s, diff: %s, distance to last knot: %s\n", x, y, knotdiff, x - lastX);
      x = (x+lastX) / 2; y = interpolFunc.apply(x);
      System.out.printf("| x: %s, y: %s\n", x, y);
      if(knotdiff != 0) {
        alertus += 1;
      }
      lastX = x;
    }
//    System.out.printf("%s knots out of %s done goofed!\n", alertus, interPoints);
//
//    System.out.printf("interpolator: %s\n", interpolFunc);
//    System.out.printf("function: %s\n", ((LagrangeInterpolator.LagrangePolynomial)interpolFunc).toMathString());
//    System.out.println(sample.stream().map(Observation::getResultNumber).collect(Collectors.toList()));
//    System.out.println(sample.stream().map(o->(double)o.getResultTime().atZone(ZONE_ID).toEpochSecond()).collect(Collectors.toList()));
    //System.out.printf("lowx: %s, highx: %s\n", intervalStart, intervalEnd);
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

  private <T> List<T> getApproximateTschebyscheffSamplingPoints(List<T> elements, Function<T, Double> xExtractor,
      int interPoints,
      double intervalStart, double intervalEnd)
  {
    List<T> r = new ArrayList<>();
    //sorts the elements according to x to find the approximate sampling points faster
    elements.sort(Comparator.comparing(xExtractor));

    for(int i=0; i<interPoints; i++){
      //calculates the i-th Tschebyscheff sampling point transformed onto the given interval
      double samplingPoint =
          intervalStart + (Math.cos((double)(2 * i + 1) / (2 * interPoints) * Math.PI) + 1) / 2 * (intervalEnd - intervalStart);
      //i-th sampling point to add
      T toAdd = elements.get(elements.size() - 1);
      T last = toAdd;
      for(T t : elements) {
        if(xExtractor.apply(t) > samplingPoint){
          double tX = xExtractor.apply(t);
          double lastX = xExtractor.apply(last);
          toAdd = samplingPoint + samplingPoint - lastX - tX < 0 ? last : t;
          break;
        }
        last = t;
      }
      r.add(toAdd);
      elements.remove(toAdd);
    }

    return r;
  }

}
