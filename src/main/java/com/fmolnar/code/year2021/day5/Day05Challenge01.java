package com.fmolnar.code.year2021.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05Challenge01 {

    public static final String command = "((\\d{1,5}),(\\d{1,5})) -> ((\\d{1,5}),(\\d{1,5}))";
    private Pattern dataMatcher = Pattern.compile(command);
    Set<Point> allPoints = new HashSet<>();
    Set<Point> doublePoints = new HashSet<>();

    public void calculate() {

        InputStream readerToRemove = getClass().getResourceAsStream("/2021/day05/input.txt");
        try (BufferedReader fileToRemove = new BufferedReader(new InputStreamReader(readerToRemove))) {
            String line;
            int counter = 0;
            while ((line = fileToRemove.readLine()) != null) {
                Matcher match = dataMatcher.matcher(line);
                boolean found = match.find();
                int x1 = Integer.valueOf(match.group(2));
                int y1 = Integer.valueOf(match.group(3));
                int x2 = Integer.valueOf(match.group(5));
                int y2 = Integer.valueOf(match.group(6));
                Point oldPoint = new Point(x1, y1);
                Point newPoint = new Point(x2, y2);
                if (x1 == x2 || y1 == y2) {
                    calculateDouble(oldPoint, newPoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Result (1): " +doublePoints.size());
    }

    private void calculateDouble(Point regiPoint, Point ujPoint) {

        // Vertical movement
        if (regiPoint.x == ujPoint.x) {
            int yKezdet = regiPoint.y < ujPoint.y ? regiPoint.y : ujPoint.y;
            int yVeg = yKezdet == regiPoint.y ? ujPoint.y : regiPoint.y;
            for (int y = yKezdet; y <= yVeg; y++) {
                Point pointToCheck = new Point(regiPoint.x, y);
                if (allPoints.contains(pointToCheck)) {
                    doublePoints.add(pointToCheck);
                } else {
                    allPoints.add(pointToCheck);
                }
            }
            return;
        }

        // Horizontal movement
        int xKezdet = regiPoint.x < ujPoint.x ? regiPoint.x : ujPoint.x;
        int xVeg = xKezdet == regiPoint.x ? ujPoint.x : regiPoint.x;
        for (int x = xKezdet; x <= xVeg; x++) {
            Point pointToCheck = new Point(x, regiPoint.y);
            if (allPoints.contains(pointToCheck)) {
                doublePoints.add(pointToCheck);
            } else {
                allPoints.add(pointToCheck);
            }
        }
    }

    class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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
