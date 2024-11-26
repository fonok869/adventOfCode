package com.fmolnar.code.year2021.day15;

import com.fmolnar.code.FileReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day15Practise {

    static Map<Point, Integer> halo = new HashMap<>();
    static Map<Point, Integer> mapMinDistance = new HashMap<>();
    static Map<Point, Integer> mapMinStar = new HashMap<>();
    static Queue<Poids> priorityQueue = new PriorityQueue<Poids>();
    static int maxX;
    static int maxY;

    public static void main(String[] args) throws IOException {

        List<String> lines = FileReaderUtils.readFile("/2021/day15/input.txt");
        //lines = enrichLines(lines);
        maxX = lines.get(0).length();
        maxY = lines.size();
        int counterY = 0;
        for (String line : lines) {
            for (int index = 0; index < line.length(); index++) {
                Point key = new Point(counterY, index);
                Integer distanceValue = Integer.valueOf(String.valueOf(line.charAt(index)));
                halo.put(key, distanceValue);
                if (key.equals(new Point(0, 0))) {
                    mapMinDistance.put(key, 0);
                    mapMinStar.put(key, 0);
                    priorityQueue.add(new Poids(key, 0));
                } else {
                    priorityQueue.add(new Poids(key, Integer.MAX_VALUE));
                    mapMinDistance.put(key, Integer.MAX_VALUE);
                    mapMinStar.put(key, Integer.MAX_VALUE);
                }
            }
            counterY++;
        }
        callDijkstra();
    }

    private static List<String> enrichLines(List<String> lines) {
        // vizszintes
        List<String> newStrings = new ArrayList<>();
        int oldYMax = lines.size();
        for (int y = 0; y < lines.size(); y++) {
            StringBuffer buffer = new StringBuffer();
            String line = lines.get(y);
            buffer.append(line);
            for (int sum = 1; sum < 5; sum++) {
                for (int x = 0; x < line.length(); x++) {
                    Integer newDistance = (Integer.valueOf(String.valueOf(line.charAt(x))) + sum);
                    if (9 < newDistance) {
                        newDistance = newDistance % 10 + 1;
                    }
                    buffer.append(newDistance);
                }
            }
            newStrings.add(buffer.toString());
        }


        for (int sum = 1; sum < 5; sum++) {
            System.out.println();
            for (int y = 0; y < oldYMax; y++) {
                StringBuffer buffer = new StringBuffer();
                String line = newStrings.get(y);

                for (int x = 0; x < newStrings.get(0).length(); x++) {
                    Integer newDistance = (Integer.valueOf(String.valueOf(line.charAt(x))) + sum);
                    if (9 < newDistance) {
                        newDistance = newDistance % 10 + 1;

                    }
                    buffer.append(newDistance);
                }
                newStrings.add(buffer.toString());
            }
        }
        return newStrings;

    }

    private static void callDijkstra() {

        Point startingPoint = new Point(0, 0);
        Point endPoint = new Point(maxY - 1, maxX - 1);
        int startingDistance;

        while (!startingPoint.equals(endPoint)) {
            // balra
            Poids newPoids = priorityQueue.poll();
            startingPoint = newPoids.point();
            startingDistance = mapMinDistance.get(startingPoint);
            directionCheck(startingPoint, startingDistance, new Point(0, -1));
            // jobbra
            directionCheck(startingPoint, startingDistance, new Point(0, 1));
            // lefele
            directionCheck(startingPoint, startingDistance, new Point(1, 0));
            // felfele
            directionCheck(startingPoint, startingDistance, new Point(-1, 0));

        }
        System.out.println("Atment rajta: " + mapMinDistance.entrySet().stream().map(s -> s.getValue()).filter(s -> s != Integer.MAX_VALUE).count());

        System.out.println("Eredmeny: " + mapMinDistance.get(endPoint));

    }

    private static void directionCheck(Point startingPoint, int startingDistance, Point move) {
        Point toCheck = new Point(startingPoint.y() + move.y(), startingPoint.x() + move.x());
        // benne van
        if (halo.containsKey(toCheck)) {
            int newDistance = halo.get(toCheck) + startingDistance;
            int sulyozas = Math.abs(toCheck.x() - maxX) + Math.abs(toCheck.y() - maxY);
            int totalF = newDistance + sulyozas;
            if (totalF < mapMinStar.get(toCheck)) {
                mapMinDistance.put(toCheck, newDistance);
                mapMinStar.put(toCheck, totalF);
                priorityQueue.add(new Poids(toCheck, totalF));
            }
        }
    }

}

record Poids(Point point, int distance) implements Comparable<Poids> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poids poids = (Poids) o;
        return distance == poids.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(distance);
    }

    @Override
    public int compareTo(Poids o) {
        if (this.distance < o.distance) {
            return -1;
        } else if (this.distance > o.distance) {
            return 1;
        }
        return 0;
    }
}

record Point(int y, int x) {

}