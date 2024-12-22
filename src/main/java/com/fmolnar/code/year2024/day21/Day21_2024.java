package com.fmolnar.code.year2024.day21;

import com.fmolnar.code.PointXY;
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day21_2024 {

    public final Map<PointXY, String> directionalKeyPad = Map.of(
            new PointXY(1, 0), "^",
            new PointXY(2, 0), "A",
            new PointXY(0, 1), "<",
            new PointXY(1, 1), "v",
            new PointXY(2, 1), ">"

    );

    final List<String> lines;

    public Map<PointXY, String> numericKeyPad = new HashMap<>();
    Map<CachedSteps, Set<String>> cachedDirectionalKeyPadCache = new ConcurrentHashMap<>();
    Map<String, Set<String>> cachedDirectionalLinesCache = new ConcurrentHashMap<>();

    public Day21_2024(List<String> lines) {
        Map<PointXY, String> mapSzamok = Map.of(
                new PointXY(0, 0), "7",
                new PointXY(1, 0), "8",
                new PointXY(2, 0), "9",
                new PointXY(0, 1), "4",
                new PointXY(1, 1), "5",
                new PointXY(2, 1), "6",
                new PointXY(0, 2), "1",
                new PointXY(1, 2), "2",
                new PointXY(2, 2), "3",
                new PointXY(1, 3), "0");

        numericKeyPad = new HashMap<>(mapSzamok);
        numericKeyPad.put(new PointXY(2, 3), "A");
        this.lines = lines;
    }

    public void calculateDay21_2024() throws IOException {

        //List<String> lines = AdventOfCodeUtils.readFile("/2024/day21/input.txt");


        Map<CachedSteps, Set<String>> cachedNumericKeyPadCache = new ConcurrentHashMap<>();

        int szum = 0;

        for (String line : lines) {
            String beginningLetter = "A";
            PointXY beginningPoint = new PointXY(2, 3);
            List<String> allLinesToSuccess = new ArrayList<>();

            int minOccurrence = 0;
            int szam = Integer.valueOf(line.substring(0, line.length() - 1));
            for (int i = 0; i < line.length(); i++) {
                String actualLetter = String.valueOf(line.charAt(i));
                CachedSteps cachedStep = new CachedSteps(actualLetter, beginningLetter);
                Map.Entry<PointXY, String> actualEntry = numericKeyPad.entrySet().stream().filter(s -> s.getValue().equals(actualLetter)).findFirst().get();
                PointXY actualPoint = actualEntry.getKey();


//                if (cachedNumericKeyPadCache.containsKey(cachedStep)) {
//                    createAllLinesToSuccess(allLinesToSuccess, cachedNumericKeyPadCache.get(cachedStep));
//                    beginningLetter = actualLetter;
//                    beginningPoint = actualPoint;
//                    continue;
//                }


                int diffX = actualPoint.x() - beginningPoint.x();
                int diffY = actualPoint.y() - beginningPoint.y();


                Set<String> allPossibilities = new LepesekNumeric(diffX, diffY).calculateStringOptions();

                sanitaizeCorner(actualPoint, beginningPoint, diffX, diffY, allPossibilities);

                Set<String> firstLevel = getAllDirectionalKepads(allPossibilities);
                //Set<String> second = getAllDirectionalKepads(firstLevel, false, new HashSet<>());
                Set<Integer> hosszak = new HashSet<>();
                Set<String> third = getAllDirectionalKepads(firstLevel);

                minOccurrence += third.stream().mapToInt(s -> s.length()).min().getAsInt();

                //cachedNumericKeyPadCache.put(cachedStep, new HashSet<>(allPossibilities));
                beginningLetter = actualLetter;
                beginningPoint = actualPoint;

                createAllLinesToSuccess(allLinesToSuccess, allPossibilities);

            }

            szum += minOccurrence * szam;


            System.out.println("Min:" + minOccurrence);
            System.out.println("Szam: " + szam + " line: " + line);


        }


        System.out.println(szum);
    }

    private void createAllLinesToSuccess(Collection<String> allLinesToSuccess, Set<String> allPossibilities) {
        if (allLinesToSuccess.isEmpty()) {
            allLinesToSuccess.addAll(new ArrayList<>(allPossibilities));
        } else {
            List<String> allNewLines = new ArrayList<>();
            for (String lineAlreadySuccess : allLinesToSuccess) {
                for (String newSuffix : allPossibilities) {
                    allNewLines.add(new StringBuffer(lineAlreadySuccess).append(newSuffix).toString());
                }
            }
            if (!allNewLines.isEmpty()) {
                allLinesToSuccess.clear();
                allLinesToSuccess.addAll(allNewLines);
            }
        }
    }

    private void createAllLinesNbToSuccess(Collection<Integer> allLinesToSuccess, Set<String> allPossibilities) {
        if (allLinesToSuccess.isEmpty()) {
            allLinesToSuccess.addAll(allPossibilities.stream().map(s -> s.length()).collect(Collectors.toSet()));
        } else {
            Set<Integer> allNewLinesSize = new HashSet<>();
            for (Integer lineAlreadySize : allLinesToSuccess) {
                for (String newSuffix : allPossibilities) {
                    allNewLinesSize.add(lineAlreadySize + newSuffix.length());
                }
            }
            if (!allNewLinesSize.isEmpty()) {
                allLinesToSuccess.clear();
                allLinesToSuccess.addAll(allNewLinesSize);
            }
        }
    }

    public Set<String> getAllDirectionalKepads(Collection<String> allNumericalPossibilities) {

        // directionalKeyPad

        Set<String> globals = new HashSet<>();
        for (String numericalKeyPadNextStep : allNumericalPossibilities) {

            String beginningDirectionalLetter = "A";
            PointXY beginningDirectionnalPoint = new PointXY(2, 0);
            Set<String> allLinesToSuccess = new HashSet<>();
            Set<Integer> allLinesNbSuccess = new HashSet<>();
            outer:
            for (int j = 0; j < numericalKeyPadNextStep.length(); j++) {
                String actualDirectionalLetter = String.valueOf(numericalKeyPadNextStep.charAt(j));
                CachedSteps cachedSteps = new CachedSteps(actualDirectionalLetter, beginningDirectionalLetter);
                Map.Entry<PointXY, String> actualNumericPadEntry = directionalKeyPad.entrySet().stream().filter(s -> s.getValue().equals(actualDirectionalLetter)).findFirst().get();
                PointXY directionalPoint = actualNumericPadEntry.getKey();

                if (cachedDirectionalKeyPadCache.containsKey(cachedSteps)) {

                    createAllLinesToSuccess(allLinesToSuccess, cachedDirectionalKeyPadCache.get(cachedSteps));

                    beginningDirectionalLetter = actualDirectionalLetter;
                    beginningDirectionnalPoint = directionalPoint;
                    continue;
                }


                int diffX1 = directionalPoint.x() - beginningDirectionnalPoint.x();
                int diffY1 = directionalPoint.y() - beginningDirectionnalPoint.y();

                Set<String> allPossibilitiesNumericPad = new LepesekNumeric(diffX1, diffY1).calculateStringOptions();

                sanitaizeDirectionCorner(directionalPoint, beginningDirectionnalPoint, diffX1, diffY1, allPossibilitiesNumericPad);

                cachedDirectionalKeyPadCache.put(cachedSteps, new HashSet<>(allPossibilitiesNumericPad));
                beginningDirectionalLetter = actualDirectionalLetter;
                beginningDirectionnalPoint = directionalPoint;

                createAllLinesToSuccess(allLinesToSuccess, allPossibilitiesNumericPad);
            }


            globals.addAll(allLinesToSuccess);


        }
        return globals;
    }


    private static void sanitaizeDirectionCorner(PointXY actualPoint, PointXY beginningPoint, int diffX, int diffY, Collection<String> allPossibilities) {
        if ((actualPoint.x() == 0 && beginningPoint.y() == 0) || (beginningPoint.x() == 0 && actualPoint.y() == 0)) {
            // ki kell venni egy esetet
            String toRemove = "";
            if (diffX < 0) {
                toRemove = "<".repeat(Math.abs(diffX)) + "v".repeat(Math.abs(diffY)) + "A";
            } else {
                toRemove = "^".repeat(Math.abs(diffY)) + ">".repeat(Math.abs(diffX)) + "A";
            }
            allPossibilities.remove(toRemove);
        }
    }

    private static void sanitaizeCorner(PointXY actualPoint, PointXY beginningPoint, int diffX, int diffY, Collection<String> allPossibilities) {
        if ((actualPoint.x() == 0 && beginningPoint.y() == 3) || (beginningPoint.x() == 0 && actualPoint.y() == 3)) {
            // ki kell venni egy esetet
            String toRemove = "";
            if (diffX < 0) {
                toRemove = "<".repeat(Math.abs(diffX)) + "^".repeat(Math.abs(diffY)) + "A";
            } else {
                toRemove = "v".repeat(Math.abs(diffY)) + ">".repeat(Math.abs(diffX)) + "A";
            }
            allPossibilities.remove(toRemove);
        }
    }

}

