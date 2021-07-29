package edu.teco.sensordatenbankmanagementsystem.util;


import java.util.List;

public final class Meth {

    private Meth() {
    }

    static public int round(double a) {
        return (int) Math.round(a);
    }

    static class Dongle{
        double a;
        double b;

        public Dongle(double a, double b) {
            this.a = a;
            this.b = b;
        }

        public double getA() {
            return a;
        }

        public double getB() {
            return b;
        }
    }

    public static void main(String[] args) {
        List<Dongle> dongles = List.of(
                new Dongle(-1, 1),
                new Dongle(0, 6),
                new Dongle(1, -3),
                new Dongle(3, 3)
        );

        Interpolator<Dongle> d = new Interpolator<>(
                dongles,
                Dongle::getA,
                Dongle::getB
        );

        System.out.println(d);
        System.out.println(d.f(-2));
        System.out.println(d.f(-1));
        System.out.println(d.f(0));
        System.out.println(d.f(1));
        System.out.println(d.f(2));
        System.out.println(d.f(3));
    }
}
