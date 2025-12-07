package com.fmolnar.code.year2025.day07;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day07Challenge2025 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day07/input.txt");

        Map<Point, Long> starter = new HashMap<>();
        Set<Point> splitter = new HashSet<>();

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if ("S".equals(line.substring(x, x + 1))) {
                    starter.put(new Point(y, x), 1L);
                } else if ("^".equals(line.substring(x, x + 1))) {
                    splitter.add(new Point(y, x));
                }
            }
        }

        int counterSplitter = 0;
        while (true) {
            Map<Point, Long> newStarter = new HashMap();
            for (Point start : starter.keySet()) {
                Long value = starter.get(start);
                if (splitter.contains(start.downward())) {
                    Set<Point> splitted = start.splitted();
                    for (Point splittedPoint : splitted) {
                        newStarter.compute(splittedPoint, (k1, v1) -> v1 == null ? value : v1 + value);
                    }
                    counterSplitter++;
                } else {
                    newStarter.compute(start.downward(), (k1, v1) -> v1 == null ? value : v1 + value);
                }
            }
            starter = new HashMap<>(newStarter);
            if (starter.keySet().stream().mapToInt(s -> s.y()).filter(y -> lines.size() < y).findAny().isPresent()) {
                break;
            }
        }
        System.out.println(counterSplitter);
        System.out.println("Second: " + starter.entrySet().stream().mapToLong(entry -> entry.getValue()).sum());

    }
}

record Point(int y, int x) {
    Point downward() {
        return new Point(y + 1, x);
    }

    Set<Point> splitted() {
        return Set.of(new Point(y() + 1, x + 1), new Point(y + 1, x - 1));
    }
}
