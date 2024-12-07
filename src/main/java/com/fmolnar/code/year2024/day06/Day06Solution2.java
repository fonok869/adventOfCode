package com.fmolnar.code.year2024.day06;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.fmolnar.code.year2024.day06.Day06Solution2.Direction.N;

public class Day06Solution2 {

    public void calculateDay06_2024() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2024/day06/input.txt");

        Map<PointXY, String> mapsIntegs = AdventOfCodeUtils.getMapStringInput(lines);
        int xMax = lines.get(0).length();
        int yMax = lines.size();
        PointXY start = null;
        PointXY start2 = null;
        for (int index = 0; index < lines.size(); index++) {
            for (int j = 0; j < lines.get(index).length(); j++) {
                if (lines.get(index).charAt(j) == '^') {
                    start = new PointXY(j, index);
                }
            }
        }
        start2 = start;


        Direction direction = N;
        Set<PointXY> pointXYSet = new HashSet<>();

        extractCalculator(start, direction, mapsIntegs, pointXYSet, 10 * xMax * yMax);


        int counter2 = 0;

        for (PointXY pointToCheck : pointXYSet) {
            Direction newDirection = N;
            PointXY started = start2;
            if (pointToCheck.equals(started)) {
                continue;
            }
            Map<PointXY, String> newMap = new HashMap<>(mapsIntegs);
            newMap.put(pointToCheck, "#");
            if (checkIfStucked(started, newMap, newDirection, 10 * xMax * yMax)) {
                counter2++;
            }
        }


        System.out.println("Second: " + counter2);


    }

    private boolean checkIfStucked(PointXY started, Map<PointXY, String> newMap, Direction newDirection, int max) {

        Set<Location> directionChecker = new HashSet<>();

        directionChecker.add(new Location(started, newDirection));
        for (int i = 0; i < max; i++) {
            PointXY forwarded = new PointXY(started.x() + newDirection.x, started.y() + newDirection.y);
            if (newMap.containsKey(forwarded)) {
                if ("#".equals(newMap.get(forwarded))) {
                    newDirection = newDirection.turnRight();
                    //started = forwarded;
                } else {
                    started = forwarded;
                }
                Location actual = new Location(started, newDirection);
                // Korbement
                if (directionChecker.contains(actual)) {
                    return true;
                }
                directionChecker.add(actual);
            } else {
                return false;
            }
        }

        return false;

    }


    private boolean extractCalculator(PointXY start, Direction direction, Map<PointXY, String> mapsIntegs, Set<PointXY> pointXYSet, int max) {
        int counter = max;
        for (int i = 0; i < counter; i++) {
            // forward
            PointXY forwaed = new PointXY(start.x() + direction.x(), start.y() + direction.y());
            if (mapsIntegs.containsKey(forwaed)) {
                if (mapsIntegs.get(forwaed).equals(".") || mapsIntegs.get(forwaed).equals("^")) {
                    pointXYSet.add(forwaed);
                    start = forwaed;
                    continue;
                } else {
                    // jobbra
                    direction = direction.turnRight();
                    forwaed = new PointXY(start.x() + direction.x(), start.y() + direction.y());

                    pointXYSet.add(forwaed);
                    start = forwaed;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    record Location(PointXY pointXY, Direction direction) {
    }

    ;

    enum Direction {
        S(0, 1),
        W(-1, 0),
        N(0, -1),
        E(1, 0);

        private final int x;
        private final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int y() {
            return y;
        }


        int x() {
            return x;
        }


        public Direction turnRight() {
            return switch (this) {
                case S -> W;
                case W -> N;
                case N -> E;
                case E -> S;
            };
        }
    }
}
