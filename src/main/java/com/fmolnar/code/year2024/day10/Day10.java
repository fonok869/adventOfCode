package com.fmolnar.code.year2024.day10;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day10 {

    public Set<PointXY> directionsNormals = Set.of(new PointXY(0, 1), new PointXY(0, -1), new PointXY(1, 0), new PointXY(-1, 0));

    public void calculateDay102024() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2024/day10/input.txt");


        Map<PointXY, Integer> maps = AdventOfCodeUtils.getMapIntegersInput(lines);


        Set<PointXY> founded1 = new HashSet<>();
        System.out.println("First : " + exercice(maps, founded1));

        List<PointXY> founded = new ArrayList<>();
        System.out.println("Second : " + exercice(maps, founded));

    }

    private int exercice(Map<PointXY, Integer> maps, Collection<PointXY> founded) {
        int sum = 0;
        for (Map.Entry<PointXY, Integer> entry : maps.entrySet()) {
            if (entry.getValue() == 0) {
                Set<PointXY> alreadyVisited = new HashSet<>();
                recursieve(alreadyVisited, entry.getKey(), 1, maps, founded);
                sum += founded.size();
                founded.clear();
            }
        }
        return sum;
    }

    private void recursieve(Set<PointXY> pointsAlreadyVisisted, PointXY actualPoint, int searchFor, Map<PointXY, Integer> maps, Collection<PointXY> founded) {

        for (PointXY pointToCheck : directionsNormals) {
            PointXY newPoint = new PointXY(pointToCheck.x() + actualPoint.x(), pointToCheck.y() + actualPoint.y());
            if (maps.containsKey(newPoint) && maps.get(newPoint).equals(searchFor) && !pointsAlreadyVisisted.contains(newPoint)) {
                if (searchFor == 9) {
                    founded.add(newPoint);
                    continue;
                }
                Set<PointXY> alreadyVisited = new HashSet<>();
                alreadyVisited.addAll(pointsAlreadyVisisted);
                alreadyVisited.add(newPoint);
                recursieve(alreadyVisited, newPoint, searchFor + 1, maps, founded);
            }
        }
    }
}
