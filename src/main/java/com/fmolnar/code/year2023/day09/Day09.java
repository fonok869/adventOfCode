package com.fmolnar.code.year2023.day09;

import com.fmolnar.code.AdventOfCodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day09 {

    public static void calculate() throws IOException {
        List<String> lines = AdventOfCodeUtils.readFile("/2023/day09/input.txt");

        List<Integer> sumsBeginning = new ArrayList<>();
        List<Integer> sumsEnding = new ArrayList<>();
        for (String line : lines) {
            List<String> strings = Arrays.asList(line.split(" ")).stream().map(String::trim).toList();
            List<Integer> numbers = new ArrayList<>();
            for (String szam : strings) {
                numbers.add(Integer.valueOf(szam));
            }
            NumberList numberListActual = new NumberList(numbers);
            NumberList numberListIterator = numberListActual;
            List<NumberList> differences = new ArrayList<>();
            while (true) {
                numberListIterator = numberListIterator.kulonbsegek();
                if (numberListIterator.allZero()) {
                    break;
                }
                differences.add(numberListIterator);
            }
            Collections.reverse(differences);
            calculateNextFirst(differences, sumsBeginning, numberListActual);
            calculateNextLast(differences, sumsEnding, numberListActual);
        }

        System.out.println("Result 1 : " + sumsEnding.stream().mapToInt(s -> s).sum());
        System.out.println("Result 2 : " + sumsBeginning.stream().mapToInt(s -> s).sum());

    }

    private static void calculateNextFirst(List<NumberList> differences, List<Integer> sums, NumberList actualNumber) {
        int toAdd = 0;
        for (int i = 0; i < differences.size(); i++) {
            NumberList szamAct = differences.get(i);
            if (i == 0) {
                toAdd = szamAct.firstValue() + toAdd;
            } else {
                toAdd = szamAct.firstValue() - toAdd;
            }
        }
        sums.add(actualNumber.firstValue() - toAdd);
    }

    private static void calculateNextLast(List<NumberList> differences, List<Integer> sums, NumberList actualNumber) {
        int toAdd = 0;
        for (int i = 0; i < differences.size(); i++) {
            NumberList szamAct = differences.get(i);
            toAdd = szamAct.lastValue() + toAdd;
        }
        sums.add(actualNumber.lastValue() + toAdd);
    }

    record NumberList(List<Integer> numbers) {

        NumberList kulonbsegek() {
            List<Integer> kulonbsegek = new ArrayList<>();
            for (int i = 1; i < numbers.size(); i++) {
                kulonbsegek.add(numbers.get(i) - numbers.get(i - 1));
            }
            return new NumberList(kulonbsegek);
        }

        Integer lastValue() {
            return numbers.get(numbers.size() - 1);
        }

        Integer firstValue() {
            return numbers.get(0);
        }

        boolean allZero() {
            return numbers.stream().mapToInt(s -> s).allMatch(s -> s == 0);
        }
    }
}
