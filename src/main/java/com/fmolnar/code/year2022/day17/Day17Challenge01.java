package com.fmolnar.code.year2022.day17;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17Challenge01 {

    String instructions;
    int maxX = 7;
    int maxY = 10000;
    int[][] matrix = new int[maxX][maxY];
    int maxHeight = maxY - 4;

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

        int actualGas = 0;
        for (int i = 0; i < 2022; i++) {
            actualGas = stepForm(forms.get(i % 5), actualGas);
            calculYMax();
        }
        System.out.println("Result (1): " + (maxY - (maxHeight + 4)));
    }

    private void calculYMax() {
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
        maxHeight = maxs.stream().mapToInt(s -> s).min().getAsInt() - 4;
    }

    private int stepForm(List<Point> alakzat, int actualGas) {
        Point actualPoint = new Point(2, maxHeight);
        for (int i = 0; i < 100; i++) {
            int[][] vierge = matrix;
            if (i != 0) {
                vierge = calculateVierge(actualPoint, alakzat);
            }
            // Oldalra lepes
            if ((i % 2) == 0) {
                // Balra lepes
                if (instructions.charAt(actualGas%instructions.length()) == '<') {
                    Point step = new Point(-1, 0);
                    if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                        actualPoint = addTwoPoint(actualPoint, step);
                        matrix = draw(actualPoint, alakzat, vierge);
                    } // Vege nem tud lepni
                    actualGas += 1;
                }
                // Jobbra lepes
                else if (instructions.charAt(actualGas%instructions.length()) == '>') {
                    Point step = new Point(1, 0);
                    if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                        actualPoint = addTwoPoint(actualPoint, step);
                        matrix = draw(actualPoint, alakzat, vierge);
                    } // Vege nem tud lepni
                    actualGas += 1;
                } else {
                    System.out.println("Nem kellene itt lennie 1");
                }
            }// Lefele lepes
            else {
                Point step = new Point(0, 1);
                if (canGoToDirection(alakzat, actualPoint, vierge, step)) {
                    actualPoint = addTwoPoint(actualPoint, step);
                    matrix = draw(actualPoint, alakzat, vierge);
                } // Vege nem tud lepni
                else {
                    return actualGas;
                }
            }

        }
        // aktualis Gas limit
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
