package edu.teco.sensordatenbankmanagementsystem.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link Meth} provides mathematical utilities
 */
public final class Meth {

    private Meth() {
    }

    /**
     * Rounds a number to the nearest integer and towards positive infinity when tied
     *
     * @param a number to round
     * @return nearest integer
     */
    static public int round(double a) {
        return (int) Math.round(a);
    }

    /**
     * Gets the bisection interval the given key would be in a given list of sorted interval boundary points
     *
     * @param list of boundary points
     * @param key  to find the bisection interval of
     * @param <T>  type of the key
     * @return index of the bisection interval (0 indicates before the first boundary point)
     */
    static public <T>
    int bisect(List<? extends Comparable<? super T>> list, T key) {
        int r = Collections.binarySearch(list, key) + 1;
        return r < 0 ? -r : r;
    }

    /**
     * Solves a linear equation with a tridiagonal matrix
     * Tx = d
     *
     * @param t tridiagonal matrix T
     * @param d coefficient vector d
     * @return solution vector x
     */
    static public List<Double> solveTridiagonal(TridiagonalMatrix t, List<Double> d) {
        int n = t.n;
        List<Double> a = t.a;
        List<Double> b = t.b;
        List<Double> c = t.c;
        List<Double> cs = new ArrayList<>(n - 1);
        cs.add(c.get(0) / b.get(0));
        for (int i = 1; i < n - 1; i++) {
            cs.add(c.get(i) / (b.get(i) - a.get(i - 1) * cs.get(i - 1)));
        }
        List<Double> ds = new ArrayList<>(n);
        ds.add(0, d.get(0) / b.get(0));
        for (int i = 1; i < n; i++) {
            ds.add((d.get(i) - a.get(i - 1) * ds.get(i - 1) / (b.get(i) - a.get(i - 1) * cs.get(i - 1))));
        }
        List<Double> x = new ArrayList<>(n);
        x.add(ds.get(n - 1));
        for (int i = 1; i < n; i++) {
            x.add(ds.get(n - i - 1) - cs.get(n - i - 1) * x.get(i - 1));
        }
        Collections.reverse(x);
        return x;
    }

    /**
     * Calculates the dot product of a vector
     *
     * @param a vector to calculate dot product of
     * @return dot product of vector <code>a</code>
     */
    static public double dotProduct(List<Double> a) {
        return a.stream().reduce(0., (acc, v) -> acc + v * v);
    }

    /**
     * {@link TridiagonalMatrix} represents a tridiagonal matrix efficiently by only saving its sub-, super- and
     * diagonal
     */
    public static class TridiagonalMatrix {

        // subdiagonal
        public final List<Double> a;
        // diagonal
        public final List<Double> b;
        // superdiagonal
        public final List<Double> c;

        public final int n;

        public TridiagonalMatrix(List<Double> a, List<Double> b, List<Double> c) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.n = b.size();
        }
    }

}
