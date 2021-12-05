package com.fmolnar.code.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CuccSegedlet {

    public static final String command = "((\\d{1,5}),(\\d{1,5})): ((\\d{1,5})x(\\d{1,5}))";
    private Pattern dateMatcher = Pattern.compile(command);
    List<List<Point>> pointsEnsembles = new ArrayList<>();
    Set<Point> points = new HashSet<>();
    Set<Point> doublesPoints = new HashSet<>();

    public void calculate() {
        InputStream readerToRemove = getClass().getResourceAsStream("/aoc/day3_2018.txt");
        try (BufferedReader fileToRemove = new BufferedReader(new InputStreamReader(readerToRemove))) {
            String line;
            int counter = 0;
            while ((line = fileToRemove.readLine()) != null) {
                Matcher match = dateMatcher.matcher(line);
                boolean found = match.find();
                System.out.println("Counter: " + counter++);
                int left = Integer.valueOf(match.group(2));
                int top = Integer.valueOf(match.group(3));
                int wide = Integer.valueOf(match.group(5));
                int tall = Integer.valueOf(match.group(6));
                parcourir(left, top, wide, tall);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        calculateIntact();
        //calculateClaims();

    }

    private void calculateIntact() {
        outer:
        for (int i = 0; i < pointsEnsembles.size(); i++) {
            List<Point> pointsToExamine = pointsEnsembles.get(i);
            boolean breaked = false;
            for(Point point : pointsToExamine){
                if(doublesPoints.contains(point)){
                    breaked = true;
                    break;
                } else {

                }
            }
            if(!breaked){
                System.out.println("i: " +i);
            }
        }
    }


    private void parcourir(int left, int top, int wide, int tall) {
        List<Point> arrays = new ArrayList<>();
        for (int i = left; i < wide + left; i++) {
            for (int y = top; y < top + tall; y++) {
                Point pointer = new Point(i, y);
                if (points.contains(pointer)) {
                    doublesPoints.add(pointer);
                } else {
                    points.add(pointer);
                }
                arrays.add(pointer);
            }
        }
        pointsEnsembles.add(arrays);
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
            if (!(o instanceof Point)) return false;
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
