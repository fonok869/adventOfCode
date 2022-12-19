package com.fmolnar.code.year2022.day18;

import com.fmolnar.code.FileReaderUtils;
import com.fmolnar.code.year2022.aoc.PrintUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day18 {

    public void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2022/day18/input.txt");
        List<Point> points = new ArrayList<>();
        List<Integer> allX = new ArrayList<>();
        List<Integer> allY = new ArrayList<>();
        List<Integer> allZ = new ArrayList<>();
        for (String line : lines) {
            String[] strings = line.split(",");
            points.add(new Point(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2])));
            allX.add(Integer.valueOf(strings[0]));
            allY.add(Integer.valueOf(strings[1]));
            allZ.add(Integer.valueOf(strings[2]));
        }


        List<Point> allPointscopy = new ArrayList<>(points);


        int maxX = allX.stream().mapToInt(s -> s).max().getAsInt();
        int minX = allX.stream().mapToInt(s -> s).min().getAsInt();
        int maxY = allY.stream().mapToInt(s -> s).max().getAsInt();
        int minY = allY.stream().mapToInt(s -> s).min().getAsInt();
        int maxZ = allZ.stream().mapToInt(s -> s).max().getAsInt();
        int minZ = allZ.stream().mapToInt(s -> s).min().getAsInt();

        int[][][] matrix = new int[maxX - minX + 1 + 2][maxY - minY + 1 + 2][maxZ - minZ + 1 + 2];

        List<Point> allDirections = getAllDirections();

        for (Point point : allPointscopy) {
            matrix[point.x - minX + 1][point.y - minY + 1][point.z - minZ + 1] = 1;
        }


        for (int y = 0; y < matrix[0].length; y++) {
            for (int z = 0; z < matrix[0][0].length; z++) {
                matrix[0][y][z] = 2;
            }
        }


        for (int szmaszor = 0; szmaszor < 20; szmaszor++) {
            for (int x = 0; x < matrix.length; x++) {
                for (int y = 0; y < matrix[0].length; y++) {
                    for (int z = 0; z < matrix[0][0].length; z++) {
                        if (matrix[x][y][z] == 0) {
                            Point actual = new Point(x, y, z);
                            for (Point direction : allDirections) {
                                Point tocheck = addTwoPoint(actual, direction);
                                if (isInsideMatrix(matrix, tocheck)) {
                                    if (matrix[tocheck.x][tocheck.y][tocheck.z] == 2) {
                                        matrix[x][y][z] = 2;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int x = 0; x < matrix.length; x++) {
            System.out.println("i: " + x);
            PrintUtils.showMatrix(matrix[x]);
        }

        int i=0;
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    if (matrix[x][y][z] == 1) {
                        Point actual = new Point(x, y, z);
                        for (Point direction : allDirections) {
                            Point tocheck = addTwoPoint(actual, direction);
                            if (isInsideMatrix(matrix, tocheck)) {
                                if (matrix[tocheck.x][tocheck.y][tocheck.z] == 2) {
                                    i++;
                                }
                            }
                        }

                    }
                }
            }
        }

        System.out.println("Max X: " + allX.stream().mapToInt(s -> s).max().getAsInt());
        System.out.println("Min X: " + allX.stream().mapToInt(s -> s).min().getAsInt());
        System.out.println("Max Y: " + allY.stream().mapToInt(s -> s).max().getAsInt());
        System.out.println("Min Y: " + allY.stream().mapToInt(s -> s).min().getAsInt());
        System.out.println("Max Z: " + allZ.stream().mapToInt(s -> s).max().getAsInt());
        System.out.println("Min Z: " + allZ.stream().mapToInt(s -> s).min().getAsInt());

        long all = calculateAllsurface(points);
        System.out.println("Totot: " + i);
    }

    private boolean isInsideMatrix(int[][][] matrix, Point tocheck) {
        return 0 <= tocheck.x && tocheck.x < matrix.length && 0 <= tocheck.y && tocheck.y < matrix[0].length && 0 <= tocheck.z && tocheck.z < matrix[0][0].length;
    }

    private Point addTwoPoint(Point p1, Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }

    private List<Point> getAllDirections() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0, 1));
        points.add(new Point(0, 0, -1));
        points.add(new Point(1, 0, 0));
        points.add(new Point(-1, 0, 0));
        points.add(new Point(0, 1, 0));
        points.add(new Point(0, -1, 0));
        return points;
    }

    private long calculateAllsurface(List<Point> points) {
        List<Point> allPointscopy = new ArrayList<>(points);
        List<Point> allPointscopy2 = new ArrayList<>(points);
        long all = allPointscopy.size() * 6;
        for (Point p : allPointscopy) {
            allPointscopy2.remove(p);
            for (Point p2 : allPointscopy2) {
                if (p.isConnected(p2)) {
                    all -= 2;
                }
            }

        }
        return all;
    }

    record Point(int x, int y, int z) {
        boolean isConnected(Point pointToCheck) {
            return (Math.abs(pointToCheck.x - x) + Math.abs(pointToCheck.y - y) + Math.abs(pointToCheck.z - z)) == 1;
        }
    }

    ;
}
