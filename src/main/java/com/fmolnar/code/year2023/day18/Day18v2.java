package com.fmolnar.code.year2023.day18;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

public class Day18v2 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    // List<String> strings = Arrays.stream(toto.split(",")).collect(Collectors.toList());
    //        List<Character> chars = toto.chars().mapToObj(s->(char) s).collect(Collectors.toList());


    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day18/input.txt");

        Set<Point> csucsok = new HashSet<>();
        Point initPoint = new Point(0, 0);
        csucsok.add(initPoint);
        Set<Point> felfele = new HashSet<>();
        Set<Point> jobbra = new HashSet<>();
        long elekHosszaSum = 0;
        List<Step> steps = new ArrayList<>();
        for (String line : lines) {
            int hashtag = line.indexOf('#');
            int secondEspace = line.lastIndexOf(')');
            long actual = Long.parseLong(line.substring(hashtag + 1, secondEspace - 1), 16);
            Direction directionActual = getDirectionFrom(line.substring(secondEspace - 1, secondEspace));
//            char letter = line.charAt(0);
//            Direction directionActual = getDirectionFromLetter(letter);
//            int firstEspace = line.indexOf(' ');
//            int secondEspace = line.lastIndexOf(' ');
//            long actual = Long.valueOf(line.substring(firstEspace + 1, secondEspace));
            elekHosszaSum += actual;
            Step stepActual = new Step(initPoint, directionActual, actual);
            steps.add(stepActual);
            initPoint = stepActual.nextPoint();
        }
        List<Point> csucsokSorrendben = new ArrayList<>();
        steps.forEach(s -> csucsokSorrendben.add(s.p));

        long allArea = (long) shoelaceArea(csucsokSorrendben);
        long keresett = allArea + (elekHosszaSum/2l)+1;



        System.out.println("Toto: allArea: " + keresett);

    }

    private static Direction getDirectionFromLetter(char letter) {
        if(letter == 'D'){
            return Direction.D;
        }

        if(letter == 'U'){
            return Direction.U;
        }

        if(letter == 'L'){
            return Direction.L;
        }

        if(letter == 'R'){
            return Direction.R;
        }

        throw new RuntimeException("Cucc");
    }

    private static double shoelaceArea(List<Point> v) {
        int n = v.size();
        double a = 0l;
        for (int i = 0; i < n - 1; i++) {
            a += v.get(i).y * v.get(i + 1).x - v.get(i + 1).y * v.get(i).x;
        }
        return Math.abs(a + v.get(n - 1).y * v.get(0).x - v.get(0).y * v.get(n - 1).x) / 2.0;
    }


    private static void addWalls(Set<Point> walls, Step stepActual) {

        switch (stepActual.direction) {
            case R ->
                    LongStream.rangeClosed(stepActual.p.x, stepActual.p.x + stepActual.steps).forEach(x -> walls.add(new Point(stepActual.p.y, x)));
            case L ->
                    LongStream.rangeClosed(stepActual.p.x - stepActual.steps, stepActual.p.x).forEach(x -> walls.add(new Point(stepActual.p.y, x)));
            case U ->
                    LongStream.rangeClosed(stepActual.p.y - stepActual.steps, stepActual.p.y).forEach(y -> walls.add(new Point(y, stepActual.p.x)));
            case D ->
                    LongStream.rangeClosed(stepActual.p.y, stepActual.p.y + stepActual.steps).forEach(y -> walls.add(new Point(y, stepActual.p.x)));
        }
    }

    private static Direction getDirectionFrom(String substring) {
        Integer direction = Integer.valueOf(substring);
        switch (direction) {
            case 0 -> {
                return Direction.R;
            }
            case 1 -> {
                return Direction.D;
            }
            case 2 -> {
                return Direction.L;
            }
            case 3 -> {
                return Direction.U;
            }
        }
        throw new RuntimeException("Nincs direction.");
    }

    record Point(long y, long x) {
    }

    enum Direction {
        U,
        R,
        D,
        L;

        Point addNumber(Point init, long number) {
            switch (this) {
                case R -> {
                    return new Point(init.y, init.x + number);
                }
                case L -> {
                    return new Point(init.y, init.x - number);
                }
                case U -> {
                    return new Point(init.y - number, init.x);
                }
                case D -> {
                    return new Point(init.y + number, init.x);
                }

            }
            throw new RuntimeException("nem volt meg az irany");
        }
    }

    record Step(Point p, Direction direction, long steps) {
        Point nextPoint() {
            return direction.addNumber(p, steps);
        }
    }

    ;
}
