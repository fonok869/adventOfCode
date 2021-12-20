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

public class Day13Challenge02 {

    private List<String> lines = new ArrayList<>();
    private Set<Dot> dotsAll = new HashSet<>();
    private List<Dot> hajtasok = new ArrayList<>();
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
                            hajtasok.add(new Dot(Integer.valueOf(instruct[1]), 0));
                        } else if ("y".equals(instruct[0].substring(instruct[0].length() - 1))) {
                            hajtasok.add(new Dot(0, Integer.valueOf(instruct[1])));
                        } else {
                            System.out.println("Baj van");
                        }

                    } else {
                        String[] dots = line.split(",");
                        Integer x = Integer.valueOf(dots[0]);
                        Integer y = Integer.valueOf(dots[1]);
                        Dot dot = new Dot(x, y);
                        dotsAll.add(dot);
                        if (x > xMax) {
                            xMax = x;
                        }

                        if (y > yMax) {
                            yMax = y;
                        }
                        yMax = 2 * 447;
                        xMax = 655 * 2;


                    }
                    lines.add(line);
                } else if (line.length() == 0) {
                    instructions = true;
                }
            }
        }
        // Part 02
        hajtasaok();
    }

    private void hajtasaok() {
        int size = hajtasok.size();
        int counter = 0;
        Set<Dot> all = new HashSet<>(dotsAll);
        for (Dot hajtas : hajtasok) {
            counter++;
            Set<Dot> newDots = new HashSet<>();
            int hajtasX = hajtas.x;
            int hajtasY = hajtas.y;
            for (Dot dot : all) {
                int dotX = dot.x;
                int dotY = dot.y;
                int dotNewX = dotX;
                int dotNewY = dotY;
                if (hajtasX == 0) {
                    if (hajtasY < dotY) {
                        newDots.add(new Dot(dotX, ((yMax - dotY) % hajtasY)));
                    } else if (dotY < hajtasY) {
                        newDots.add(new Dot(dotX, dotY));
                    }
                } else if (hajtasY == 0) {
                    if (hajtasX < dotX) {
                        int newX = (xMax - dotX) % hajtasX;
                        newDots.add(new Dot(newX, dotY));
                    } else if (dotX < hajtasX) {
                        newDots.add(new Dot(dotX, dotY));
                    } else {
                        System.out.println("Limit: " + dot);
                    }
                }
            }
            if (counter < size) {

                if (hajtasX == 0) {
                    yMax = hajtasY - 1;
                } else if (hajtasY == 0) {
                    xMax = hajtasX - 1;
                }
            }
            all = new HashSet<>(newDots);
        }
        for (Dot dot : all) {
            if (xMax <= dot.x) {
                xMax = dot.x;
            } else if (yMax <= dot.y) {
                yMax = dot.y;
            }
        }
        char[][] codes = new char[xMax][yMax];
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < yMax; j++) {
                Dot dotToCheck = new Dot(i, j);
                if (all.contains(dotToCheck)) {
                    codes[i][j] = 'O';
                } else {
                    codes[i][j] = ' ';
                }
            }
        }

        for (int j = 0; j < yMax/2; j++) {
            String line = "";
            for (int i = 0; i < xMax; i++) {

                if (i % 5 == 4) {
                    line = line + " | ";
                } else
                    line = line + codes[i][j];
            }
            System.out.println(line);
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




