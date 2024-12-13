package com.fmolnar.code.year2024.day13;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {
    public void calculateDay13Year2024() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2024/day13/input.txt");

        String patternButtonA = "^Button A: X\\+(\\d+), Y\\+(\\d+)";
        String patternButtonB = "^Button B: X\\+(\\d+), Y\\+(\\d+)";
        String patternPrize = "^Prize: X=(\\d+), Y=(\\d+)";

        List<Long> sumFirst = new ArrayList<>();
        List<Long> sumsSecond = new ArrayList<>();

        for (int index = 0; index < lines.size(); index = index + 4) {
            String buttonAString = lines.get(index);
            PointActual buttonA = getPoint(patternButtonA, buttonAString, true);
            String buttonBString = lines.get(index + 1);
            PointActual buttonB = getPoint(patternButtonB, buttonBString, true);
            String prizeString = lines.get(index + 2);
            PointActual prize = getPoint(patternPrize, prizeString, false);
            PointActual prize2 = getPoint(patternPrize, prizeString, true);

            sumFirst.add(getMinIfPossible2(buttonA, buttonB, prize));
            sumsSecond.add(getMinIfPossible2(buttonA, buttonB, prize2));
        }

        System.out.println("First: " + sumFirst.stream().mapToLong(s -> s).sum());
        System.out.println("Second: " + sumsSecond.stream().mapToLong(s -> s).sum());
    }

    private long getMinIfPossible2(PointActual a, PointActual b, PointActual p) {

        long x = (p.x * b.y - p.y * b.x) / (a.x * b.y - a.y * b.x);
        long y = (a.x * p.y - a.y * p.x) / (a.x * b.y - a.y * b.x);


        long bb = (p.x * a.y - p.y * a.x) / (a.y * b.x - b.y * a.x);
        long aa = (p.x * b.y - p.y * b.x) / (b.y * a.x - b.x * a.y);

        long valueX = a.x * aa + b.x * bb;
        long valueY = a.y * aa + b.y * bb;

        if (valueX == p.x && valueY == p.y) {
            System.out.println("a: " + x + " b " + y);
            return x * 3l + y;
        }
        return 0;
    }

    private long getMinIfPossible(PointActual buttonA, PointActual buttonB, PointActual prize) {
        Set<Long> mins = new HashSet<>();

        long minX = Math.min(buttonA.x(), buttonB.x());
        long minY = Math.min(buttonA.y(), buttonB.y());

        long maxX = Math.max(buttonA.x(), buttonB.x());
        long maxY = Math.max(buttonA.y(), buttonB.y());

        long minOccurencesX = prize.x() / maxX - 1;
        long minOccurencesY = prize.y() / maxY - 1;

        long maxOccurrencesX = prize.x() / minX + 1;
        long maxOccurrencesY = prize.y() / minY + 1;

        long minTotal = Math.min(minOccurencesX, minOccurencesY);
        long maxTotal = Math.max(maxOccurrencesX, maxOccurrencesY);


        for (long occurencestotal = minTotal; occurencestotal <= maxTotal; occurencestotal++) {
            for (long occurencesA = 0; occurencesA <= occurencestotal; occurencesA++) {
                long occurrenecsB = occurencestotal - occurencesA;

                long xTotal = buttonA.x() * occurencesA + buttonB.x() * occurrenecsB;
                long yTotal = buttonA.y() * occurencesA + buttonB.y() * occurrenecsB;
                long tokens = occurencesA * 3 + occurrenecsB;

                if (prize.x() < xTotal || prize.y() < yTotal) {
                    continue;
                }
                if (prize.x() == xTotal && prize.y() == yTotal) {
                    mins.add(tokens);
                    System.out.println("OccurrencesA : " + occurencesA + " occurrencesB: " + occurrenecsB);
                    continue;
                }
            }
        }
        if (mins.stream().mapToLong(s -> s).min().isPresent()) {
            return mins.stream().mapToLong(s -> s).min().getAsLong();
        }

        return 0;
    }


    private PointActual getPoint(String patternButtonA, String buttonA, boolean second) {
        Pattern matcher = Pattern.compile(patternButtonA);
        Matcher match = matcher.matcher(buttonA);
        if (match.find()) {
            if (second && "^Prize: X=(\\d+), Y=(\\d+)".equals(patternButtonA)) {
                long x = Long.valueOf((match.group(1))) + 10000000000000l;
                long y = Long.valueOf((match.group(2))) + 10000000000000l;
                return new PointActual(x, y);
            }
            long x = Long.valueOf((match.group(1)));
            long y = Long.valueOf((match.group(2)));
            return new PointActual(x, y);
        }
        return null;
    }

    record PointActual(long x, long y) {

    }


}
