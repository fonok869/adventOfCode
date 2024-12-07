package com.fmolnar.code.year2023.day21;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day21 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day21/input.txt");

        Set<Point> rocks = new HashSet<>();
        Set<Point> startingPoints = new HashSet<>();
        int yMax = lines.size();
        int xMax = lines.get(0).length();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    rocks.add(new Point(y, x));
                } else if (line.charAt(x) == 'S') {
                    startingPoints.add(new Point(y, x));
                }
            }
        }
        Set<Point> directions = new HashSet<>();
        directions.add(new Point(0, 1));
        directions.add(new Point(0, -1));
        directions.add(new Point(-1, 0));
        directions.add(new Point(1, 0));

        int maxStep = 350;
        int modulo = -4 % 3;
        Set<Point> newSteps = new HashSet<>();
        int beforeStartingPoint = startingPoints.size();
        for (int i = 1; i < maxStep + 1; i++) {

            System.out.println("i: " +i + " size:" +  startingPoints.size() + " diff: " + (startingPoints.size()-beforeStartingPoint));
            beforeStartingPoint = startingPoints.size();


            startingPoints.forEach(point -> {
                directions.forEach(direction -> {
                    Point pointToCheck = point.add(direction);
                    int yMAradek = pointToCheck.y % yMax;
                    if (yMAradek < 0) {
                        yMAradek += yMax;
                    }

                    int xMAradek = pointToCheck.x % xMax;
                    if (xMAradek < 0) {
                        xMAradek += xMax;
                    }
                    Point pointModules = new Point(yMAradek, xMAradek);
                    if (!rocks.contains(pointModules)) {
                        newSteps.add(pointToCheck);
                    }
                });
            });
            startingPoints.clear();
            startingPoints.addAll(newSteps);
            newSteps.clear();
        }

        System.out.println("Result: " + (14669l*202300l*202300l+14738l*202300l+3701l));


    }



    record Point(int y, int x) {

        Point add(Point pNew) {
            return new Point(y + pNew.y, x + pNew.x);
        }
    }
}
