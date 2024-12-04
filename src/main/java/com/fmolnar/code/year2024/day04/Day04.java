package com.fmolnar.code.year2024.day04;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {

    Map<Point, String> letters = new HashMap<>();
    int xMax, yMax;

    public void calculateDay04() throws IOException {
        List<String> lines = FileReaderUtils.readFile("/2024/day04/input.txt");

        Set<Point> directionsDiagonal = Set.of(
                new Point(1, 1),
                new Point(1, -1),
                new Point(-1, -1),
                new Point(-1, 1));

        Set<Point> directionsNormals = Set.of(
                new Point(0, 1),
                new Point(0, -1),
                new Point(1, 0),
                new Point(-1, 0));

        yMax = lines.size();
        xMax = lines.get(0).length();

        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            for (int x = 0; x < line.length(); x++) {
                letters.put(new Point(x, index), String.valueOf(line.charAt(x)));
            }
        }

        System.out.println("First : " + getCounterExercice1(Stream.of(directionsDiagonal, directionsNormals).flatMap(Collection::stream).collect(Collectors.toSet())));
        System.out.println("Second : " + getCounterExercice2(directionsDiagonal));
    }

    private int getCounterExercice1(Set<Point> collect) {
        int counterExercice = 0;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                for (Point direction : collect) {
                    String firstLetter = letters.get(new Point(x, y));
                    if (firstLetter.equals("X") &&
                            "M".equals(letters.get(new Point(x + direction.x(), direction.y() + y)))
                            && "A".equals(letters.get(new Point(x + direction.x() * 2, direction.y() * 2 + y)))
                            && "S".equals(letters.get(new Point(x + direction.x() * 3, direction.y() * 3 + y)))
                    ) {
                        counterExercice++;

                    }
                }
            }
        }
        return counterExercice;
    }

    private int getCounterExercice2(Set<Point> directionsDiagonal) {
        int counterExercice = 0;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                int countMas = 0;
                for (Point direction : directionsDiagonal) {
                    String firstLetter = letters.get(new Point(x, y));
                    if (firstLetter.equals("A") && "M".equals(letters.get(new Point(x + direction.x(), direction.y() + y)))
                            && "S".equals(letters.get(new Point(x + direction.x() * -1, direction.y() * -1 + y)))) {
                        countMas++;
                    }
                }
                if (countMas > 1) {
                    counterExercice++;
                }
            }
        }
        return counterExercice;
    }
}

record Point(int x, int y) {
}

;
