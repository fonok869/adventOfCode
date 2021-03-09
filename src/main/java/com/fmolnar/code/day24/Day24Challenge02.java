package com.fmolnar.code.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24Challenge02 {
    public Day24Challenge02() {
        szomszedok = new ArrayList<>();
        szomszedok.add(W);
        szomszedok.add(E);
        szomszedok.add(NW);
        szomszedok.add(NE);
        szomszedok.add(SE);
        szomszedok.add(SW);

    }

    private final Point W = new Point(-2, 0);
    private final Point E = new Point(2, 0);
    private final Point NW = new Point(-1, 1);
    private final Point NE = new Point(1, 1);
    private final Point SW = new Point(-1, -1);
    private final Point SE = new Point(1, -1);
    List<Point> szomszedok = null;
    List<Point> blacks = new ArrayList<>();

    private static final int iteration = 100;

    private List<String> lines = new ArrayList<>();
    List<Point> points = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/day24/input.txt");
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
        List<String> blackPointsString = counts.entrySet().stream().filter(stringLongEntry -> (stringLongEntry.getValue()%2==1)).map(en -> en.getKey()).collect(Collectors.toList());
        fillBlacks(blackPointsString);
        for(int i=0; i<iteration; i++){
            szamolas();
            System.out.println("Blacks size " +" iteration: " + blacks.size());
        }
    }

    private void szamolas() {
        Set<Point> szomszedokToCheck = new HashSet<>();
        List<Point> newBlacks = new ArrayList<>();
        for(Point black: blacks){
            int countBlackSzomszed = addToSzomszedokToCheck(black, szomszedokToCheck);
            // Remain black point --> otherwise white point
            if(countBlackSzomszed == 1 || countBlackSzomszed == 2){
                newBlacks.add(black);
            }
        }

        // SzomszedToCheck white -> black
        for(Point szomszedToCheckAsWhite : szomszedokToCheck){
            // AlreadyChecked
            if(blacks.contains(szomszedToCheckAsWhite)){
                continue;
            } // White --> black
            else {
                int countBlackSzomszed = addToSzomszedokToCheck(szomszedToCheckAsWhite, new HashSet<>());
                if(countBlackSzomszed==2){
                    newBlacks.add(szomszedToCheckAsWhite);
                }
            }
        }

        blacks = newBlacks;
    }


    private int addToSzomszedokToCheck(Point black, Set<Point> szomszedokToCheck) {
        int counter =0;
        for(Point szomszed : szomszedok){
            Point szomszedPoint = osszead(black, szomszed);
            szomszedokToCheck.add(szomszedPoint);
            if(blacks.contains(szomszedPoint)){
                counter++;
            }
        }
        return counter;
    }

    private void fillBlacks(List<String> blackPointsString) {
        for(String black : blackPointsString){
            for(Point point : points){
                if(point.toString().equals(black)){
                    blacks.add(point);
                    break;
                }
            }
        }
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
