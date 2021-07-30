package edu.teco.sensordatenbankmanagementsystem.util.interpolation;

import java.util.List;
import java.util.function.Function;

public interface Interpolator <T, R> {

    <X> Function<T, R> interpolate(List<X> samples, Function<X, T> xExtractor, Function<X, R> fExtractor);

}
