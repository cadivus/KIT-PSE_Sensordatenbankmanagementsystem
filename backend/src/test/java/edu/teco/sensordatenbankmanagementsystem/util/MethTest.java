package edu.teco.sensordatenbankmanagementsystem.util;

import org.junit.jupiter.api.Test;
import testutil.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class MethTest {

    @Test
    void roundTest() {
        assertEquals(3, Meth.round(3.49));
        assertEquals(4, Meth.round(3.5));
    }

    @Test
    void bisectTest() {
        List<Double> iList = IntStream.range(0, 13).mapToObj(i -> (Double) (double) i).collect(Collectors.toList());
        assertEquals(0, Meth.bisect(iList, -1.));
        assertEquals(1, Meth.bisect(iList, 0.));
        assertEquals(8, Meth.bisect(iList, 7.));
        assertEquals(6, Meth.bisect(iList, 5.5));
        assertEquals(13, Meth.bisect(iList, 69.));
    }

    @Test
    void dotProductTest() {
        int n = 3;
        List<Double> v = new ArrayList<>(List.of(0., 0., 0.));
        for (int i = 0; i < n; i++) {
            v.set(i, 1.);
            TestUtils.assertAlmost(1., Meth.dotProduct(v));
            v.set(i, 0.);
        }
        TestUtils.assertAlmost(13, Meth.dotProduct(List.of(3., 4., 12.)));
    }

    @Test
    static void solveTridiagonalTest() {
        Meth.TridiagonalMatrix t = new Meth.TridiagonalMatrix(
                List.of(1., 1.),
                List.of(1., 1., 1.),
                List.of(1., 1.)
        );
        List<Double> x = List.of(1., 2., 3.);
        List<Double> xr = Meth.solveTridiagonal(t, List.of(3., 6., 5.));
        for (int i = 0; i < 3; i++) {
            TestUtils.assertAlmost(x.get(i), xr.get(i));
        }
    }

}
