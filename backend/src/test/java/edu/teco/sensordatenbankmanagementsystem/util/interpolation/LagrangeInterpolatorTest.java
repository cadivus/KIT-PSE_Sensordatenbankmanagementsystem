package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static testutil.TestUtils.assertAlmost;

public class LagrangeInterpolatorTest {

    @Test
    void interpolateTest() {
        List<Double> samples = List.of(-1.,0.,1.);
        Function<Double, Double> xExtractor = Function.identity();
        Function<Double, Double> yExtractor = a -> a*a*a;
        List<Double> solutions = samples.stream().map(yExtractor).collect(Collectors.toList());
        Function<Double, Double> interpolFunc =
                LagrangeInterpolator.getInstance().interpolate(samples,
                        xExtractor, yExtractor);

        for(double sample : samples){
            assertAlmost(yExtractor.apply(sample), interpolFunc.apply(xExtractor.apply(sample)));
        }
    }
}
