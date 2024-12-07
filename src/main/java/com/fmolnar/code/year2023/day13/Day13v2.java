package com.fmolnar.code.year2023.day13;

import com.fmolnar.code.AdventOfCodeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day13v2 {

    public static void main(String[] args) throws IOException {
        calculate();
    }

    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day13/input.txt");
        List<List<Character>> maps = new ArrayList<>();
        int sum = 0;
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) {
                sum += permutateMatrixAndSolve(maps);
                maps = new ArrayList<>();
                continue;
            }
            maps.add(line.chars().mapToObj(s -> (char) s).collect(Collectors.toList()));
        }
        sum += permutateMatrixAndSolve(maps);
        System.out.println("Result: " + sum);
    }

    private static int permutateMatrixAndSolve(List<List<Character>> maps) {
        int maxY = maps.size();
        int maxX = maps.get(0).size();
        List<List<Character>> newChars = deepCopy(maps);
        for (int j = 0; j < maxY; j++) {
            for (int i = 0; i < maxX; i++) {
                char actual = newChars.get(j).get(i);
                newChars.get(j).remove(i);
                int sum = 0;
                if ('.' == actual) {
                    newChars.get(j).add(i, '#');
                    sum = calculateRemplacedMAtrix(newChars, j, i);
                } else {
                    newChars.get(j).add(i, '.');
                    sum = calculateRemplacedMAtrix(newChars, j, i);
                }
                if (sum != 0) {
                    return sum;
                }
                newChars = deepCopy(maps);
            }
        }
        return 0;

    }

    private static int calculateRemplacedMAtrix(List<List<Character>> newChars, int j, int i) {
        int sum = checkAllHorizontalLines(newChars, j, true);
        if (sum == 0) {
            sum = checkAllVerticalLines(newChars, i);
        }
        return sum;
    }

    private static int checkAllVerticalLines(List<List<Character>> newChars, int iChanged) {
        List<List<Character>> newVerticalChar = transoformMap(newChars);
        return checkAllHorizontalLines(newVerticalChar, iChanged, false);
    }

    private static List<List<Character>> deepCopy(List<List<Character>> maps) {
        List<List<Character>> newChars = new ArrayList<>();
        IntStream.range(0, maps.size()).forEach(i -> {
            List<Character> newChar = new ArrayList<>();
            IntStream.range(0, maps.get(0).size()).forEach(j -> {
                newChar.add(maps.get(i).get(j));
            });
            newChars.add(newChar);
        });
        return newChars;
    }

    private static int checkAllHorizontalLines(List<List<Character>> maps, int jChanged, boolean horizontal) {
        // Horizontal
        int sum = 0;
        boolean vizszintes = false;
        for (int i = 0; i < maps.size() - 1; i++) {
            List<Character> elso = maps.get(i);
            List<Character> masodik = maps.get(i + 1);
            boolean jChangedTouched = false;
            if (masodik.equals(elso)) {
                for (int j = 0; j <= Math.min(i, maps.size() - 1 - (i + 1)); j++) {
                    List<Character> elsoV = maps.get(i - j);
                    List<Character> masodikV = maps.get(i + 1 + j);
                    if (elsoV.equals(masodikV)) {
                        if ((i - j) == jChanged || (i + 1 + j) == jChanged) {
                            jChangedTouched = true;
                        }
                        vizszintes = true;
                    } else {
                        vizszintes = false;
                        break;
                    }
                }
                if (vizszintes && jChangedTouched) {
                    sum += (i + 1) * (horizontal ? 100 : 1);
                    break;
                }
            }
        }
        return sum;
    }

    private static List<List<Character>> transoformMap(List<List<Character>> maps) {
        List<List<Character>> newMap = new ArrayList<>();
        for (int i = 0; i < maps.get(0).size(); i++) {
            List<Character> newChars = new ArrayList<>();
            for (int j = 0; j < maps.size(); j++) {
                newChars.add(maps.get(j).get(i));
            }
            newMap.add(newChars);
        }
        return newMap;
    }
}
