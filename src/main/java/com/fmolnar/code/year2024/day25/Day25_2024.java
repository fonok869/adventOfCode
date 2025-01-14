package com.fmolnar.code.year2024.day25;

import com.fmolnar.code.AdventOfCodeUtils;
import com.fmolnar.code.PointXY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day25_2024 {

    private final List<String> lines;
    private final List<Set<PointXY>> alls = new ArrayList<>();

    public Day25_2024() throws IOException {
        long initTime = System.currentTimeMillis();
        lines = AdventOfCodeUtils.readFile("/2024/day25/input.txt");
        List<String> lineKey = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                alls.add(AdventOfCodeUtils.getMapStringInput(lineKey).entrySet().stream().filter(s -> "#".equals(s.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet()));
                lineKey = new ArrayList<>();
            } else {
                lineKey.add(line);
            }
        }
        System.out.println("Time[ms]: " + (System.currentTimeMillis() - initTime));
    }

    public void calculateDay252024() {
        long initTime = System.currentTimeMillis();
        int counter = 0;
        for (Set<PointXY> key : alls) {
            for (Set<PointXY> zar : alls) {
                Set<PointXY> sumPoints = Stream.of(key, zar).flatMap(Collection::stream).collect(Collectors.toSet());
                if (sumPoints.size() == key.size() + zar.size()) {
                    counter++;
                }
            }
        }

        System.out.println("Solution: " + counter / 2);
        System.out.println("Time[ms]: " + (System.currentTimeMillis() - initTime));
    }

}
