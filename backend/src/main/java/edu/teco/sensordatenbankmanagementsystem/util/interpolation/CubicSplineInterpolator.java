package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import java.util.List;
import java.util.function.Function;

/**
 * using cubic splines to interpolate
 */
public class CubicSplineInterpolator implements Interpolator<Double, Double> {

    private static LagrangeInterpolator instance = null;

    public static LagrangeInterpolator getInstance() {
        return instance == null ? (instance = new LagrangeInterpolator()) : instance;
    }

    //todo Felix, quite the effort, gonna do that when I feel like it
    @Override
    public <X> Function<Double, Double> interpolate(List<X> samples, Function<X, Double> xExtractor, Function<X, Double> fExtractor) {
        return null;
    }
}
