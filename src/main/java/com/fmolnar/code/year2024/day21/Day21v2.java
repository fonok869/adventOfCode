package com.fmolnar.code.year2024.day21;

import com.fmolnar.code.Direction;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21v2 {

    private final Map<Pair<String, Direction>, String> keypad = new HashMap<>();

    private final Map<Pair<String, Direction>, String> dpad = new HashMap<>();

    private final Map<Pair<String, String>, Long> distance = new HashMap<>();

    private final Map<Pair<List<List<String>>, Integer>, Long> cache = new HashMap<>();

    public Day21v2() {

        keypad.put(Pair.of("0", Direction.RIGHT), "A");
        keypad.put(Pair.of("0", Direction.UP), "2");
        keypad.put(Pair.of("A", Direction.LEFT), "0");
        keypad.put(Pair.of("A", Direction.UP), "3");
        keypad.put(Pair.of("1", Direction.UP), "4");
        keypad.put(Pair.of("1", Direction.RIGHT), "2");
        keypad.put(Pair.of("2", Direction.UP), "5");
        keypad.put(Pair.of("2", Direction.RIGHT), "3");
        keypad.put(Pair.of("2", Direction.DOWN), "0");
        keypad.put(Pair.of("2", Direction.LEFT), "1");
        keypad.put(Pair.of("3", Direction.UP), "6");
        keypad.put(Pair.of("3", Direction.LEFT), "2");
        keypad.put(Pair.of("3", Direction.DOWN), "A");
        keypad.put(Pair.of("4", Direction.UP), "7");
        keypad.put(Pair.of("4", Direction.RIGHT), "5");
        keypad.put(Pair.of("4", Direction.DOWN), "1");
        keypad.put(Pair.of("5", Direction.UP), "8");
        keypad.put(Pair.of("5", Direction.RIGHT), "6");
        keypad.put(Pair.of("5", Direction.DOWN), "2");
        keypad.put(Pair.of("5", Direction.LEFT), "4");
        keypad.put(Pair.of("6", Direction.UP), "9");
        keypad.put(Pair.of("6", Direction.DOWN), "3");
        keypad.put(Pair.of("6", Direction.LEFT), "5");
        keypad.put(Pair.of("7", Direction.RIGHT), "8");
        keypad.put(Pair.of("7", Direction.DOWN), "4");
        keypad.put(Pair.of("8", Direction.RIGHT), "9");
        keypad.put(Pair.of("8", Direction.DOWN), "5");
        keypad.put(Pair.of("8", Direction.LEFT), "7");
        keypad.put(Pair.of("9", Direction.DOWN), "6");
        keypad.put(Pair.of("9", Direction.LEFT), "8");

        dpad.put(Pair.of("<", Direction.RIGHT), "v");
        dpad.put(Pair.of("v", Direction.LEFT), "<");
        dpad.put(Pair.of("v", Direction.UP), "^");
        dpad.put(Pair.of("v", Direction.RIGHT), ">");
        dpad.put(Pair.of(">", Direction.LEFT), "v");
        dpad.put(Pair.of(">", Direction.UP), "A");
        dpad.put(Pair.of("^", Direction.DOWN), "v");
        dpad.put(Pair.of("^", Direction.RIGHT), "A");
        dpad.put(Pair.of("A", Direction.DOWN), ">");
        dpad.put(Pair.of("A", Direction.LEFT), "^");
    }

    public String runSolution(List<String> lines, int depth) {
        initDistance(depth);

        AtomicLong score = new AtomicLong();
        lines.forEach(code -> {
            var c = code.split("");
            long dist = 0;
            for (int i = 0; i < c.length; ++i) {
                long d = distance.get(Pair.of(i == 0 ? "A" : c[i - 1], c[i]));
                dist += d;
            }

            long codeScore = dist * Long.parseLong(code.replaceAll("A", ""));
            System.out.println(code + " " + dist + " -> " + codeScore);
            score.addAndGet(codeScore);

        });

        return "%d".formatted(score.get());
    }

    private void initDistance(int depth) {
        for (var start : List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A")) {
            for (var end : List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A")) {
                List<String> L0 = findAllPaths(start, end, List.of(), keypad)
                        .stream()
                        .map(path -> path.stream().map(Direction::toChar).collect(Collectors.joining()) + "A")
                        .toList();
                // L0 holds every possible path to get from `start` to `end`, using a directional keypad. It is encoded as path text

                distance.put(Pair.of(start, end), getShortest(List.of(L0), depth));
            }
        }
    }

    private long getShortest(List<List<String>> steps, int depth) {
        if (cache.containsKey(Pair.of(steps, depth))) {
            return cache.get(Pair.of(steps, depth));
        }

        if (depth == 0) {
            long shortestCombination = steps.stream()
                    .map(step -> step.stream().min(Comparator.comparingLong(String::length)).orElseThrow())
                    .collect(Collectors.joining())
                    .length();
            cache.put(Pair.of(steps, depth), shortestCombination);
            return shortestCombination;
        }

        long path = 0;
        for (var step : steps) {
            Long stepShortest = null;
            for (var candidate : step) {
                var L1 = iterateKeypad(candidate);
                var shortest = getShortest(L1, depth - 1);
                if (stepShortest == null || stepShortest > shortest) stepShortest = shortest;
            }
            assert stepShortest != null;
            path += stepShortest;
        }
        cache.put(Pair.of(steps, depth), path);
        return path;
    }

    private List<List<String>> iterateKeypad(String path) {
        var pathLs = path.split("");
        List<List<String>> paths = new ArrayList<>();

        // for every step in the path, calculate possible ways to do it.
        for (int i = 0; i < pathLs.length; ++i) {
            var start = i == 0 ? "A" : pathLs[i - 1];
            var end = pathLs[i];
            var sePaths = findAllPaths(start, end, List.of(), dpad);
            paths.add(sePaths.stream().map(p -> p.stream().map(Direction::toChar).collect(Collectors.joining()) + "A").toList());
        }

        return paths;
    }

    private List<List<Direction>> findAllPaths(
            String start,
            String end,
            List<String> visited,
            Map<Pair<String, Direction>, String> graph
    ) {
        if (start.equals(end)) {
            List<List<Direction>> empty = new ArrayList<>();
            empty.add(new ArrayList<>());
            return empty;
        }

        List<List<Direction>> subpaths = new LinkedList<>();

        var newVisisted = Stream.concat(visited.stream(), Stream.of(start)).toList();
        for (Direction directionNow : Direction.values()) {
            if (graph.containsKey(Pair.of(start, directionNow))) {
                var next = graph.get(Pair.of(start, directionNow));
                if (visited.contains(next)) {
                    continue;
                }

                List<List<Direction>> subpath = findAllPaths(graph.get(Pair.of(start, directionNow)), end, newVisisted, graph);

                for (List<Direction> directionList : subpath) {
                    directionList.add(0, directionNow);
                }
                subpaths.addAll(subpath);
            }
        }

        return subpaths;
    }


}
