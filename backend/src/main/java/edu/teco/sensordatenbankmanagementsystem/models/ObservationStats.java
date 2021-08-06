package edu.teco.sensordatenbankmanagementsystem.models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class ObservationStats {

    public final Map<String, Stats> obsIdToStats = new HashMap<>();

    public void addObservedProperty(String name, List<Double> values) {
        Stats tp = new Stats();
        if (!values.isEmpty()) {
            tp.avg = values.stream().mapToDouble(a -> a).average().orElse(0);
            tp.stdv = Math.sqrt(values.stream().reduce(0., (a, b) -> a + (b - tp.avg) * (b - tp.avg)) / values.size());
            DoubleStream valuesInDoubleStream = values.stream().mapToDouble(a -> a).sorted();
            tp.med = values.size() % 2 == 0 ?
                    valuesInDoubleStream.skip(values.size() / 2 - 1).limit(2).average().getAsDouble() :
                    valuesInDoubleStream.skip(values.size() / 2).findFirst().getAsDouble();
            tp.min = values.get(0);
            tp.max = values.get(values.size() - 1);
        }
        obsIdToStats.put(name, tp);
    }

    private class Stats {
        public double avg;
        public double med;
        public double stdv;
        double max = Double.MIN_VALUE;
        public double min = Double.MAX_VALUE;
    }

}