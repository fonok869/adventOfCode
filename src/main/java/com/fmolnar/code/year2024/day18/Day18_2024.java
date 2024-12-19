package com.fmolnar.code.year2024.day18;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day18_2024 {
    int maxX = 71;
    int maxY = 71;

    public void calculateDay18_2024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day18/input.txt");
        PriorityQueue<Poids> priorityQueue = new PriorityQueue<>((p1, p2) -> {
            if (p1.distance() < p2.distance()) {
                return -1;
            } else if (p1.distance() > p2.distance()) {
                return 1;
            }
            return 0;
        });
        Map<PointXY, Integer> pointsWithDistance = new HashMap<>();


        Set<PointXY> falak = new HashSet<>();
        boolean firstPass = false;
        boolean found = true;
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            int vesszo = line.indexOf(',');
            PointXY actualFal = new PointXY(Integer.valueOf(line.substring(0, vesszo)), Integer.valueOf(line.substring(vesszo + 1)));
            falak.add(actualFal);
            if (index == 1024) {
                initDisjktra(priorityQueue, pointsWithDistance, falak);
                found = doDijkstra(priorityQueue, pointsWithDistance, falak);
            }
            if (1024 < index) {
                priorityQueue.clear();
                pointsWithDistance.clear();
                initDisjktra(priorityQueue, pointsWithDistance, falak);
                found = doDijkstra(priorityQueue, pointsWithDistance, falak);
                if (!found) {
                    System.out.println(line);
                    System.out.println(index);
                    break;
                }
            }
        }

        initDisjktra(priorityQueue, pointsWithDistance, falak);

        doDijkstra(priorityQueue, pointsWithDistance, falak);
    }

    private boolean doDijkstra(PriorityQueue<Poids> priorityQueue, Map<PointXY, Integer> pointsWithDistance, Set<PointXY> falak) {
        Set<PointXY> visited = new HashSet<>();

        boolean reached = false;
        while (!priorityQueue.isEmpty()) {
            Poids current = priorityQueue.remove();

            if (new PointXY(maxX - 1, maxY - 1).equals(current.p())) {
                reached = true;
                System.out.println("Distance: " + current.distance());
                break;
            }

            if (visited.contains(current.p())) {
                continue;
            }

            //System.out.println(current);
            visited.add(current.p());

            disjktraCalcul(current, visited, priorityQueue, falak, pointsWithDistance);

        }

        if (reached == false) {
            return false;
        }
        return true;

        //printOut(maxY, maxY, visited, falak);


    }

    private static void printOut(int maxY, int maxX, Set<PointXY> keySetActual, Set<PointXY> falak) {
        for (int j = 0; j < maxY; j++) {
            for (int x = 0; x < maxX * 2; x++) {
                if (keySetActual.contains(new PointXY(x, j))) {
                    System.out.print("O");
                } else if (falak.contains(new PointXY(x, j))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void disjktraCalcul(Poids current, Set<PointXY> visited, PriorityQueue<Poids> priorityQueue, Set<PointXY> falak, Map<PointXY, Integer> pointsWithDistance) {


        for (PointXY direction : AdventOfCodeUtils.directionsNormals) {
            PointXY pointTocheck = current.p().add(direction);
            if (pointsWithDistance.keySet().contains(pointTocheck)) {
                int newDistance = current.distance() + 1;
                if (newDistance <= pointsWithDistance.get(pointTocheck)) {
                    pointsWithDistance.put(pointTocheck, newDistance);
                    priorityQueue.add(new Poids(pointTocheck, newDistance));
                }
            }
        }
    }

    private void initDisjktra(PriorityQueue<Poids> priorityQueue, Map<PointXY, Integer> pointsWithDistance, Set<PointXY> falak) {
        priorityQueue.add(new Poids(new PointXY(0, 0), 0));
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                if (!falak.contains(new PointXY(x, y))) {
                    pointsWithDistance.put(new PointXY(x, y), Integer.MAX_VALUE);
                }
            }
        }
    }
}

record Poids(PointXY p, int distance) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poids poids = (Poids) o;
        return Objects.equals(p, poids.p);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(p);
    }
}
