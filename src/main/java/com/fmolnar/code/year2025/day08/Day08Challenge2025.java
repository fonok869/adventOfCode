package com.fmolnar.code.year2025.day08;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day08Challenge2025 {
    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day08/input.txt");

        List<PointXYZ> points = new ArrayList<>();
        for (String line : lines) {
            String[] lineNumbers = line.split(",");
            points.add(new PointXYZ(Long.parseLong(lineNumbers[0]), Long.parseLong(lineNumbers[1]), Long.parseLong(lineNumbers[2])));
        }
        ;

        List<PointXYZ> points2 = new ArrayList<>(points);
        List<PointXYZ> points3 = new ArrayList<>(points);
        Map<Long, Set<Distance>> distances = new HashMap<>();

        for (int index = 0; index < points2.size(); index++) {
            PointXYZ pointActual = points2.get(index);
            for (int index2 = index + 1; index2 < points3.size(); index2++) {
                PointXYZ pointActual2 = points3.get(index2);
                long distance = pointActual.getDistance(pointActual2);
                distances.putIfAbsent(distance, new HashSet<>());
                distances.get(distance).add(new Distance(pointActual, pointActual2));
            }
        }
        List<Long> ordered = distances.keySet().stream().sorted().toList();
        List<Set<PointXYZ>> circuits = new ArrayList<>();
        int shortest = 10;
        int counter = 1;
        outerloop:
        for (Long distanceToSearchFor : ordered) {

            Set<Distance> distance = distances.get(distanceToSearchFor);
            if (1 < distance.size()) {
                System.err.println("Nagyobb 2-nel");
            }
            Distance next = distance.iterator().next();
            if (circuits.isEmpty()) {
                HashSet<PointXYZ> pointsToAdd = new HashSet<>();
                pointsToAdd.add(next.first());
                pointsToAdd.add(next.second());
                circuits.add(pointsToAdd);
            } else {
                boolean found = false;
                int firstNumber = -1;
                int secondNumber = -1;
                for (int i = 0; i < circuits.size(); i++) {
                    // kulon meg kell nezni mi van
                    if (circuits.get(i).contains(next.first())) {
                        firstNumber = i;
                    } else if (circuits.get(i).contains(next.second())) {
                        secondNumber = i;
                    }
                }
                if (firstNumber == -1 && secondNumber == -1) {
                    HashSet<PointXYZ> pointsToAdd = new HashSet<>();
                    pointsToAdd.add(next.first());
                    pointsToAdd.add(next.second());
                    circuits.add(pointsToAdd);
                } else if (firstNumber != -1 && secondNumber == -1) {
                    circuits.get(firstNumber).add(next.second());
                } else if (secondNumber != -1 && firstNumber == -1) {
                    circuits.get(secondNumber).add(next.first());
                } else if (firstNumber != -1 && secondNumber != -1) {
                    if (firstNumber == secondNumber) {
                        // semmit sem csinalunk
                    }
                    circuits.get(firstNumber).addAll(circuits.get(secondNumber));
                    circuits.remove(secondNumber);
                } else {
                    System.err.println("Itt nem kellene lennem");
                }
                if (circuits.size() == 1 && circuits.get(0).size() == points.size()) {
                    System.out.println("Second resultn: " + next.first().x() * next.second().x());
                    break outerloop;
                }
                // masikban es ugyaabban
                // ket kulonbozoben
            }
            //plotCircuits(circuits, counter);
            if (shortest == counter++) {
                List<Integer> sortedVErsion = circuits.stream().mapToInt(Set::size).boxed().sorted(Comparator.reverseOrder()).toList();
                long szorzat = Long.valueOf(sortedVErsion.get(0)) * Long.valueOf(sortedVErsion.get(1)) * Long.valueOf(sortedVErsion.get(2));
                System.out.println("First result: " + szorzat);

            }

        }
    }

}

record PointXYZ(long x, long y, long z) {

    public long getDistance(PointXYZ p) {
        return (x - p.x) * (x - p.x) + (y - p.y) * (y - p.y) + (z - p.z) * (z - p.z);
    }
}

record Distance(PointXYZ first, PointXYZ second) {

}