record CachedSteps(String from, String to) {

}

record LepesekNumeric(int diffX, int diffY) {


    Set<String> calculateStringOptions() {

        Set<String> result = new HashSet<>();

        // valamlyik 0

        if (diffY == 0 && diffX == 0) {
            result.add("A");
        }

        if (diffX == 0 && diffY != 0) {
            result.add(makeY(diffY));
        }

        if (diffX != 0 && diffY == 0) {
            result.add(makeX(diffX));
        }


        //minden negativ
        if (diffY < 0 && diffX < 0) {
            allPermutation("^", "<", result);
        }

        if (diffY > 0 && diffX < 0) {
            allPermutation("v", "<", result);
        }

        if (diffY < 0 && diffX > 0) {
            allPermutation("^", ">", result);
        }

        if (diffY > 0 && diffX > 0) {
            allPermutation("v", ">", result);
        }

        return result;

    }

    private void allPermutation(String ySign, String xSign, Collection<String> result) {
        List<String> relaciok = new ArrayList<>();
        IntStream.range(0, Math.abs(diffY)).forEach(s -> relaciok.add(ySign));
        IntStream.range(0, Math.abs(diffX)).forEach(s -> relaciok.add(xSign));
        Generator.permutation(relaciok)
                .simple()
                .stream().forEach(s -> {
                    result.add(String.join("", s) + "A");
                });
    }

    private String makeX(int diffX) {
        if (diffX < 0) {
            return getLongString(diffX, "<");
        }
        return getLongString(diffX, ">");

    }

    private String makeY(int diffY) {
        if (diffY < 0) {
            return getLongString(diffY, "^");
        }
        return getLongString(diffY, "v");
    }

    private static String getLongString(int diffY, String sign) {
        return sign.repeat(Math.abs(diffY)) + "A";
    }


}