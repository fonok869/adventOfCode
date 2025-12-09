package com.fmolnar.code.year2025.day09;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day09Challenge2025 {
    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day09/input.txt");

        Set<Point> points = new HashSet<>();
        for (String line : lines) {
            String[] split = line.split(",");
            points.add(new Point(Long.valueOf(split[0]), Long.valueOf(split[1])));
        }
        List<Point> pointsToCheck = new ArrayList<>();
        pointsToCheck.addAll(points);

        List<Long> values = new ArrayList<>();
        for (int index = 0; index < pointsToCheck.size(); index++) {
            Point p1 = pointsToCheck.get(index);
            for (int index2 = index + 1; index2 < pointsToCheck.size(); index2++) {
                Point p2 = pointsToCheck.get(index2);
                values.add(p1.distance(p2));
            }
        }

        List<Long> maxok = values.stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println(maxok.get(0));
    }
}

record Point(long x, long y) {

    public Long distance(Point p2) {
        return (Math.abs(x - p2.x) + 1) * (Math.abs(y - p2.y) + 1);
    }
}
