package edu.teco.sensordatenbankmanagementsystem.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * interpolates expected numerical observation values on a given set of observation data
 */
public class Interpolator<T> {

    private final List<Double> f;
    private final List<Double> x;

    /**
     * interpolates by calculating the newton representation of the interpolation polynomial as we
     * don't have all function values at arbitrary positions, thus can not choose more efficient
     * interpolation points
     *
     * @param points points to interpolate to, don't overdo the amount
     */
    public Interpolator(List<T> points, Function<T, Double> xExtractor, Function<T, Double> fExtractor) {
        int N = points.size();
        this.x = points.stream().map(xExtractor)
                .collect(Collectors.toList());
        this.f = new ArrayList<>(N);
        List<Double> tmp = points.stream().map(fExtractor).collect(Collectors.toList());
        for (int i = 0; i < N; i++) {
            if (i > 0) {
                for (int j = N - 1; j >= i; j--) {
                    tmp.set(j, (tmp.get(j - 1) - tmp.get(j)) / (this.x.get(j - i) - this.x.get(j)));
                }
            }
            this.f.add(tmp.get(i));
        }
    }

    /**
     * applies the interpolating polynomial function f to x = f(x)
     *
     * @param x to get the value at
     * @return interpolated value at the given time
     */
    public double f(double x) {
        double val = this.f.get(this.f.size() - 1);
        for (int i = this.f.size() - 2; i >= 0; i--) {
            val *= (x - this.x.get(i));
            val += this.f.get(i);
        }
        return val;
    }

    @Override
    public String toString() {
        return "Interpolator{" +
                "f=" + f +
                ", x=" + x +
                '}';
    }
}