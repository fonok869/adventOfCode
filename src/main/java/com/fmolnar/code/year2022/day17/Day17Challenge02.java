package com.fmolnar.code.year2022.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17Challenge02 {

    String instructions;
    int maxX = 7;
    int maxY = 200;
    int[][] matrix = new int[maxX][maxY];
    int maxHeight = maxY - 4;
    List<List<Integer>> roundsUp = new ArrayList<>();
    List<Long> maxHeights = new ArrayList<>();
    List<Long> dueReorganisation = new ArrayList<>();
    List<Long> actualGasList = new ArrayList<>();
    final int limit = 20;
    final int geosNumber = 5;

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day17/input.txt");
        List<List<Point>> forms = new ArrayList<>();
        forms.add(createLineHorizzontal());
        forms.add(createCross());
        forms.add(createL());
        forms.add(createLineVertical());
        forms.add(createSquare());

        for (String line : lines) {
            instructions = line;
        }

        int cardinality = instructions.length() * geosNumber;
        long actualGas = 0;
        for (int i = 0; i < 4 * cardinality; i++) {
            actualGas = stepForm(forms.get(i % geosNumber), actualGas);
            calculYMax(actualGas);
            if (maxHeight <= limit) {
                reorganisation();
            }
        }

        int init = 1071;
        long diffMax = 65170l;
        long diffHeight = 97812;
        long diffGasUnit = 383458l;

        loop:
        for (int i = 0; i < cardinality; i++) {
            List<Integer> l1 = roundsUp.get(i);
            for (int j = cardinality; j < 2 * cardinality; j++) {
                List<Integer> l2 = roundsUp.get(j);
                if (l1.equals(l2)) {
                    int diff = (j - i);
                    for (int k = 2 * cardinality; k < 3 * cardinality; k++) {
                        List<Integer> l3 = roundsUp.get(j);
                        if (l1.equals(l3)) {
                            int diff2 = (k - j);
                            if (diff == diff2 && diff > cardinality) {
                                if((maxHeights.get(k) - maxHeights.get(j))==(maxHeights.get(j) - maxHeights.get(i))) {
                                    if((actualGasList.get(k) - actualGasList.get(j))==(actualGasList.get(j) - actualGasList.get(i)))
                                    diffMax = k - j;
                                    diffHeight = maxHeights.get(k) - maxHeights.get(j);
                                    diffGasUnit = actualGasList.get(k) - actualGasList.get(j);
                                    init = i;
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }
        }

        long numberRocks = 1000000000000L;
        long stepped = numberRocks / Long.valueOf(diffMax);
        long rested = numberRocks - (stepped * diffMax);
        long diffGasAll = stepped * diffGasUnit;
        long heightStepped = stepped * diffHeight;


        // Init Everything
        matrix = new int[maxX][maxY];
        maxHeight = maxY - 4;
        maxHeights = new ArrayList<>();
        roundsUp = new ArrayList<>();
        dueReorganisation = new ArrayList<>();

        long actualGas2 = 0;
        for (int i = 0; i < rested; i++) {
            if (i == init) {
                actualGas2 = (actualGas2 + diffGasAll) % instructions.length();
            }
            actualGas2 = stepForm(forms.get(i % geosNumber), actualGas2);
            calculYMax(actualGas2);
            if (maxHeight <= limit) {
                reorganisation();
            }
        }
//
        System.out.println("MAxHeight: " + (maxY - (maxHeight + 4) + heightStepped + dueReorganisation.stream().mapToLong(s -> s).sum()));
    }

    private void reorganisation() {
        List<Integer> maxs = new ArrayList<>();
        for (int x = 0; x < maxX; x++) {
            int yMax = maxY;
            for (int y = maxY - 1; 0 <= y; y--) {
                if (matrix[x][y] == 1) {
                    if (y < yMax) {
                        yMax = y;
                    }
                }
            }
            maxs.add(yMax);
        }
        final int max = maxs.stream().mapToInt(s -> s).max().getAsInt();
        int diff = maxY - max - limit;

        if (diff < 0) {
            throw new RuntimeException("Can not be negative");
        }
        int[][] newMatrix = new int[maxX][maxY];
        for (int y = maxY - 1; diff <= y; y--) {
            for (int x = 0; x < maxX; x++) {
                newMatrix[x][y] = matrix[x][y - (diff)];
            }
        }
        dueReorganisation.add(Long.valueOf(diff));
        maxHeight += diff;
        matrix = newMatrix;
    }


    private void calculYMax(long actualGas) {
        List<Integer> maxs = new ArrayList<>();
        for (int x = 0; x < maxX; x++) {
            int yMax = maxY;
            for (int y = maxY - 1; 0 <= y; y--) {
                if (matrix[x][y] == 1) {
                    if (y < yMax) {
                        yMax = y;
                    }
                }
            }
            maxs.add(yMax);
        }
        final int min = maxs.stream().mapToInt(s -> s).min().getAsInt();
        final int max = maxs.stream().mapToInt(s -> s).max().getAsInt();
        maxHeight = min - 4;
        List<Integer> relatives = new ArrayList<>();
        for (int maxValue : maxs) {
            relatives.add(Math.abs(max - maxValue));
        }

        maxHeights.add((Long.valueOf(maxY - min)) + dueReorganisation.stream().mapToLong(s -> s).sum());
        actualGasList.add(actualGas);

        roundsUp.add(relatives);
    }

    private long stepForm(List<Point> alakzat, long actualGas) {
        Point actualPoint = new Point(2, maxHeight);
        for (int i = 0; i < 100; i++) {
            int[][] vierge = matrix;
            if (i != 0) {
                vierge = calculateVierge(actualPoint, alakzat);
            }
            // Lateral Step
            if ((i % 2) == 0) {

                // Left Step
                if (instructions.charAt((int) (actualGas % ((long) instructions.length()))) == '<') {
                    Point step = new Point(-1, 0);
                    if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                        actualPoint = addTwoPoint(actualPoint, step);
                        matrix = draw(actualPoint, alakzat, vierge);
                    } // Finished stucked
                    else {

                    }
                    actualGas += 1;
                }
                // Step Right
                else if (instructions.charAt((int) (actualGas % (long) instructions.length())) == '>') {
                    Point step = new Point(1, 0);
                    if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                        actualPoint = addTwoPoint(actualPoint, step);
                        matrix = draw(actualPoint, alakzat, vierge);
                    } // Finished stucked
                    else {

                    }
                    actualGas += 1;
                } else {
                    System.out.println("Can not be here 1");
                }
            }// Downwards Step
            else {
                Point step = new Point(0, 1);
                if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                    actualPoint = addTwoPoint(actualPoint, step);
                    matrix = draw(actualPoint, alakzat, vierge);
                } // Finished stucked
                else {
                    return actualGas;
                }
            }

        }
        return actualGas;

    }

    private int[][] draw(Point newPoint, List<Point> alakzat, int[][] vierge) {
        List<Point> pointsCalculated = alakzat.stream().map(s -> addTwoPoint(s, newPoint)).collect(Collectors.toList());
        for (Point pointer : pointsCalculated) {
            vierge[pointer.x][pointer.y] = 1;
        }
        return vierge;
    }


    private boolean canGoToDirection(List<Point> alakzat, Point actualPoint, int[][] vierge, Point point) {
        Point newPoint = addTwoPoint(actualPoint, point);
        List<Point> pointsCalculated = alakzat.stream().map(s -> addTwoPoint(s, newPoint)).collect(Collectors.toList());

        for (Point pointToCheck : pointsCalculated) {
            if (0 <= pointToCheck.x && pointToCheck.x < maxX && 0 <= pointToCheck.y && pointToCheck.y < maxY) {
                if (vierge[pointToCheck.x][pointToCheck.y] == 0) {
                    // we can move
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;

    }

    private int[][] calculateVierge(Point actualPoint, List<Point> points) {
        List<Point> pointsCalculated = points.stream().map(s -> addTwoPoint(s, actualPoint)).collect(Collectors.toList());
        int[][] matrixVierge = new int[maxX][maxY];
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                Point pointToCheck = new Point(x, y);
                if (!pointsCalculated.contains(pointToCheck)) {
                    matrixVierge[x][y] = matrix[x][y];
                }
            }
        }
        return matrixVierge;
    }

    Point addTwoPoint(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }


    private List<Point> createSquare() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, -1));
        points.add(new Point(1, 0));
        points.add(new Point(1, -1));
        return points;
    }

    private List<Point> createLineVertical() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, -1));
        points.add(new Point(0, -2));
        points.add(new Point(0, -3));
        return points;
    }

    private List<Point> createL() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(2, 0));
        points.add(new Point(2, -1));
        points.add(new Point(2, -2));
        return points;
    }

    private List<Point> createCross() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 0));
        points.add(new Point(0, -1));
        points.add(new Point(1, -1));
        points.add(new Point(1, -2));
        points.add(new Point(2, -1));
        return points;
    }

    private List<Point> createLineHorizzontal() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(2, 0));
        points.add(new Point(3, 0));
        return points;
    }

    record Point(int x, int y) {

    }
}
