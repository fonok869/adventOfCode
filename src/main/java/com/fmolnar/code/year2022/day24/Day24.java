package com.fmolnar.code.year2022.day24;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day24 {

    int[][] lefts;
    int[][] rights;
    int[][] ups;
    int[][] downs;
    int[][] sums;
    int yMax;
    int xMax;
    Point up = new Point(-1, 0);
    Point down = new Point(1, 0);
    Point left = new Point(0, -1);
    Point right = new Point(0, 1);
    List<Point> allDirections = new ArrayList<>();

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day24/input.txt");

        yMax = lines.size();
        xMax = lines.get(0).length();

        lefts = new int[yMax][xMax];
        rights = new int[yMax][xMax];
        ups = new int[yMax][xMax];
        downs = new int[yMax][xMax];
        sums = new int[yMax][xMax];

        calculateAllDirections();

        for (int y = 0; y < yMax; y++) {
            String line = lines.get(y);
            for (int x = 0; x < xMax; x++) {
                char actual = line.charAt(x);
                if (actual == '>') {
                    rights[y][x] = 1;
                } else if (actual == '<') {
                    lefts[y][x] = 1;
                } else if (actual == '^') {
                    ups[y][x] = 1;
                } else if (actual == 'v') {
                    downs[y][x] = 1;
                } else if (actual == '#') {
                    downs[y][x] = 9;
                    ups[y][x] = 9;
                    lefts[y][x] = 9;
                    rights[y][x] = 9;
                }
            }
        }

        calculateSums();

        int xOut = -1;
        for (int x = 0; x < xMax; x++) {
            if (sums[yMax - 1][x] == 0) {
                xOut = x;
                break;
            }
        }

        final Point destination = new Point(yMax - 1, xOut);
        final Point startPoint = new Point(0, 1);
        Set<Point> pointsToCheck = new HashSet<>();
        pointsToCheck.add(startPoint);

        boolean hasReachedGoal = false;
        boolean hasReachedBack = false;

        for (int i = 0; i < 10000; i++) {
            calculOneStep();
            calculateSums();
            pointsToCheck = calculNextPossibleSteps(pointsToCheck);
            // First reached Goal
            if (!hasReachedBack && !hasReachedGoal && pointsToCheck.contains(destination)) {
                pointsToCheck = new HashSet<>();
                pointsToCheck.add(destination);
                System.out.println("First : " + (i + 1));
                hasReachedGoal = true;
            }

            if (hasReachedGoal && !hasReachedBack && pointsToCheck.contains(startPoint)) {
                pointsToCheck = new HashSet<>();
                pointsToCheck.add(startPoint);
                hasReachedBack = true;
            }

            if (hasReachedBack && hasReachedGoal && pointsToCheck.contains(destination)) {
                System.out.println("Second : " + (i + 1));
                break;
            }
        }
    }

    private void calculateAllDirections() {
        allDirections.add(new Point(0, 1));
        allDirections.add(new Point(0, -1));
        allDirections.add(new Point(1, 0));
        allDirections.add(new Point(-1, 0));
        allDirections.add(new Point(0, 0));
    }

    private Set<Point> calculNextPossibleSteps(Set<Point> pointsToCheck) {
        Set<Point> newPoints = new HashSet<>();
        pointsToCheck.forEach(p -> {
            allDirections.forEach(
                    d -> {
                        int yActual = p.y + d.y;
                        int xActual = p.x + d.x;
                        if (0 <= yActual && yActual < yMax && 0 <= xActual && xActual < xMax && sums[yActual][xActual] == 0) {
                            newPoints.add(new Point(yActual, xActual));
                        }
                    }
            );
        });

        return newPoints;
    }

    private void calculOneStep() {
        ups = calculateNewStep(up, ups);
        downs = calculateNewStep(down, downs);
        rights = calculateNewStep(right, rights);
        lefts = calculateNewStep(left, lefts);
    }

    private void calculateSums() {
        int[][] newSums = new int[yMax][xMax];
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (!(rights[y][x] == 0 && lefts[y][x] == 0 && downs[y][x] == 0 && ups[y][x] == 0)) {
                    newSums[y][x] = 1;
                }
            }
        }
        sums = newSums;
    }

    private int[][] calculateNewStep(Point direction, int[][] sums) {
        int[][] newSums = new int[yMax][xMax];
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (sums[y][x] == 9) {
                    newSums[y][x] = 9;
                } else if (sums[y][x] != 0) {
                    int xNew = edgeAdjust((x + direction.x), xMax);
                    int yNew = edgeAdjust((y + direction.y), yMax);
                    newSums[yNew][xNew] = 1;
                }
            }
        }
        return newSums;
    }

    int edgeAdjust(int xNew, int xMax) {
        if (xNew == 0) {
            xNew = xMax - 2;
        } else if (xNew == xMax - 1) {
            xNew = 1;
        }
        return xNew;
    }

    record Point(int y, int x) {
    };
}
