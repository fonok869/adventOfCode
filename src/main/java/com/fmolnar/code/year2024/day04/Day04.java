package com.fmolnar.code.year2024.day04;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {

    Map<PointXY, String> letters = new HashMap<>();
    int xMax, yMax;

    public void calculateDay04() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2024/day04/input.txt");

        Set<PointXY> directionsDiagonal = Set.of(
                new PointXY(1, 1),
                new PointXY(1, -1),
                new PointXY(-1, -1),
                new PointXY(-1, 1));

        Set<PointXY> directionsNormals = Set.of(
                new PointXY(0, 1),
                new PointXY(0, -1),
                new PointXY(1, 0),
                new PointXY(-1, 0));

        yMax = lines.size();
        xMax = lines.get(0).length();

        letters = AdventOfCodeUtils.getMapStringInput(lines);

        System.out.println("First : " + getCounterExercice1(Stream.of(directionsDiagonal, directionsNormals).flatMap(Collection::stream).collect(Collectors.toSet())));
        System.out.println("Second : " + getCounterExercice2(directionsDiagonal));
    }

    private int getCounterExercice1(Set<PointXY> collect) {
        int counterExercice = 0;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                for (PointXY direction : collect) {
                    String firstLetter = letters.get(new PointXY(x, y));
                    if (firstLetter.equals("X") &&
                            "M".equals(letters.get(new PointXY(x + direction.x(), direction.y() + y)))
                            && "A".equals(letters.get(new PointXY(x + direction.x() * 2, direction.y() * 2 + y)))
                            && "S".equals(letters.get(new PointXY(x + direction.x() * 3, direction.y() * 3 + y)))
                    ) {
                        counterExercice++;

                    }
                }
            }
        }
        return counterExercice;
    }

    private int getCounterExercice2(Set<PointXY> directionsDiagonal) {
        int counterExercice = 0;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                int countMas = 0;
                for (PointXY direction : directionsDiagonal) {
                    String firstLetter = letters.get(new PointXY(x, y));
                    if (firstLetter.equals("A") && "M".equals(letters.get(new PointXY(x + direction.x(), direction.y() + y)))
                            && "S".equals(letters.get(new PointXY(x + direction.x() * -1, direction.y() * -1 + y)))) {
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

;
