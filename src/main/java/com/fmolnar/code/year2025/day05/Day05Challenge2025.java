package com.fmolnar.code.year2025.day05;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05Challenge2025 {

    public void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2025/day05/input.txt");

        Set<InterValleWithElojel> intervalls = new HashSet<>();
        boolean firstPart = true;
        int counter = 0;
        for (String line : lines) {
            if (line.equals("")) {
                firstPart = false;
            } else if (firstPart) {
                int index = line.indexOf('-');
                Long start = Long.parseLong(line.substring(0, index));
                Long end = Long.parseLong(line.substring(index + 1));
                intervalls.add(new InterValleWithElojel(1, start, end));
            } else {
                long value = Long.parseLong(line);
                if (intervalls.stream().filter(intervalle -> intervalle.isInside(value)).findFirst().isPresent()) {
                    counter++;
                }
            }
        }

        List<InterValleWithElojel> interValleWithElojels = withAllIntervals(intervalls);
        System.out.println("Second: " + interValleWithElojels.stream().mapToLong(InterValleWithElojel::distance).sum());
        System.out.println("Second: " + interValleWithElojels.stream().mapToLong(InterValleWithElojel::distance).sum());
        System.out.println("Number of intervalls: " + counter);
    }

    private List<InterValleWithElojel> withAllIntervals(Set<InterValleWithElojel> intervalls) {
        List<InterValleWithElojel> allNews = new ArrayList<>();
        for (InterValleWithElojel interval : intervalls) {
            List<InterValleWithElojel> allNewIntervalls = new ArrayList<>();
            if (allNews.isEmpty()) {

            } else {
                for (InterValleWithElojel inter : allNews) {
                    InterValleWithElojel intersection = interval.getIntersection(inter);
                    if (intersection != null) {
                        allNewIntervalls.add(intersection);
                    }
                }
                allNews.addAll(allNewIntervalls);
            }
            allNews.add(interval);
        }
        return allNews;
    }

}

record InterValleWithElojel(int elojel, long start, long end) {
    InterValleWithElojel getIntersection(InterValleWithElojel inter) {
        if (inter.end < start || end < inter.start) return null;
        return new InterValleWithElojel(-1 * elojel * inter.elojel, Math.max(start, inter.start()), Math.min(end, inter.end()));
    }

    public boolean isInside(long value) {
        return start <= value && value <= end;
    }

    public long distance() {
        return elojel * ((end - start) + 1);
    }
}
