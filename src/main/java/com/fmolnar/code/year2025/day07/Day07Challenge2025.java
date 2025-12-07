package com.fmolnar.code.year2025.day07;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day07Challenge2025 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day07/input.txt");

        Map<Point, Long> starter = new HashMap<>();
        Set<Point> splitter = new HashSet<>();

        readInputAndGetStartAndSplitterPosition(lines, starter, splitter);

        AtomicInteger counterSplitter = new AtomicInteger(0);
        while (true) {
            Map<Point, Long> newStarter = new HashMap<>();
            starter.forEach((start, value) -> {
                if (shouldSplitBeam(splitter, start)) {
                    start.splitted().forEach(splittedPoint ->
                            newStarter.compute(splittedPoint, (k1, v1) -> v1 == null ? value : v1 + value)
                    );
                    counterSplitter.incrementAndGet();
                } else {
                    newStarter.compute(start.downward(), (k1, v1) -> v1 == null ? value : v1 + value);
                }
            });
            starter = new HashMap<>(newStarter);
            if (isFinished(starter, lines)) {
                break;
            }
        }
        System.out.println("First: " + counterSplitter.get());
        System.out.println("Second: " + starter.entrySet().stream().mapToLong(entry -> entry.getValue()).sum());

    }

    private static void readInputAndGetStartAndSplitterPosition(List<String> lines, Map<Point, Long> starter, Set<Point> splitter) {
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (isStarterPoint(line, x)) {
                    starter.put(new Point(y, x), 1L);
                } else if (isSplitterPoint(line, x)) {
                    splitter.add(new Point(y, x));
                }
            }
        }
    }

    private static boolean isSplitterPoint(String line, int x) {
        return "^".equals(line.substring(x, x + 1));
    }

    private static boolean isStarterPoint(String line, int x) {
        return "S".equals(line.substring(x, x + 1));
    }

    private static boolean isFinished(Map<Point, Long> starter, List<String> lines) {
        return starter.keySet().stream().mapToInt(s -> s.y()).filter(y -> lines.size() < y).findAny().isPresent();
    }

    private static boolean shouldSplitBeam(Set<Point> splitter, Point start) {
        return splitter.contains(start.downward());
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
