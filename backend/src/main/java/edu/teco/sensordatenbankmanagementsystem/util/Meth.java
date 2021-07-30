package edu.teco.sensordatenbankmanagementsystem.util;


import edu.teco.sensordatenbankmanagementsystem.util.interpolation.LagrangeInterpolator;
import edu.teco.sensordatenbankmanagementsystem.util.interpolation.NewtonInterpolator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.Function;

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

    public static void main(String[] args) throws Exception {
        List<Dongle> dongles = List.of(
                new Dongle(-1, 1),
                new Dongle(0, 6),
                new Dongle(1, -3),
                new Dongle(3, 3)
        );

        Function<Double,Double> d = LagrangeInterpolator.getInstance().interpolate(
                dongles,
                Dongle::getA,
                Dongle::getB
        );
        Function<Double,Double> dd = NewtonInterpolator.getInstance().interpolate(
                dongles,
                Dongle::getA,
                Dongle::getB
        );

        BufferedImage a;
        Graphics2D g;

        System.out.println(dd = d);

        int i = 0;
        for(Function<Double,Double> ff : new Function[]{d, dd}){

            a = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D)a.getGraphics();
            g.setColor(Color.black);
            drawGraph(
                    g, 0, 0, 500, 500,
                    -4, 4, -16 ,16, dongles, d, 1, Dongle::getA, Dongle::getB
            );
            ImageIO.write(a, "png", new File("graph"+i+++".png"));
        }

    }
    private static <T> void drawGraph(
            Graphics2D g,
            int x, int y, int w, int h,
            double xIntStart, double xIntEnd, double yIntStart, double yIntEnd,
            List<T> sample, Function<Double, Double> foncy, int granularity,
            Function<T, Double> xExtractor, Function<T, Double> yExtractor
    ){
        int imageScale = Math.max(w, h);
        //size of emphasis circle around base data points (relative to image scale)
        final double emphasisCircleScale = .009;
        final int emphasisCircleRad = round(emphasisCircleScale * imageScale);
        //thickness of interpolation lines (relative to image scale)
        final double lineThiccScale = .004;
        g.setStroke(new BasicStroke((float)(lineThiccScale * imageScale)));

        double xIntLen = xIntEnd - xIntStart;
        double yIntLen = yIntEnd - yIntStart;
        double wPxPerOne = w / xIntLen;
        double hPxPerOne = h / yIntLen;

//    System.out.printf("x: %s, y: %s, w: %s, h: %s\n",x,y,w,h);
//    System.out.printf("xIS: %s, xIE: %s, yIS: %s, yIE: %s\n",xIntStart,xIntEnd,yIntStart,yIntEnd);
//    System.out.printf("xIL: %s, yIL: %s, wPxPerOne: %s, yPxPerOne: %s\n",xIntLen,yIntLen,wPxPerOne,hPxPerOne);
//    System.out.printf("circle rad: %s, line thicc: %s\n", emphasisCircleRad, lineThiccScale * imageScale);

        for(int i = 0; i <= sample.size(); i ++) {
            int lowXPx = x + round(i == 0 ? 0 : wPxPerOne * (xExtractor.apply(sample.get(i - 1)) - xIntStart));
            int highXPx = x + round(i == sample.size() ? w : wPxPerOne * (xExtractor.apply(sample.get(i)) - xIntStart));

            //draws lines to approximate the function every #granularity pixels
            int nextXPx = lowXPx;
            int prevYPx = y + h - round(hPxPerOne * (foncy.apply(xIntStart + (lowXPx - x) / wPxPerOne) - yIntStart));
            while(nextXPx != highXPx){
                nextXPx += granularity;
                if(nextXPx > highXPx) nextXPx = highXPx;
                //System.out.printf("%s ", interpolator.f(xIntStart + (nextXPx - x) / wPxPerOne));
                int nextYPx =
                        y + h - round(hPxPerOne * (foncy.apply(xIntStart + (nextXPx - x) / wPxPerOne) - yIntStart));
                g.drawLine(nextXPx - granularity, prevYPx, nextXPx, nextYPx);
                prevYPx = nextYPx;
            }
        }

        //draws circles to accentuate the sample data points
        for(T t : sample) {
            int xPx = x + round(wPxPerOne * (xExtractor.apply(t) - xIntStart));
            int yPx = y + h - round(hPxPerOne * (yExtractor.apply(t) - yIntStart));
            g.drawOval(xPx - emphasisCircleRad / 2, yPx - emphasisCircleRad / 2, emphasisCircleRad, emphasisCircleRad);
        }
    }
}
