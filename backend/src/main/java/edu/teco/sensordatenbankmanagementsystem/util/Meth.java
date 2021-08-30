package edu.teco.sensordatenbankmanagementsystem.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Meth {

    private Meth() {
    }

    static public int round(double a) {
        return (int) Math.round(a);
    }

    static public <T>
    int bisect(List<? extends Comparable<? super T>> list, T key){
        return -(Collections.binarySearch(list, key) + 1);
    }

    static public List<Double> solveTridiagonal(TridiagonalMatrix t, List<Double> d){
        int n = t.n;
        List<Double> a = t.a;
        List<Double> b = t.b;
        List<Double> c = t.c;
        List<Double> cs = new ArrayList<>(n-1);
        cs.add(c.get(0) / b.get(0));
        for(int i=1; i<n-1; i++){
            cs.add(c.get(i) / (b.get(i) - a.get(i-1) * cs.get(i-1)));
        }
        List<Double> ds = new ArrayList<>(n);
        ds.set(0, d.get(0) / b.get(0));
        for(int i=1; i<n; i++){
            ds.add((d.get(i) - a.get(i-1) * ds.get(i-1) / (b.get(i) - a.get(i-1) * cs.get(i-1))));
        }
        List<Double> x = new ArrayList<>(n);
        x.add(ds.get(n-1));
        for(int i=1; i<n; i++){
            x.add(ds.get(n-i-1) - cs.get(n-i-1) * x.get(i-1));
        }
        Collections.reverse(x);
        return x;
    }

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
