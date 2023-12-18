package com.fmolnar.code.year2023.day18;

import com.fmolnar.code.FileReaderUtils;
import com.fmolnar.code.year2023.day10.Day10;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2023/day18/input.txt");

        Set<Point> walls = new HashSet<>();
        Point initPoint = new Point(0, 0);
        Set<Point> felfele = new HashSet<>();
        Set<Point> jobbra = new HashSet<>();
        Point newPoint;
        for (String line : lines) {
            char letter = line.charAt(0);
            int firstEspace = line.indexOf(' ');
            int secondEspace = line.lastIndexOf(' ');
            Integer lepes = Integer.valueOf(line.substring(firstEspace + 1, secondEspace));
            if ('R' == letter) {
                for (int i = 0; i <= lepes; i++) {
                    walls.add(new Point(initPoint.y, initPoint.x + i));
                    if (i != lepes) {
                        jobbra.add(new Point(initPoint.y, initPoint.x + i));
                    }
                }
                initPoint = new Point(initPoint.y(), initPoint.x + lepes);
            } else if ('L' == letter) {
                for (int i = 0; i <= lepes; i++) {
                    walls.add(new Point(initPoint.y, initPoint.x - i));
                    if(i!=0){
                        jobbra.add(new Point(initPoint.y, initPoint.x - i));
                    }
                }
                initPoint = new Point(initPoint.y(), initPoint.x - lepes);
            } else if ('U' == letter) {
                for (int i = 0; i <= lepes; i++) {
                    walls.add(new Point(initPoint.y - i, initPoint.x));
                    if (i != lepes) {
                        felfele.add(new Point(initPoint.y - i, initPoint.x));
                    }
                }
                initPoint = new Point(initPoint.y - lepes, initPoint.x);
                // Utolsot le kell venni
            } else if ('D' == letter) {
                for (int i = 0; i <= lepes; i++) {
                    walls.add(new Point(initPoint.y + i, initPoint.x));
                    if(i!=0){
                        felfele.add(new Point(initPoint.y + i, initPoint.x));
                    }
                }
                initPoint = new Point(initPoint.y + lepes, initPoint.x);
            }
        }

        int xMin = walls.stream().mapToInt(Point::x).min().getAsInt();
        int xMax = walls.stream().mapToInt(Point::x).max().getAsInt();

        int yMin = walls.stream().mapToInt(Point::y).min().getAsInt();
        int yMax = walls.stream().mapToInt(Point::y).max().getAsInt();

        int counterInside = 0;
        Set<Point> insideUp = new HashSet<>();
        for (int j = yMin; j <= yMax; j++) {
            int felfeleNumber = 0;
            for (int i = xMin; i <= xMax; i++) {
                Point pointToCheck = new Point(j, i);
                if (walls.contains(pointToCheck)) {
                    continue;
                }

                for (int k = pointToCheck.x; xMin <= k; k--) {
                    Point pointToCheckInside = new Point(pointToCheck.y, k);
                    if (felfele.contains(pointToCheckInside)) {
                        felfeleNumber++;
                    }
                }
                if (felfeleNumber % 2 == 1) {
                    counterInside++;
                    insideUp.add(pointToCheck);
                }
                felfeleNumber = 0;
            }

        }


        int counterInsideJobbra = 0;
        Set<Point> insideJobbra = new HashSet<>();
        for (int i = xMin; i <= xMax; i++) {
            int jobbraNumber = 0;
            for (int j = yMin; j <= yMax; j++) {
                Point pointToCheck = new Point(j, i);
                if (walls.contains(pointToCheck)) {
                    continue;
                }

                for (int k = pointToCheck.y; yMin <= k; k--) {
                    Point pointToCheckInside = new Point(k, pointToCheck.x);
                    if (jobbra.contains(pointToCheckInside)) {
                        jobbraNumber++;
                    }
                }
                if (jobbraNumber % 2 == 1) {
                    counterInsideJobbra++;
                    insideJobbra.add(pointToCheck);
                }
                jobbraNumber = 0;
            }

        }
        System.out.println("CounterInsideFelfele: " + counterInside + " " + felfele.size());
        System.out.println("CounterInsideJobbra: " + counterInsideJobbra + " " + jobbra.size());
        int sumTotal = walls.size() + Math.min(counterInside, counterInsideJobbra);
        System.out.println("Eredmeny: " + sumTotal);

    }

    record Point(int y, int x) {
    }
}
