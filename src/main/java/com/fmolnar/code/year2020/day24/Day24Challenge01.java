package com.fmolnar.code.year2020.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day24Challenge01 {
    public Day24Challenge01() {

    }

    private final Point W = new Point(-2, 0);
    private final Point E = new Point(2, 0);
    private final Point NW = new Point(-1, 1);
    private final Point NE = new Point(1, 1);
    private final Point SW = new Point(-1, -1);
    private final Point SE = new Point(1, -1);

    private List<String> lines = new ArrayList<>();
    List<Point> points = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2020/day24/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    points.add(handle(line));
                }
            }
        }
        List<String> strings = points.stream().map(s->s.toString()).collect(Collectors.toList());
        Map<String, Long> counts = strings.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        long count = counts.entrySet().stream().filter(stringLongEntry -> (stringLongEntry.getValue()%2==1)).count();
        System.out.println("Black: " + count);
    }

    private Point handle(String line) {
        Point startingPoint = new Point(0, 0);
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == 'e') {
                startingPoint = osszead(startingPoint, E);
            } else if (line.charAt(i) == 'w') {
                startingPoint = osszead(startingPoint, W);
            } else if (line.charAt(i) == 's') {
                if (line.charAt(i + 1) == 'e') {
                    startingPoint = osszead(startingPoint, SE);
                } else {
                    startingPoint = osszead(startingPoint, SW);
                }
                i = i + 1;
            } else if (line.charAt(i) == 'n') {
                if (line.charAt(i + 1) == 'e') {
                    startingPoint = osszead(startingPoint, NE);
                } else {
                    startingPoint = osszead(startingPoint, NW);
                }
                i = i + 1;
            } else {
                throw new RuntimeException("Nem gondoltam ra");
            }
        }
        return startingPoint;
    }

    private Point osszead(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    public class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
