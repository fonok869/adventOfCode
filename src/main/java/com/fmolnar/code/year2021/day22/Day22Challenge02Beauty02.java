package com.fmolnar.code.year2021.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22Challenge02Beauty02 {

    public static final String commandX = "((-?\\d{1,10})..(-?\\d{1,10}))";
    private Pattern matcherX = Pattern.compile(commandX);

    public static final String commandY = "((-?\\d{1,10})..(-?\\d{1,10}))";
    private Pattern matcherY = Pattern.compile(commandY);

    public static final String commandZ = "((-?\\d{1,10})..(-?\\d{1,10}))";
    private Pattern matcherZ = Pattern.compile(commandZ);


    List<Rectangle> rectanglesOns = new ArrayList<>();
    List<Rectangle> rectangles = new ArrayList<>();


    //on x=10..12,y=10..12,z=10..12
    //on x=11..13,y=11..13,z=11..13

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day22/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    String xGroup = line.substring(line.indexOf('x'), line.indexOf('y'));
                    String yGroup = line.substring(line.indexOf('y'), line.indexOf('z'));
                    String zGroup = line.substring(line.indexOf('z'));

                    Matcher matchX = matcherX.matcher(xGroup);
                    int x1 = 0;
                    int x2 = 0;
                    int y1 = 0;
                    int y2 = 0;
                    int z1 = 0;
                    int z2 = 0;

                    if (matchX.find()) {
                        x1 = Integer.valueOf(matchX.group(2));
                        x2 = Integer.valueOf(matchX.group(3));
                    }

                    Matcher matchY = matcherY.matcher(yGroup);
                    if (matchY.find()) {
                        y1 = Integer.valueOf(matchY.group(2));
                        y2 = Integer.valueOf(matchY.group(3));
                    }

                    Matcher matchZ = matcherZ.matcher(zGroup);
                    if (matchZ.find()) {
                        z1 = Integer.valueOf(matchZ.group(2));
                        z2 = Integer.valueOf(matchZ.group(3));
                    }


                    if (line.startsWith("on")) {
                        rectangles.add(new Rectangle(true, x1, x2, y1, y2, z1, z2));
                    } else {
                        rectangles.add(new Rectangle(false, x1, x2, y1, y2, z1, z2));
                    }
                }
            }
        }
        rectangles.forEach(System.out::println);

        List<Cuboid> reactor = new ArrayList<>();
        int counter = 0;

        for (Rectangle rectangleActual : rectangles) {
            List<Cuboid> newCubs = new ArrayList<>();
            if (rectangleActual.on) {
                newCubs.add(Cuboid.fromRectangle(1, rectangleActual));
            }

            Cuboid cuboidActual = Cuboid.fromRectangle(1, rectangleActual);

            for (Cuboid cuboid : reactor) {
                Cuboid cuboidIntersection = calculateIntersection(cuboidActual,cuboid);
                if (cuboidIntersection != null) {
                    int elojel = (-1) * cuboid.elojel;
                    newCubs.add(Cuboid.fromCuboidWithElojel(elojel, cuboidIntersection));
                }
            }

            reactor.addAll(newCubs);

            System.out.println("Counter: : " + counter++  + " " + reactor.size()+ " " + reactor.stream().mapToLong(s -> s.volume()).sum());

        }

        System.out.println("Day22Challenge02: " + reactor.stream().mapToLong(s -> s.volume()).sum());
    }

    private Cuboid calculateIntersection(Cuboid r1, Cuboid r2) {
        Cuboid intersection = new Cuboid(1,
                Math.max(r1.x1, r2.x1), Math.min(r1.x2, r2.x2),
                Math.max(r1.y1, r2.y1), Math.min(r1.y2, r2.y2),
                Math.max(r1.z1, r2.z1), Math.min(r1.z2, r2.z2)
        );
        if ((intersection.x1 > intersection.x2) || (intersection.y1 > intersection.y2) ||
                (intersection.z1 > intersection.z2)) {
            return null;
        }
        return intersection;
    }

    private Rectangle calculateIntersection(Rectangle r1, Rectangle r2) {
        Rectangle intersection = new Rectangle(true,
                Math.max(r1.x1, r2.x1), Math.min(r1.x2, r2.x2),
                Math.max(r1.y1, r2.y1), Math.min(r1.y2, r2.y2),
                Math.max(r1.z1, r2.z1), Math.min(r1.z2, r2.z2)
        );
        if ((intersection.x1 > intersection.x2) || (intersection.y1 > intersection.y2) ||
                (intersection.z1 > intersection.z2)) {
            return null;
        }
        return intersection;
    }

    // 608785599994040261
    // 2758514936282235
    //       2005776293634


    public static record Cuboid(int elojel, int x1, int x2, int y1, int y2, int z1, int z2) {

        public static Cuboid fromCuboidWithElojel(int elojel, Cuboid r){
            return new Cuboid(elojel, r.x1, r.x2, r.y1, r.y2, r.z1, r.z2);
        }

        public static Cuboid fromRectangle(int elojel, Rectangle r) {
            return new Cuboid(elojel, r.x1, r.x2, r.y1, r.y2, r.z1, r.z2);
        }

        public long volume() {
            long x = Math.abs((x2 - x1)+1);
            long y = Math.abs((y2 - y1)+1);
            long z = Math.abs((z2 - z1)+1);
            return (elojel*1L) * x *y *z;
        }

    }

    public static record Rectangle(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {

        public Rectangle newRectangle(int x1Plus, int x2Plus, int y1Plus, int y2Plus, int z1Plus, int z2Plus) {
            return new Rectangle(this.on(), x1() + x1Plus, x2() + x2Plus, y1() + y1Plus, y2() + y2Plus, z1() + z1Plus, z2() + z2Plus);
        }

        public long size() {
            return 1L * (x2 - x1) * (y2 - y1) * (z2 - z1);
        }


        @Override
        public String toString() {
            return (on? "on" : "off") + " x=" + x1 + ".." + x2 + ",y=" + y1 + ".." + y2 + ",z=" + z1 + ".." + z2;
        }
    }

    public static record Point(int x, int y, int z) {

    }
}
