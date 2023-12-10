package com.fmolnar.code.year2023.day10;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day10 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    public static Map<Point, Character> pointInst = new HashMap<>();

    public static void calculate() throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2023/day10/input.txt");
        Point beginning = null;
        Set<Point> pontok = new HashSet<>();

        for (int j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            for (int i = 0; i < line.length(); i++) {
                pointInst.put(new Point(j, i), line.charAt(i));
                if ('S' == line.charAt(i)) {
                    beginning = new Point(j, i);
                }
                if (line.charAt(i) == '.') {
                    pontok.add(new Point(j, i));
                }
            }
        }

        final Point N = new Point(-1, 0);
        final Point S = new Point(1, 0);
        final Point E = new Point(0, 1);
        final Point W = new Point(0, -1);

        List<Point> startingPoints = new ArrayList<>();
        List<Point> newStartingPoints = new ArrayList<>();
        startingPoints.add(beginning);
        List<Point> directions = Arrays.asList(N, S, E, W);
        Set<Point> alreadyVisited = new HashSet<>();
        Set<Point> bornerPoint = new HashSet<>();
        bornerPoint.add(beginning);
        Set<Point> felfelePoint = new HashSet<>();
        Set<Point> jobbraPoint = new HashSet<>();
        int boucle = -1;
        for (int lepes = 1; lepes < 100000; lepes++) {
            startingPoints.forEach(
                    start -> directions.forEach(direction ->
                            {
                                alreadyVisited.add(start);
                                Point toCheck = start.add(direction);
                                Character startChar = pointInst.get(start);
                                if (alreadyVisited.contains(toCheck)) {
                                    return;
                                }
                                Character actualChar = pointInst.get(toCheck);
                                if (actualChar == null || '.' == actualChar || 'S' == actualChar) {
                                    alreadyVisited.add(toCheck);
                                    return;
                                } else if ('|' == actualChar) {
                                    if ((direction == N && Arrays.asList('|', 'L', 'J', 'S').contains(startChar)) || (direction == S && Arrays.asList('|', 'F', '7', 'S').contains(startChar))) {
                                        newStartingPoints.add(toCheck);
                                        alreadyVisited.add(toCheck);
                                        bornerPoint.add(toCheck);
                                        felfelePoint.add(toCheck);
                                    }
                                } else if ('-' == actualChar) {
                                    if ((direction == E && Arrays.asList('-', 'L', 'F', 'S').contains(startChar)) || (direction == W && Arrays.asList('-', 'J', '7', 'S').contains(startChar))) {
                                        newStartingPoints.add(toCheck);
                                        alreadyVisited.add(toCheck);
                                        bornerPoint.add(toCheck);
                                        jobbraPoint.add(toCheck);
                                    }
                                } else if ('L' == actualChar) {
                                    if ((direction == S && Arrays.asList('|', 'F', '7', 'S').contains(startChar)) || (direction == W && Arrays.asList('-', 'J', '7', 'S').contains(startChar))) {
                                        newStartingPoints.add(toCheck);
                                        alreadyVisited.add(toCheck);
                                        bornerPoint.add(toCheck);
                                        felfelePoint.add(toCheck);
                                    }
                                } else if ('J' == actualChar) {
                                    if ((direction == S && Arrays.asList('|', 'F', '7', 'S').contains(startChar)) || (direction == E && Arrays.asList('-', 'L', 'F', 'S').contains(startChar))) {
                                        newStartingPoints.add(toCheck);
                                        alreadyVisited.add(toCheck);
                                        bornerPoint.add(toCheck);
                                        jobbraPoint.add(toCheck);
                                        felfelePoint.add(toCheck);
                                    }
                                } else if ('7' == actualChar) {
                                    if ((direction == N && Arrays.asList('|', 'L', 'J', 'S').contains(startChar)) || (direction == E && Arrays.asList('-', 'L', 'F', 'S').contains(startChar))) {
                                        newStartingPoints.add(toCheck);
                                        alreadyVisited.add(toCheck);
                                        bornerPoint.add(toCheck);
                                        jobbraPoint.add(toCheck);
                                    }
                                } else if ('F' == actualChar) {
                                    if ((direction == N && Arrays.asList('|', 'L', 'J', 'S').contains(startChar)) || (direction == W && Arrays.asList('-', 'J', '7', 'S').contains(startChar))) {
                                        newStartingPoints.add(toCheck);
                                        alreadyVisited.add(toCheck);
                                        bornerPoint.add(toCheck);

                                    }
                                } else {
                                    throw new RuntimeException("Nemm kellene itt lennie");
                                }

                            }
                    )
            );
            if (newStartingPoints.size() == 1) {
                System.out.println();
            }
            if (newStartingPoints.size() == 0) {
                boucle = lepes - 1;
                break;
            }
            startingPoints.clear();
            startingPoints.addAll(newStartingPoints);
            newStartingPoints.clear();

        }

        int counterInside = 0;

        for (int j = 0; j < lines.size(); j++) {
            int felfele = 0;
            String line = lines.get(j);
            for (int i = line.length() - 1; 0 <= i; i--) {
                Point pointToCheck = new Point(j, i);
                if (felfelePoint.contains(pointToCheck)) {
                    continue;
                } else if (bornerPoint.contains(pointToCheck)) {
                    continue;
                }

                for (int k = pointToCheck.x - 1; 0 <= k; k--) {
                    Point pointToCkeck = new Point(pointToCheck.y, k);
                    if (felfelePoint.contains(pointToCkeck)) {
                        felfele++;
                    }
                }
                if (felfele % 2 == 1) {
                    counterInside++;
                }
                felfele = 0;
            }
        }

        int counterJobbra = 0;
        for (int i = 0; i < lines.get(0).length(); i++) {
            for (int j = lines.size() - 1; 0 <= j; j--) {
                int jobbra = 0;
                Point pointToCheck = new Point(j, i);
                if (jobbraPoint.contains(pointToCheck)) {
                    continue;
                } else if (bornerPoint.contains(pointToCheck)) {
                    continue;
                }

                for (int k = pointToCheck.y - 1; 0 <= k; k--) {
                    Point pointToCkeck = new Point(k, i);
                    if (jobbraPoint.contains(pointToCkeck)) {
                        jobbra++;
                    }
                }
                if (jobbra % 2 == 1) {
                    counterJobbra++;
                }
            }
        }


        System.out.println("Steps: " + boucle);
        System.out.println("Point Inside : " + Math.min(counterInside, counterJobbra));
    }

    record Point(int y, int x) {

        Point add(Point p) {
            return new Point(p.y + y, p.x + x);
        }
    }


}
