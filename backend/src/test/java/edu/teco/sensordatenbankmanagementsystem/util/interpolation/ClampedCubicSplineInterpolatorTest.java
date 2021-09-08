package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static testutil.TestUtils.assertAlmost;

class ClampedCubicSplineInterpolatorTest {

    @Test
    void interpolateTest() {
        List<Double> samples = List.of(-1.,0.,1.);
        Function<Double, Double> xExtractor = Function.identity();
        Function<Double, Double> yExtractor = a -> a*a*a;
        List<Double> solutions = samples.stream().map(yExtractor).collect(Collectors.toList());
        Function<Double, Double> interpolFunc =
                ClampedCubicSplineInterpolator.getInstance().interpolate(samples,
                xExtractor, yExtractor);

        for(double sample : samples){
            assertAlmost(yExtractor.apply(sample), interpolFunc.apply(xExtractor.apply(sample)));
        }
    }
}