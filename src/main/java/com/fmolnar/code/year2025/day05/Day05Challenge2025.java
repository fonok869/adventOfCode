package com.fmolnar.code.year2025.day05;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day05Challenge2025 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day05/input.txt");

        Set<IntervalWithSign> intervalls = new HashSet<>();
        boolean firstPart = true;
        int counter = 0;
        for (String line : lines) {
            if (line.equals("")) {
                firstPart = false;
            } else if (firstPart) {
                int index = line.indexOf('-');
                Long start = Long.parseLong(line.substring(0, index));
                Long end = Long.parseLong(line.substring(index + 1));
                intervalls.add(new IntervalWithSign(1, start, end));
            } else {
                long value = Long.parseLong(line);
                if (intervalls.stream().filter(intervalle -> intervalle.isInside(value)).findFirst().isPresent()) {
                    counter++;
                }
            }
        }

        List<IntervalWithSign> intervalWithSigns = withAllIntervals(intervalls);
        System.out.println("Number of intervalls: " + counter);
        System.out.println("Second: " + intervalWithSigns.stream().mapToLong(IntervalWithSign::distance).sum());
    }

    private List<IntervalWithSign> withAllIntervals(Set<IntervalWithSign> intervals) {
        List<IntervalWithSign> allNews = new ArrayList<>();
        intervals.forEach(interval -> {
            allNews.addAll(allNews.stream().map(interval::getIntersection).filter(Objects::nonNull).toList());
            allNews.add(interval);
        });
        return allNews;
    }

}

record IntervalWithSign(int sign, long start, long end) {

    IntervalWithSign getIntersection(IntervalWithSign inter) {
        if (inter.end < start || end < inter.start) return null;
        return new IntervalWithSign(-1 * sign * inter.sign, Math.max(start, inter.start()), Math.min(end, inter.end()));
    }

    public boolean isInside(long value) {
        return start <= value && value <= end;
    }

    public long distance() {
        return sign * ((end - start) + 1);
    }
}
