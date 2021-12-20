package com.fmolnar.code.year2021.day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day09Challenge01 {

    private List<String> lines = new ArrayList<>();
    List<List<Integer>> all = new ArrayList<>();
    List<Point> locMins = new ArrayList<>();

    public void calculate() throws IOException {
        InputStream reader = getClass().getResourceAsStream("/2021/day09/input.txt");

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
        calcul(all);
    }

    private void calcul(List<List<Integer>> all) {
        List<Point> pointsToCheck = Arrays.asList(new Point(0, 1), new Point(0, -1), new Point(1, 0), new Point(-1, 0));
        int counter = 0;
        int verticalSize = all.size();
        int horizontalSize = all.get(0).size();
        for (int fuggoleges = 0; fuggoleges < verticalSize; fuggoleges++) {
            for (int vizszintes = 0; vizszintes < horizontalSize; vizszintes++) {
                counter += checkIfLocMin(pointsToCheck, fuggoleges, vizszintes);
            }
        }
        System.out.println("Counter: " + counter);

    }

    private int checkIfLocMin(List<Point> pointsToCheck, int fuggoleges, int vizszintes) {
        int numberToCheck = all.get(fuggoleges).get(vizszintes);
        boolean isLocMin = true;
        for (Point point : pointsToCheck) {
            int y1 = point.y + fuggoleges;
            int x1 = point.x + vizszintes;
            if (0 <= y1 && y1< all.size() && 0 <= x1 && x1< all.get(0).size()){
                if(numberToCheck < all.get(y1).get(x1)){

                }
                else {
                    return 0;
                }
            }
        }
        locMins.add(new Point(vizszintes, fuggoleges));
        return isLocMin ? numberToCheck+1 : 0;
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
