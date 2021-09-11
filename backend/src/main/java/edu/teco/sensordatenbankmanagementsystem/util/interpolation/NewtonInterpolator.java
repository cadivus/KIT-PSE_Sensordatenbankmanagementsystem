package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@link NewtonInterpolator} interpolates (n+1) sample points using a n-th degree polynomial represented by the
 * Newton-basis
 * <p>
 * The error is cumulative from the left to the right interpolation points. This may result in massive compound
 * errors at the right interpolation points when used with a large number of interpolation points.
 */
public class NewtonInterpolator implements Interpolator<Double, Double> {

    private static NewtonInterpolator instance = null;

    public static NewtonInterpolator getInstance() {
        return instance == null ? (instance = new NewtonInterpolator()) : instance;
    }

    @Override
    public <X> Function<Double, Double> interpolate(List<X> samples, Function<X, Double> xExtractor,
                                                    Function<X, Double> fExtractor) {
        final List<Double> f;
        final List<Double> x;
        int N = samples.size();
        x = samples.stream().map(xExtractor)
                .collect(Collectors.toList());
        f = new ArrayList<>(N);
        List<Double> tmp = samples.stream().map(fExtractor).collect(Collectors.toList());
        for (int i = 0; i < N; i++) {
            if (i > 0) {
                for (int j = N - 1; j >= i; j--) {
                    tmp.set(j, (tmp.get(j - 1) - tmp.get(j)) / (x.get(j - i) - x.get(j)));
                }
            }
            f.add(tmp.get(i));
        }
        return ax -> {
            double val = f.get(f.size() - 1);
            for (int i = f.size() - 2; i >= 0; i--) {
                val *= (ax - x.get(i));
                val += f.get(i);
            }
            return val;
        };
    }

}