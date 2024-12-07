package com.fmolnar.code.year2023.day03;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day03v2 {

    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day03/input.txt");
        List<Long> valids = new ArrayList<>();
        List<Point> directions = initdirections();
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if (lines.get(i).charAt(j) == '*') {
                    Set<Long> numbers = checkIfas2Numbers(i, j, lines, directions);
                    if (numbers.size() == 2) {
                        valids.add(numbers.stream().reduce(1l, (a, b) -> a * b));
                    }
                }
            }
        }

        System.out.println("Valid : " + valids.stream().mapToLong(s -> s).sum());
    }


    private static Set<Long> checkIfas2Numbers(int i, int j, List<String> lines, List<Point> directions) {
        int maxX = lines.get(0).length();
        int maxY = lines.size();
        Set<Long> numbers = new HashSet<>();

        for (Point direction : directions) {
            Point pointToCheck = new Point(i + direction.y, j + direction.x);
            if (0 <= pointToCheck.x && pointToCheck.x < maxX
                    && 0 <= pointToCheck.y && pointToCheck.y < maxY) {
                Character letter = lines.get(pointToCheck.y).charAt(pointToCheck.x);

                if (Character.isDigit(letter)) {
                    numbers.add(getDigitNumber(pointToCheck, lines));
                }

            }
        }
        return numbers;

    }

    private static Long getDigitNumber(Point pointToCheck, List<String> lines) {
        int maxX = lines.get(0).length();
        int begin = pointToCheck.x;
        int end = pointToCheck.x;
        // -1 X direction
        for (int p1 = pointToCheck.x; Math.max(0, pointToCheck.x - 3) <= p1; p1--) {
            if (Character.isDigit(lines.get(pointToCheck.y).charAt(p1))) {
                begin = p1;
            } else {
                break;
            }
        }

        for (int p2 = pointToCheck.x; p2 < Math.min(maxX, pointToCheck.x + 3); p2++) {
            if (Character.isDigit(lines.get(pointToCheck.y).charAt(p2))) {
                end = p2;
            } else {
                break;
            }
        }

        return Long.valueOf(lines.get(pointToCheck.y).substring(begin, end + 1));
    }


    private static List<Point> initdirections() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(-1, -1));
        points.add(new Point(-1, 0));
        points.add(new Point(-1, 1));
        points.add(new Point(0, -1));
        points.add(new Point(0, 1));
        points.add(new Point(1, -1));
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));
        return points;
    }

    public record Point(int y, int x) {
    }
}
