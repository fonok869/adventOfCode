package com.fmolnar.code.year2025.day10;

import com.fmolnar.code.AdventOfCodeUtils;
import org.ojalgo.optimisation.ExpressionsBasedModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10Challenge2025 {

    private static Map<Integer, Set<BitMap>> bitmapsChecker = new HashMap<>();

    public static void calculate() throws IOException {

        List<String> lines = AdventOfCodeUtils.readFile("/2025/day10/input.txt");

        final double EPS = 1e-9; // practical strict positivity

        ExpressionsBasedModel model = new ExpressionsBasedModel();


        List<Integer> maxs = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            String searched = split[0].substring(1, split[0].length() - 1);
            boolean[] bitmapSearched = getBitmapSearched(searched);


            List<BitMap> bitMaps = new ArrayList<>();
            for (int i = 1; i < split.length - 1; i++) {
                Set<Integer> positionsToModify = Arrays.stream(split[i].split(",")).map(s -> {
                    if (s.startsWith("(") && s.endsWith(")")) {
                        return s.substring(1, s.length() - 1);
                    } else if (s.startsWith("(")) {
                        return s.substring(1);
                    } else if (s.endsWith(")")) {
                        return s.substring(0, s.length() - 1);
                    }
                    return s;
                }).map(Integer::valueOf).collect(Collectors.toSet());
                boolean[] checker = new boolean[searched.length()];
                for (int position = 0; position < searched.length(); position++) {
                    if (positionsToModify.contains(position)) {
                        checker[position] = true;
                    }
                }
                bitMaps.add(new BitMap(checker));
            }


            maxs.add(getAllMinValues(bitmapSearched, bitMaps));
        }
        System.out.println(maxs.stream().mapToInt(s -> s).sum());
    }

    private static int getAllMinValues(boolean[] bitmapSearched, List<BitMap> bitMaps) {
        Set<Integer> mins = new HashSet<>();
        if (bitmapsChecker.get(bitMaps.size()) == null) {
            bitmapsChecker.put(bitMaps.size(), fillOut(bitMaps.size()));
        }

        Set<BitMap> allPossibilities = bitmapsChecker.get(bitMaps.size());

        for (BitMap onePossibility : allPossibilities) {
            boolean[] actual = new boolean[bitmapSearched.length];
            for (int i = 0; i < onePossibility.bitmapChanger().length; i++) {
                if (onePossibility.bitmapChanger()[i]) {
                    boolean[] actualTry = bitMaps.get(i).bitmapChanger();
                    for (int j = 0; j < bitmapSearched.length; j++) {
                        actual[j] = actual[j] ^ actualTry[j];
                    }
                }

            }
            if (equalsBoolean(actual, bitmapSearched)) {
                mins.add(onePossibility.getSize());
            }
        }

        return mins.stream().min(Integer::compareTo).get();
    }

    private static boolean equalsBoolean(boolean[] actual, boolean[] bitmapSearched) {
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != bitmapSearched[i]) {
                return false;
            }
        }
        return true;
    }

    private static Set<BitMap> fillOut(int size) {
        Set<BitMap> set = new HashSet<>();
        boolean[] trueBools = new boolean[1];
        trueBools[0] = true;
        boolean[] falseBools = new boolean[1];
        falseBools[0] = false;
        set.add(new BitMap(falseBools));
        set.add(new BitMap(trueBools));
        for (int i = 0; i < size - 1; i++) {
            Set<BitMap> newSet = new HashSet<>();
            newSet.addAll(set.stream().map(BitMap::getNewBitmaps).flatMap(Set::stream).collect(Collectors.toSet()));
            set.clear();
            set.addAll(newSet);
        }
        return set;
    }

    private static boolean[] getBitmapSearched(String searched) {
        boolean[] bitMapToSearchFor = new boolean[searched.length()];
        for (int i = 0; i < searched.length(); i++) {
            if (searched.charAt(i) == '#') {
                bitMapToSearchFor[i] = true;
            }
        }
        return bitMapToSearchFor;
    }
}

record BitMap(boolean[] bitmapChanger) {
    int getSize() {
        int counter = 0;
        for (boolean actualBinaire : bitmapChanger) {
            if (actualBinaire) {
                counter++;
            }
        }
        return counter;
    }

    Set<BitMap> getNewBitmaps() {
        boolean[] new1 = new boolean[bitmapChanger().length + 1];
        boolean[] new2 = new boolean[bitmapChanger().length + 1];
        for (int i = 0; i < bitmapChanger.length; i++) {
            new1[i] = bitmapChanger[i];
            new2[i] = bitmapChanger[i];
        }
        new1[bitmapChanger.length] = true;
        new2[bitmapChanger.length] = false;
        return Set.of(new BitMap(new1), new BitMap(new2));
    }
}
