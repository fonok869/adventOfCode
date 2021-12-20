package com.fmolnar.code.year2021.day11;

import com.fmolnar.code.year2021.day09.Day09Challenge01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day11Challenge01 {

    private List<String> lines = new ArrayList<>();
    List<List<Integer>> all = new ArrayList<>();
    List<Point> directions = Arrays.asList(
            new Point(-1, 0),
            new Point(-1, -1),
            new Point(0, -1),
            new Point(1, -1),
            new Point(1, 0),
            new Point(1, 1),
            new Point(0, 1),
            new Point(-1, 1)
    );


    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day11/input.txt");
        try (BufferedReader file = new BufferedReader(new InputStreamReader(reader));) {
            List<Integer> numbers = new ArrayList<>();
            String line;
            while ((line = file.readLine()) != null) {
                if (line.length() != 0) {
                    for (int i = 0; i < line.length(); i++) {
                        if (i == line.length() - 1) {
                            numbers.add(Integer.valueOf(line.substring(i)));
                        } else {
                            numbers.add(Integer.valueOf(line.substring(i, i + 1)));
                        }
                    }
                    all.add(numbers);
                    numbers = new ArrayList<>();
                }
            }
        }

        int[][] matrix = transformToMatrix();
        int step = 2000;
        List<Integer> counter = new ArrayList<>();
        List<Point> propagateFlash = new ArrayList<>();
        for (int i = 0; i < step; i++) {
            matrix = calculateMatrix(matrix, counter, propagateFlash);
            if (flashedAll(matrix)) {
                System.out.println("Step: " + i);
                break;
            }
        }

//        int counter = 0;
//        for(int i=0; i<matrix.length; i++){
//            for(int j=0; j<matrix[0].length;j++){
//                if(matrix[i][j] == 0){
//                    counter++;
//                }
//            }
//        }


        System.out.println("Counter: " + counter.stream().mapToInt(s -> s).sum());
    }

    private boolean flashedAll(int[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j]!=0){
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] calculateMatrix(int[][] matrix, List<Integer> counter, List<Point> propagateFlash) {
        int xMax = all.size();
        int jMax = all.get(0).size();
        Set<Point> already0 = new HashSet<>();
        for (int i = 0; i < xMax; i++) {
            for (int j = 0; j < jMax; j++) {
                Set<Point> origins = new HashSet<Point>();
                Point origin = new Point(i, j);
                if (already0.contains(origin)) {
                    continue;
                }
                int actual = matrix[i][j] + 1;
                matrix[i][j] = actual;
                // flash
                if (9 < actual) {
                    matrix[i][j] = 0;
                    already0.add(origin);
                    origins.add(origin);
                    counter.add(1);
                    propagateFlash(i, j, matrix, origin, origins, propagateFlash, already0, counter);
                }

            }
        }
        return normalize(matrix, counter);
    }

    private void print(int[][] matrix) {
        int xMax = all.size();
        int jMax = all.get(0).size();
        System.out.println("---------------------------------");
        for (int i = 0; i < xMax; i++) {
            String line = "";
            for (int j = 0; j < jMax; j++) {

                line = line + matrix[i][j];
            }
            System.out.println(line);
        }
    }


    private int[][] normalize(int[][] matrix, List<Integer> counter) {
        int xMax = all.size();
        int jMax = all.get(0).size();
        int[][] newMatrix = new int[xMax][jMax];
        System.out.println("---------------------------------");
        for (int i = 0; i < xMax; i++) {
            String line = "";
            for (int j = 0; j < jMax; j++) {
                if (matrix[i][j] > 9) {
                    newMatrix[i][j] = 0;
                } else {
                    newMatrix[i][j] = matrix[i][j];
                }
                line = line + newMatrix[i][j];
            }
            System.out.println(line);
        }
        return newMatrix;
    }

    private void propagateFlash(int i, int j, int[][] matrix, Point origin, Set<Point> origins, List<Point> propagateFlash, Set<Point> already0, List<Integer> counter) {
        for (Point direction : directions) {
            int x1 = i + direction.x;
            int y1 = j + direction.y;
            Point pointActual = new Point(x1, y1);
            if ((x1 == origin.x && y1 == origin.y) || x1 < 0 || y1 < 0 || matrix.length <= x1 || matrix[0].length <= y1 || origins.contains(pointActual)) {
                continue;
            }
            propagateFlash.add(pointActual);
            int actualValue = matrix[x1][y1] + 1;
            if (already0.contains(pointActual)) {
                actualValue = 0;
            }
            matrix[x1][y1] = actualValue;
            //print(matrix);
            if (9 < actualValue) {
                matrix[x1][y1] = 0;
                counter.add(1);
                already0.add(pointActual);
                origins.add(pointActual);
                propagateFlash(x1, y1, matrix, pointActual, origins, propagateFlash, already0, counter);
            }
        }
    }

    private int[][] transformToMatrix() {
        int[][] newMatrix = new int[all.size()][all.get(0).size()];
        for (int i = 0; i < all.size(); i++) {
            for (int j = 0; j < all.get(0).size(); j++) {
                newMatrix[i][j] = all.get(i).get(j);
            }
        }
        return newMatrix;
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
