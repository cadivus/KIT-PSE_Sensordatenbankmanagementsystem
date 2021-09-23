package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@link LagrangeInterpolator} interpolates (n+1) sample points using a n-th degree polynomial represented by the
 * Lagrange-polynomial-basis
 * <p>
 * The error is evenly distributed over all interpolation points.
 */
public class LagrangeInterpolator implements Interpolator<Double, Double> {

    private static LagrangeInterpolator instance = null;

    public static LagrangeInterpolator getInstance() {
        return instance == null ? (instance = new LagrangeInterpolator()) : instance;
    }

    @Override
    public <X> Function<Double, Double> interpolate(List<X> samples, Function<X, Double> xExtractor, Function<X, Double> fExtractor) {

        return new LagrangePolynomial(samples, xExtractor, fExtractor);
    }

    public static class LagrangePolynomial implements Function<Double, Double> {

        final List<Double> x;
        final List<Double> f;
        final List<Double> lagrangeCoefficients;

        public <X> LagrangePolynomial(List<X> samples, Function<X, Double> xExtractor, Function<X, Double> fExtractor) {
            this.x = samples.stream().map(xExtractor).collect(Collectors.toList());
            this.f = samples.stream().map(fExtractor).collect(Collectors.toList());
            this.lagrangeCoefficients = new ArrayList<>(samples.size());
            for (int i = 0; i < samples.size(); i++) {
                double xI = this.x.get(i);
                this.lagrangeCoefficients.add(this.x.stream().reduce(1., (a, x) -> xI == x ? a : a * (xI - x)));
            }
        }

        @Override
        public Double apply(Double ax) {
            final int N = this.x.size();

            //quick evaluation of original values
//            int indeax = x.indexOf(ax);
//            if(indeax >= 0) {
//                return f.get(indeax);
//            }

            double r = 0;
            for (int i = 0; i < N; i += 1) {
                double xI = this.x.get(i);
                // System.out.println("L_"+i+"(ax): " + x.stream().reduce(1., (a,x)->xI==x?a:a*(ax-x)) / this
                // .lagrangeCoefficients.get(i));
                r += f.get(i) * x.stream().reduce(1., (a, x) -> xI == x ? a : a * (ax - x)) / this.lagrangeCoefficients.get(i);
            }

            return r;
        }

        public String toMathString() {
            StringBuilder r = new StringBuilder();
            for (int i = 0; i < x.size(); i += 1) {
                double xI = x.get(i);
                r.append(f.get(i)).append("*");
                for (int j = 0; j < x.size(); j += 1) {
                    if (j != i) {
                        r.append(String.format("(x-%s)", x.get(j))).append("*");
                    }
                }
                r.append(1 / this.lagrangeCoefficients.get(i)).append("+");
            }
            r.setLength(r.length() - 1);
            return r.toString();
        }

        @Override
        public String toString() {
            return "LagrangePolynomial{" +
                    "x=" + x +
                    ", f=" + f +
                    ", lagrangeCoefficients=" + lagrangeCoefficients +
                    '}';
        }
    }

    /**
     * Stores the pairwise diffs of N values in an efficient NxN upper triagonal matrix
     */
    private static class PairwiseDiffMatrix implements BiFunction<Integer, Integer, Double> {

        private final List<Double> xDiffs;

        public PairwiseDiffMatrix(List<Double> echsen) {
            this.xDiffs = new ArrayList<>(echsen.size() * (echsen.size() + 1) / 2);
            for (int j = 0; j < echsen.size(); j++) {
                for (int i = 0; i <= j; i++) {
                    xDiffs.add(echsen.get(i) - echsen.get(j));
                }
            }
        }

        @Override
        public Double apply(Integer i, Integer j) {
            int sign = 1;
            if (i > j) {
                int tmp = j;
                j = i;
                i = tmp;
                sign = -1;
            }
            return sign * xDiffs.get(j * (j + 1) / 2 + i);
        }
    }

}
