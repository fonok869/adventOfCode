package com.fmolnar.code.year2024.day20;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day20_2024 {

    PointXY START;
    PointXY END;

    public void calculateDay20_2024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day20/input.txt");

        long initTime = System.currentTimeMillis();
        Map<PointXY, String> pointsMap = AdventOfCodeUtils.getMapStringInput(lines);

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == 'S') {
                    START = new PointXY(x, y);
                } else if (line.charAt(x) == 'E') {
                    END = new PointXY(x, y);
                }
            }
        }


        Set<PointXY> alreadyVisited = new HashSet<>();

        PointXY startPoint = START;
        List<PointWithDirection> pointWithDirectionList = new ArrayList<PointWithDirection>();

        PointXY nextPoint = null;

        while (true) {

            for (PointXY direction : AdventOfCodeUtils.directionsNormals) {
                nextPoint = startPoint.add(direction);
                if (alreadyVisited.contains(nextPoint)) {
                    continue;
                } else if (pointsMap.containsKey(nextPoint) && Set.of(".", "E").contains(pointsMap.get(nextPoint))) {
                    pointWithDirectionList.add(new PointWithDirection(startPoint, direction));
                    alreadyVisited.add(startPoint);
                    break;
                }
            }
            if (nextPoint.equals(END)) {
                alreadyVisited.add(END);
                pointWithDirectionList.add(new PointWithDirection(END, new PointXY(0, 0)));
                break;
            }

            startPoint = nextPoint;

        }

        int minCheats = 50;

        List<PointWithDirection> toTreat = new ArrayList<>(pointWithDirectionList);
        List<PointXY> toTreatPoints = pointWithDirectionList.stream().map(PointWithDirection::point).toList();
        Set<PointXY> toTreatPointsSet = pointWithDirectionList.stream().map(PointWithDirection::point).collect(Collectors.toSet());

        int max1 = 2;
        List<Integer> firstExercice = new ArrayList<>();
        doExercice(toTreat, max1, pointsMap, toTreatPointsSet, toTreatPoints, firstExercice);
        System.out.println("First: " + AdventOfCodeUtils.getHistogramFromIntegers(firstExercice).entrySet().stream().filter(s -> minCheats <= s.getKey()).mapToInt(s -> s.getValue()).sum());
        long secondTime = System.currentTimeMillis();
        System.out.println("Time [ms]: " + (secondTime - initTime));


        int max2 = 20;
        List<Integer> secondExercice = new ArrayList<>();
        doExercice(toTreat, max2, pointsMap, toTreatPointsSet, toTreatPoints, secondExercice);
        System.out.println("Second: " + AdventOfCodeUtils.getHistogramFromIntegers(secondExercice).entrySet().stream().filter(s -> minCheats <= s.getKey()).mapToInt(s -> s.getValue()).sum());


        long thirdTime = System.currentTimeMillis();
        System.out.println("Time [ms]: " + (thirdTime - initTime));
    }

    private static void doExercice(List<PointWithDirection> toTreat, int max, Map<PointXY, String> pointsMap, Set<PointXY> toTreatPointsSet, List<PointXY> toTreatPoints, List<Integer> kulonbsegek) {
        for (int index = 0; index < toTreat.size(); index++) {
            PointWithDirection pointTocheck = toTreat.get(index);
            PointXY pointXY = pointTocheck.point();
            Map<PointXY, Integer> pointDistance = new HashMap();
            Set<PointXY> allNeighboursLehet = new HashSet<>();
            getAllPossibleCheats(pointXY, max, pointsMap, toTreatPointsSet, pointDistance, allNeighboursLehet);
            countAllCheatsGain(pointDistance, toTreatPoints, index, kulonbsegek);
        }
    }

    private static void countAllCheatsGain(Map<PointXY, Integer> pointDistance, List<PointXY> toTreatPoints, int index, List<Integer> kulonbsegek) {
        for (Map.Entry<PointXY, Integer> possible : pointDistance.entrySet()) {
            int stepPosition = toTreatPoints.indexOf(possible.getKey());
            if (index < stepPosition) {
                int stepsByCheating = possible.getValue();
                int cheatingGains = stepPosition - index - stepsByCheating;
                if (0 <= cheatingGains) {
                    kulonbsegek.add(cheatingGains);
                }
            }
        }
    }

    private static void getAllPossibleCheats(PointXY pointXY, int max, Map<PointXY, String> pointsMap, Set<PointXY> toTreatPointsSet, Map<PointXY, Integer> pointDistance, Set<PointXY> allNeighboursLehet) {
        for (int x = pointXY.x() - max; x <= pointXY.x() + max; x++) {
            for (int y = pointXY.y() - max; y <= pointXY.y() + max; y++) {
                PointXY pointXYToCheck = new PointXY(x, y);
                if (pointsMap.containsKey(pointXYToCheck) && toTreatPointsSet.contains(pointXYToCheck)) {
                    int manhattanDistance = AdventOfCodeUtils.getManhattanDistance(pointXYToCheck, pointXY);
                    if (manhattanDistance <= max) {
                        pointDistance.put(pointXYToCheck, manhattanDistance);
                        allNeighboursLehet.add(pointXYToCheck);
                    }
                }
            }
        }
    }


}

record PointWithDirection(PointXY point, PointXY direction) {
}
