package com.fmolnar.code.year2022.day18;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day18 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2022/day18/input.txt");
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

        // making a 3D matrix which is 1 unit larger in every direction
        int[][][] matrix = new int[maxX - minX + 1 + 2][maxY - minY + 1 + 2][maxZ - minZ + 1 + 2];

        List<Point> allDirections = getAllDirections();

        for (Point point : allPointscopy) {
            matrix[point.x - minX + 1][point.y - minY + 1][point.z - minZ + 1] = 1;
        }


        // Start to flood the outer side by 2
        for (int y = 0; y < matrix[0].length; y++) {
            for (int z = 0; z < matrix[0][0].length; z++) {
                matrix[0][y][z] = 2;
            }
        }

        // Has to iterate as many as the length to ensure to fill all forms like potate as well
        for (int iteration = 0; iteration < matrix[0][0].length; iteration++) {
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

        int surfacesContactWithWater=0;
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    if (matrix[x][y][z] == 1) {
                        Point actual = new Point(x, y, z);
                        for (Point direction : allDirections) {
                            Point tocheck = addTwoPoint(actual, direction);
                            if (isInsideMatrix(matrix, tocheck)) {
                                if (matrix[tocheck.x][tocheck.y][tocheck.z] == 2) {
                                    surfacesContactWithWater++;
                                }
                            }
                        }

                    }
                }
            }
        }

        System.out.println("First: " + calculateAllsurface(points));
        System.out.println("Second: " + surfacesContactWithWater);
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
}
