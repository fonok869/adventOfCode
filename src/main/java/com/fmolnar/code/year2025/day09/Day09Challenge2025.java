package com.fmolnar.code.year2025.day09;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day09Challenge2025 {
    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day09/input.txt");

        List<Point> redPoints = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(",");
            redPoints.add(new Point(Long.valueOf(split[0]), Long.valueOf(split[1])));
        }

        Map<Long, FuggolegesFal> fuggoleges = new HashMap<>();
        Map<Long, VizszintesFal> vizszintes = new HashMap<>();
        List<Point> greenPoints = getAllGreen(redPoints, fuggoleges, vizszintes);
        List<Point> pointsToCheck = new ArrayList<>();

        Set<Integer> maxSize = new HashSet<>();
        fuggoleges.entrySet().stream().forEach(entry -> {
            if (entry.getValue().xElofordulas().size() > 2) {
                maxSize.add(entry.getValue().xElofordulas().size());
            }
        });
        vizszintes.entrySet().stream().forEach(entry -> {
            if (entry.getValue().yElofordulas().size() > 2) {
                maxSize.add(entry.getValue().yElofordulas().size());
            }
        });

        maxSize.stream().forEach(max -> {
            System.out.println(max);
        });
        pointsToCheck.addAll(redPoints);
        Stream<Long> xPoints = pointsToCheck.stream().map(Point::x);
        Stream<Long> yPoints = pointsToCheck.stream().map(Point::y);
        System.out.println("X min:" + pointsToCheck.stream().map(Point::x).mapToLong(s -> s).min().getAsLong());
        System.out.println("X max:" + pointsToCheck.stream().map(Point::x).mapToLong(s -> s).max().getAsLong());
        System.out.println("Y min:" + pointsToCheck.stream().map(Point::y).mapToLong(s -> s).min().getAsLong());
        System.out.println("Y max:" + pointsToCheck.stream().map(Point::y).mapToLong(s -> s).max().getAsLong());

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

    private List<Point> getAllGreen(List<Point> redPoints, Map<Long, FuggolegesFal> fuggoleges, Map<Long, VizszintesFal> vizszintes) {
        List<Point> greenPoints = new ArrayList<>();
        for (int i = 0; i < redPoints.size() - 1; i++) {
            Point p1 = redPoints.get(i);
            Point p2 = redPoints.get(i + 1);
            // Fuggoleges
            if (p1.x() == p2.x()) {
                if (p1.y() < p2.y()) {
                    long begin = p1.y();
                    long end = p2.y();
                    fillXSame(begin, end, greenPoints, p1.x());
                    fillEdgeX(begin, end, fuggoleges, p1.x());
                } else {
                    long begin = p2.y();
                    long end = p1.y();
                    fillXSame(begin, end, greenPoints, p1.x());
                    fillEdgeX(begin, end, fuggoleges, p1.x());
                }
                // Vizszintes
            } else if (p1.y() == p2.y()) {
                if (p1.x() < p2.x()) {
                    long begin = p1.x();
                    long end = p2.x();
                    fillYSame(begin, end, greenPoints, p1.y());
                    fillEdgeY(begin, end, vizszintes, p1.y());
                } else {
                    long begin = p2.x();
                    long end = p1.x();
                    fillYSame(begin, end, greenPoints, p1.y());
                    fillEdgeY(begin, end, vizszintes, p1.y());
                }
            }
        }
        return greenPoints;
    }

    private void fillEdgeY(long begin, long end, Map<Long, VizszintesFal> fuggoleges, long y) {
        for (long x = begin; x <= end; x++) {
            fuggoleges.putIfAbsent(x, new VizszintesFal(x, new ArrayList<>()));
            fuggoleges.get(x).yElofordulas().add(new Point(x, y));
        }
    }

    private void fillEdgeX(long begin, long end, Map<Long, FuggolegesFal> vizszintes, long x) {
        for (long y = begin; y <= end; y++) {
            vizszintes.putIfAbsent(y, new FuggolegesFal(y, new ArrayList<>()));
            vizszintes.get(y).xElofordulas().add(new Point(x, y));
        }
    }

    private void fillYSame(long begin, long end, List<Point> greenPoints, long y) {
        for (long x = begin + 1; x < end; x++) {
            greenPoints.add(new Point(x, y));
        }
    }

    private static void fillXSame(long begin, long end, List<Point> greenPoints, long x) {
        for (long y = begin + 1; y < end; y++) {
            greenPoints.add(new Point(x, y));
        }
    }
}

record Point(long x, long y) {

    public Long distance(Point p2) {
        return (Math.abs(x - p2.x) + 1) * (Math.abs(y - p2.y) + 1);
    }
}

record VizszintesFal(long x, List<Point> yElofordulas) {

}

record FuggolegesFal(long y, List<Point> xElofordulas) {

}
