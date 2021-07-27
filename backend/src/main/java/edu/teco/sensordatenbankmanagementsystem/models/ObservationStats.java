package edu.teco.sensordatenbankmanagementsystem.models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;

public class ObservationStats {

    Map<String, Stats> obsIdToStats = new HashMap<>();

    public void addObservedProperty(String name, List<Double> values) {
        double avg;
        double stdv;
        double med;
        if (values.isEmpty()) {
            avg = stdv = med = 0;
        } else {
            avg = values.stream().mapToDouble(a -> a).average().orElse(0);
            stdv = Math.sqrt(values.stream().reduce(0., (a, b) -> a + (b - avg) * (b - avg)) / values.size());
            DoubleStream valuesInDoubleStream = values.stream().mapToDouble(a -> a).sorted();
            med = values.size() % 2 == 0 ?
                    valuesInDoubleStream.skip(values.size() / 2 - 1).limit(2).average().getAsDouble() :
                    valuesInDoubleStream.skip(values.size() / 2).findFirst().getAsDouble();
        }
        obsIdToStats.put(name, new Stats(avg, med, stdv));
    }

    private class Stats {
        public double avg;
        public double med;
        private double stdv;

        public Stats(double avg, double med, double stdv) {
            this.avg = avg;
            this.med = med;
            this.stdv = stdv;
        }
    }

}