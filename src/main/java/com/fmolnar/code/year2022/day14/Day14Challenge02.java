package com.fmolnar.code.year2022.day14;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day14Challenge02 {

    int maxX = 0;
    int maxY = 0;

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day14/input.txt");

        List<List<Point>> pointers = new ArrayList<>();
        for (String line : lines) {
            String[] points = line.split("->");
            pointers.add(Arrays.stream(points).map(String::trim).map(s -> createPoint(s)).collect(Collectors.toList()));
        }

        maxX = pointers.stream().flatMap(List::stream).mapToInt(Point::x).max().getAsInt();
        maxX = maxX + 500;

        maxY = pointers.stream().flatMap(List::stream).mapToInt(Point::y).max().getAsInt();
        maxY = maxY + 2;

        int[][] matrix = new int[maxX][maxY + 1];

        for (List<Point> points : pointers) {
            for (int p = 0; p < points.size() - 1; p++) {
                matrix = drawBetween2Points(points.get(p), points.get(p + 1), matrix);
            }
        }
        matrix = drawBetween2Points(new Point(0, maxY), new Point(maxX - 1, maxY), matrix);
        for (int i = 0; i < 1000000; i++) {
            try {
                matrix = fallRock(matrix);

            }  catch (RuntimeException exception) {
                System.out.println("Resultat (2) : " + ((int) Arrays.stream(matrix).flatMapToInt(Arrays::stream).filter(number -> number == 2).count() + 1));
                break;
            }
        }

    }

    private int[][] fallRock(int[][] matrix) {
        boolean notStopped = true;
        Point source = new Point(500, 0);
        Point actual = source;
        while (notStopped) {
            if (canGoDown(actual, matrix)) {
                matrix[actual.x][actual.y] = 0;
                matrix[actual.x][actual.y + 1] = 2;
                actual = new Point(actual.x, actual.y + 1);
                continue;
            }

            if (canStepDiagLeft(actual, matrix)) {
                matrix[actual.x][actual.y] = 0;
                matrix[actual.x - 1][actual.y + 1] = 2;
                actual = new Point(actual.x - 1, actual.y + 1);
                continue;
            }

            if (canStepDiagRight(actual, matrix)) {
                matrix[actual.x][actual.y] = 0;
                matrix[actual.x + 1][actual.y + 1] = 2;
                actual = new Point(actual.x + 1, actual.y + 1);
                continue;
            }

            notStopped = false;
            if (source == actual) {
                throw new RuntimeException("Vege");
            }
        }
        return matrix;
    }

    private boolean canStepDiagRight(Point actual, int[][] matrix) {
        return matrix[actual.x + 1][actual.y + 1] == 0;
    }

    private boolean canStepDiagLeft(Point actual, int[][] matrix) {
        return matrix[actual.x - 1][actual.y + 1] == 0;
    }

    private Boolean canGoDown(Point actual, int[][] matrix) {
        return matrix[actual.x][actual.y + 1] == 0;
    }


    int[][] drawBetween2Points(Point start, Point end, int[][] toDraw) {
        if (start.x == end.x) {
            for (int y = Math.min(start.y, end.y); y <= Math.max(start.y, end.y); y++) {
                toDraw[start.x][y] = 1;
            }
        } else if (start.y == end.y) {
            for (int x = Math.min(start.x, end.x); x <= Math.max(start.x, end.x); x++) {
                toDraw[x][start.y] = 1;
            }
        } else {
            System.out.println("Nem kellene itt lennie 1 ");
        }
        return toDraw;
    }

    public Point createPoint(String s) {
        String[] values = s.split(",");
        int x = Integer.valueOf(values[0]);
        int y = Integer.valueOf(values[1]);
        return new Point(x, y);
    }

    record Point(int x, int y) {
    }
}
