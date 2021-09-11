package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import edu.teco.sensordatenbankmanagementsystem.exceptions.FunctionQueriedOutsideOfInterpolationIntervalException;
import edu.teco.sensordatenbankmanagementsystem.util.Meth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * {@link LagrangeInterpolator} interpolates (n+1) sample points using a cubic spline with clamped boundary
 * conditions
 * <p>
 * Though this does not extrapolate beyond the boundaries, it results in a smooth graph despite a large number of
 * interpolation points, if these interpolation points are relatively smooth themselves.
 */
public class ClampedCubicSplineInterpolator implements Interpolator<Double, Double> {

    private static ClampedCubicSplineInterpolator instance = null;

    public static ClampedCubicSplineInterpolator getInstance() {
        return instance == null ? (instance = new ClampedCubicSplineInterpolator()) : instance;
    }

    /**
     * The clamped velocity at the start and end of the interval
     */
    public double startVelocity = 0;
    public double endVelocity = 0;

    @Override
    public <X> Function<Double, Double> interpolate(List<X> samples, Function<X, Double> xExtractor, Function<X, Double> fExtractor) {
        int N = samples.size();
        List<Double> x = samples.stream().map(xExtractor).collect(Collectors.toList());
        List<Double> y = samples.stream().map(fExtractor).collect(Collectors.toList());
        List<Double> divDif = IntStream.range(0, N - 1).
                mapToObj(i -> (y.get(i) - y.get(i + 1)) / (x.get(i) - x.get(i + 1))).
                collect(Collectors.toList());
        List<Double> d = new ArrayList<>(N);
        d.add(divDif.get(0) - startVelocity);
        d.addAll(
                IntStream.range(1, N - 1).
                        mapToObj(i -> divDif.get(i) - divDif.get(i - 1)).
                        collect(Collectors.toList())
        );
        d.add(endVelocity - divDif.get(divDif.size() - 1));
        List<Double> h = IntStream.range(0, N - 1).
                mapToObj(i -> x.get(i + 1) - x.get(i)).
                collect(Collectors.toList());
        List<Double> diag = new ArrayList<>(N);
        diag.add(2 * h.get(0));
        diag.addAll(
                IntStream.range(1, N - 1).
                        mapToObj(i -> divDif.get(i) - divDif.get(i - 1)).
                        collect(Collectors.toList())
        );
        diag.add(2 * h.get(h.size() - 1));
        List<Double> sigmaGrindset = Meth.solveTridiagonal(
                new Meth.TridiagonalMatrix(
                        h,
                        diag,
                        h
                ),
                d
        );
        List<Double> alphaGrindset = IntStream.range(0, N - 1).
                mapToObj(i -> (sigmaGrindset.get(i) + 2 * sigmaGrindset.get(i + 1)) / h.get(i)).
                collect(Collectors.toList());
        List<Double> betaGrindset = IntStream.range(0, N - 1).
                mapToObj(i -> -(2 * sigmaGrindset.get(i) + sigmaGrindset.get(i + 1)) / h.get(i)).
                collect(Collectors.toList());
        return new ConstructedCubicSpline(
                N,
                x,
                y,
                divDif,
                alphaGrindset,
                betaGrindset
        );
    }

    /**
     * {@link ConstructedCubicSpline} represents one cubic spline
     */
    private static class ConstructedCubicSpline
            implements Function<Double, Double> {

        private int N;
        private List<Double> x;
        private List<Double> y;
        private List<Double> divDif;
        private List<Double> alphas;
        private List<Double> betas;

        public ConstructedCubicSpline(int N, List<Double> x, List<Double> y, List<Double> divDif, List<Double> alphas, List<Double> betas) {
            this.N = N;
            this.x = x;
            this.y = y;
            this.divDif = divDif;
            this.alphas = alphas;
            this.betas = betas;
        }

        @Override
        public Double apply(Double x) {
            double lbound = this.x.get(0);
            double rbound = this.x.get(this.N - 1);
            int i;
            if (x == rbound) {
                i = this.N - 1;
            } else {
                i = Meth.bisect(this.x, x);
            }
            if (i <= 0 || i >= this.N) {
                throw new FunctionQueriedOutsideOfInterpolationIntervalException(
                        x, lbound, rbound
                );
            }
            //i = i <= 0 ? 1 : i >= this.N ? this.N - 1 : i;
            double ldif = (x - this.x.get(i - 1)), rdif = (x - this.x.get(i));
            return this.y.get(i - 1) + ldif * this.divDif.get(i - 1) +
                    ldif * rdif * (this.alphas.get(i - 1) * ldif + this.betas.get(i - 1) * rdif);
        }
    }
}
