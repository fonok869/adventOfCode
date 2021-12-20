package com.fmolnar.code.year2021.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day13Challenge01 {

    private List<String> lines = new ArrayList<>();
    private Set<Dot> dotsAll = new HashSet<>();
    private List<Dot> hajtas = new ArrayList<>();
    private int xMax = 0;
    private int yMax = 0;

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day13/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            boolean instructions = false;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    if (instructions) {
                        String[] instruct = line.split("=");
                        if ("x".equals(instruct[0].substring(instruct[0].length() - 1))) {
                            hajtas.add(new Dot(Integer.valueOf(instruct[1]), 0));
                        } else if ("y".equals(instruct[0].substring(instruct[0].length() - 1))) {
                            hajtas.add(new Dot(0, Integer.valueOf(instruct[1])));
                        } else {
                            System.out.println("Baj van");
                        }

                    } else {
                        String[] dots = line.split(",");
                        Dot dot = new Dot(Integer.valueOf(dots[0]), Integer.valueOf(dots[1]));
                        dotsAll.add(dot);
                        if (Integer.valueOf(dots[0]) > xMax) {
                            xMax = Integer.valueOf(dots[0]);
                        }
                        if (Integer.valueOf(dots[1]) > yMax) {
                            yMax = Integer.valueOf(dots[1]);
                        }
                        yMax = 2*447;
                        xMax = 665*2;


                    }
                    lines.add(line);
                } else if (line.length() == 0) {
                    instructions = true;
                }
            }
        }


        System.out.println("XMax: " + xMax);
        System.out.println("YMax: " + yMax);
        hajtasaok();
    }

    private void hajtasaok() {
        Set<Dot> all = new HashSet<>(dotsAll);
       // Set<Dot> newDots = new HashSet<>();
        for (Dot hajtasok : hajtas) {
            Set<Dot> newDots = new HashSet<>();
            for (Dot dot : all) {
                if (hajtasok.x == 0) {
                    if (hajtasok.y < dot.y) {
                        newDots.add(new Dot(dot.x, ((yMax - dot.y) % hajtasok.y)));
                    } else if (dot.y < hajtasok.y) {
                        newDots.add(new Dot(dot.x, dot.y));
                    }
                } else if (hajtasok.y == 0) {
                    if (hajtasok.x < dot.x) {
                        int newX = (xMax - dot.x) % hajtasok.x;
                        newDots.add(new Dot(newX, dot.y));
                    } else if (dot.x < hajtasok.x) {
                        newDots.add(new Dot(dot.x, dot.y));
                    } else {
                        System.out.println("Limit: " + dot);
                    }
                }
            }
            if (hajtasok.x == 0) {
                yMax = hajtasok.y - 1;
            } else if(hajtasok.y == 0){
                xMax = hajtasok.x - 1;
            }

            System.out.println("Dots visible: " + newDots.size());
            break;
        }
    }


    class Dot {
        int x;
        int y;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return x == dot.x &&
                    y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Dot{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }


}




